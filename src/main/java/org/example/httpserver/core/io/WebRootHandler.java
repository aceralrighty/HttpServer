package org.example.httpserver.core.io;

import org.example.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;


public class WebRootHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(WebRootHandler.class);
    private File webRoot;

    public WebRootHandler(String webRootPath) throws WebRootNotFoundException {
        webRoot = new File(webRootPath);
        if (!webRoot.exists() || !webRoot.isDirectory()) {
            throw new WebRootNotFoundException("Webroot provided does not exist");
        }
    }

    private boolean CheckIfEndsWithSlash(String relativePath) {
        return relativePath.endsWith("/");
    }

    private boolean CheckIfProvidedRelativePathExists(String relativePath) {
        File file = new File(webRoot, relativePath);
        if (!file.exists()) {
            return false; // File does not exist
        }
        try {
            return file.getCanonicalPath().startsWith(webRoot.getCanonicalPath());
        } catch (IOException e) {
            return false; // Error resolving canonical path
        }
    }

    public String getFileMimeType(String relativePath) throws FileNotFoundException {
        if (CheckIfEndsWithSlash(relativePath)) {
            relativePath += "index.html";
        }
        if (CheckIfProvidedRelativePathExists(relativePath)) {
            throw new FileNotFoundException("File Not Found: " + relativePath);
        }
        File file = new File(webRoot, relativePath);
        String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());
        if (mimeType == null) {
            return "application/octet-stream";
        }
        return mimeType;
    }

        public byte[] getFileByteArrayData(String relativePath) throws ReadFileException, FileNotFoundException {
        if (CheckIfEndsWithSlash(relativePath)) {
            relativePath += "index.html";
        }
        if (CheckIfProvidedRelativePathExists(relativePath)) {
            throw new FileNotFoundException("File Not Found: " + relativePath);
        }
        File file = new File(webRoot, relativePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            int bytesRead = 0;
            while (bytesRead < bytes.length) {
                int result = fileInputStream.read(bytes, bytesRead, bytes.length - bytesRead);
                if (result == -1) {
                    throw new ReadFileException("Unexpected end of file: " + relativePath);
                }
                bytesRead += result;
            }
            return bytes;
        } catch (IOException e) {
            throw new ReadFileException("Error reading file: " + relativePath, e);
        }
    }



}
