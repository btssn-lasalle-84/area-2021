package com.example.area;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;
import java.util.Set;

/**
 * @file LiaisonBluetooth.java
 * @brief Déclaration de la classe LiaisonBluetooth
 * @author BRUNET Bastien
 * $LastChangedRevision: 125 $
 * $LastChangedDate: 2021-06-11 15:03:25 +0200 (ven. 11 juin 2021) $
 */

/**
 * @class LiaisonBluetooth
 * @brief Permet de gérer la communication bluetooth
 */
public class LiaisonBluetooth extends Thread
{
    private static final String TAG = "_LiaisonBluetooth";//<! TAG pour les logs
    private static final String ID = "00001101-0000-1000-8000-00805F9B34FB";//<! UUID utilisé lors des transmissions
    public static final int CREATION_SOCKET = 1;//<! Code utilisé par le handler pour signaler la création du socket
    public static final int CONNEXION_SOCKET = 2;//<! Code utilisé par le handler pour signaler la connexion du socket à un appareil
    public static final int DECONNEXION_SOCKET = 3;//<! Code utilisé par le handler pour signaler la deconnexion du socket à un appareil
    public static final int RECEPTION_TRAME = 4;//<! Code utilisé par le handler pour signaler la réception d'une trame

    private BluetoothSocket socket = null;//<! Socket assurant la connexion a un appareil en bluetooth
    private BluetoothDevice module = null;//<! Appareil auqel se connecter
    private static BluetoothAdapter bluetoothAdapter = null;//<! Adaptateur utilisé par la connexion bluetooth
    private InputStream fluxReception = null;//<! Flux de réception
    private OutputStream fluxEnvoi = null;//<! Flux d'envoi
    private TReception tReception = null;//<! Le thread de réception
    private Handler handlerIHM = null;//<! Handler vers une IHM

    /**
     * @brief Constructeur de la classe LiasonBluetooth
     * @param nomAppareil Nom de l'appareil auquel se connecter
     * @param handlerIHM Handler vers l'IHM
     */
    public LiaisonBluetooth(String nomAppareil, Handler handlerIHM)
    {
        this.handlerIHM = handlerIHM;

        activerBluetooth();

        if(rechercherAppareil(nomAppareil))
        {
            creerSocket();
        }
    }

    /**
     * @brief Méthode permettant d'activer le bluetooth s'il ne l'est pas
     */
    private void activerBluetooth()
    {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled())
        {
            Log.d(TAG,"Activation du Bluetooth");
            bluetoothAdapter.enable();
        }
    }

    /**
     * @brief Méthode permettant de rechercher un appareil à partir d'une adresse
     * @param idAppareil Le nom ou l'adresse MAC du module Bluetooth à rechercher
     */
    private boolean rechercherAppareil(String idAppareil)
    {
        Set<BluetoothDevice> appareilsAppaires = bluetoothAdapter.getBondedDevices();

        Log.d(TAG,"Recherche l'appareil : " + idAppareil);
        for (BluetoothDevice appareil : appareilsAppaires)
        {
            Log.d(TAG,"Nom : " + appareil.getName() + " | Adresse : " + appareil.getAddress());
            if (appareil.getName().equals(idAppareil) || appareil.getAddress().equals(idAppareil))
            {
                module = appareil;
                return true; // trouvé !
            }
        }

        return false;
    }

    /**
     * @brief Méthode permettant de créer une socket à partir d'un appareil
     */
    private boolean creerSocket()
    {
        if(module == null)
            return false;

        Log.d(TAG,"Création de la socket pour l'appareil : " + module.getName() + " | Adresse : " + module.getAddress());
        try
        {
            socket = module.createRfcommSocketToServiceRecord(UUID.fromString(ID));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            socket = null;
            Log.d(TAG, "Echec de la création de socket");
            return false;
        }

        if (socket != null)
        {
            tReception = new TReception();
            Message message = new Message();
            message.what = CREATION_SOCKET;
            message.obj = module.getName();
            handlerIHM.sendMessage(message);
            return true;
        }

        return false;
    }

    /**
     * @brief Méthode pour ouvrir la connexion avec un appareil
     */
    public void connecter()
    {
        if (module == null || socket == null)
            return;

        Log.d(TAG,"Connexion au module " + module.getName() + " | Adresse : " + module.getAddress());
        new Thread()
        {
            @Override public void run()
            {
                try
                {
                    bluetoothAdapter.cancelDiscovery();
                    socket.connect();
                    fluxReception = socket.getInputStream();
                    fluxEnvoi = socket.getOutputStream();
                    if (!tReception.isAlive())
                        tReception.start();
                    Message message = new Message();
                    message.what = CONNEXION_SOCKET;
                    message.obj = module.getName();
                    handlerIHM.sendMessage(message);
                }
                catch (IOException e)
                {
                    Log.e(TAG,"Erreur ouverture du socket");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * @brief Méthode pour fermer la connexion avec un appareil
     */
    public void deconnecter()
    {
        if (module == null || socket == null)
            return;

        Log.d(TAG,"Déconnexion du module " + module.getName() + " | Adresse : " + module.getAddress());
        new Thread()
        {
            @Override public void run()
            {
                try
                {
                    tReception.arreter();
                    socket.close();
                    Message message = new Message();
                    message.what = DECONNEXION_SOCKET;
                    message.obj = module.getName();
                    handlerIHM.sendMessage(message);
                }
                catch (IOException e)
                {
                    Log.e(TAG,"Erreur fermeture du socket");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * @brief Méthode pour envoyer des données
     */
    public void envoyer(String donnees)
    {
        if (module == null || socket == null)
            return;

        new Thread()
        {
            @Override public void run()
            {
                try
                {
                    if(socket.isConnected())
                    {
                        Log.d(TAG,"Envoi vers le module " + module.getName() + " | Adresse : " + module.getAddress() + " | Données : " + donnees);
                        fluxEnvoi.write(donnees.getBytes());
                    }
                }
                catch(IOException e)
                {
                    Log.e(TAG,"Erreur envoi socket");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public String getNomModule()
    {
        if(module != null)
        {
            return module.getName();
        }

        return null;
    }

    private class TReception extends Thread
    {
        private boolean fini;

        TReception()
        {
          fini = false;
        }

        /**
         * @brief Méthode qui permet de lire les données entrantes
         */
        @Override public void run()
        {
            Log.d(TAG,"Démarrage du thread Réception pour le module " + module.getName() + " | Adresse : " + module.getAddress());
            BufferedReader reception = new BufferedReader(new InputStreamReader(fluxReception));
            while(!fini)
            {
                try
                {
                    String trame = "";
                    if (socket.isConnected())
                    {
                        trame = reception.readLine();
                        if(trame.length() > 0)
                        {
                          Log.d(TAG, "Trame : " + trame);
                          Message message = new Message();
                          message.what = RECEPTION_TRAME;
                          message.obj = trame;
                          handlerIHM.sendMessage(message);
                        }
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = DECONNEXION_SOCKET;
                    message.obj = module.getName();
                    handlerIHM.sendMessage(message);
                    connecter();
                }

                try
                {
                    Thread.sleep(250);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            Log.d(TAG,"Arrêt du thread Réception pour le module " + module.getName() + " | Adresse : " + module.getAddress());
        }

        /**
         * @brief Méthode qui permet d'arrêter lire les données entrantes
         */
        public void arreter()
        {
            if (fini == false)
            {
                fini = true;
            }

            try
            {
                Thread.sleep(250);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
