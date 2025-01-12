package org.example.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerListenerThread extends Thread {

    private int port;
    private String webRoot;
    private final static Logger LOGGER = Logger.getLogger(ServerListenerThread.class.getName());

    private ServerSocket serverSocket;

    /**
     * In here i'm passing in my port into the constructor that also has the serverSocket
     *
     * @param port
     * @param webRoot
     * @throws IOException
     */
    public ServerListenerThread(int port, String webRoot) throws IOException {
        this.port = port;
        this.webRoot = webRoot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                LOGGER.info(" * Accepted connection from " + socket.getInetAddress());
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);

                workerThread.start();


            }

        } catch (IOException e) {
            LOGGER.severe("Problem with setting socket: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
