package org.example.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class HttpConnectionWorkerThread extends Thread {
    private Socket socket;
    private final static Logger LOGGER = Logger.getLogger(HttpConnectionWorkerThread.class.getName());

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // Simple HTML response
            String html = "<html><head><title>My Simple Java HTTP Server</title></head><body><h1>I was able to serve with a java server</h1></body></html>";

            final String CRLF = "\r\n";

            // Properly formatted HTTP response
            String response = "HTTP/1.1 200 OK" + CRLF +
                    "Content-Length: " + html.getBytes().length + CRLF +
                    "Content-Type: text/html" + CRLF +
                    CRLF +
                    html;

            // Write response to output stream
            outputStream.write(response.getBytes());
            outputStream.flush();

            LOGGER.info(" * Connection Processing finished...");

        } catch (Exception e) {
            LOGGER.severe(" * Connection Processing failed: " + e.getMessage());
        } finally {
            // Close resources in a safe manner
            try {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                LOGGER.severe(" * Failed to close resources: " + e.getMessage());
            }
        }
    }

}
