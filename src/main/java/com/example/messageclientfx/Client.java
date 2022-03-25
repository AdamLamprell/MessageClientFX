package com.example.messageclientfx;

import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

public final class Client {

    private static Socket activeSocket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private static AudioClip messageSound = new AudioClip(new File("message_tone.mp3").toURI().toString());

    private Client() { }

    public static void connect (String host) {
        try {
            Socket socket = new Socket(host, 5000);
            System.out.println("Connected to server.");

            activeSocket = socket;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            System.out.println("Error creating client");
            e.printStackTrace();
            closeEverything();
        }
    }

    public static void sendMessageToServer(String messageToServer) {
        try {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything();
        }
    }

    public static void closeEverything() {

        System.out.println("closeEverything called");

        try {
            if (activeSocket != null) {
                bufferedWriter.write("XXXXX DISCONNECT XXXXX");
                bufferedWriter.newLine();
                bufferedWriter.flush();

                activeSocket.close();
            }

            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveMessageFromClient(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (activeSocket.isConnected() && !activeSocket.isClosed()) {
                    try {
                        String messageFromServer = bufferedReader.readLine();

                        if (messageFromServer == null) {
                            closeEverything();
                            break;
                        }

                        LoggedInController.addLabel(messageFromServer, vBox);
                        playNotification();

                    } catch (SocketException e) {
                        System.out.println("Socket closed");
                        break;
                    } catch (IOException e) {
                        System.out.println("Error receiving from the server");
                        e.printStackTrace();
                        break;
                    }
                }
                closeEverything();
            }
        }).start();
    }

    public static boolean loginUser(String loginCredentials) {
        sendMessageToServer(loginCredentials);

        String messageFromServer = null;

        try {
            messageFromServer = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Error receiving from the server");
            e.printStackTrace();
            closeEverything();
        }

        return Objects.equals(messageFromServer, "OK");
    }

    public static boolean signUpUser(String signUpCredentials) {
        sendMessageToServer(signUpCredentials);

        String messageFromServer = null;

        try {
            messageFromServer = bufferedReader.readLine();
            System.out.println("TEST2: " + messageFromServer);
        } catch (IOException e) {
            System.out.println("Error receiving from the server");
            e.printStackTrace();
            closeEverything();
        }

        return Objects.equals(messageFromServer, "OK");
    }

    private static void playNotification() {
        messageSound.play();
    }
}
