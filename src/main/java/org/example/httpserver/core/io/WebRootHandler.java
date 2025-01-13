package org.example.httpserver.core.io;

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
            throw new WebRootNotFoundException("Webroot provided does not exist or is not a folder");
        }
    }

    private boolean checkIfEndsWithSlash(String relativePath) {
        return relativePath.endsWith("/");
    }

    /**
     * This method checks to see if the relative path provided exists inside WebRoot
     *
     * @param relativePath
     * @return true if the path exists inside WebRoot, false if not.
     */
    private boolean CheckIfProvidedRelativePathExists(String relativePath) {
        File file = new File(webRoot, relativePath);

        if (!file.exists())
            return false;

        try {
            if (file.getCanonicalPath().startsWith(webRoot.getCanonicalPath())) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public String getFileMimeType(String relativePath) throws FileNotFoundException {
        if (checkIfEndsWithSlash(relativePath)) {
            relativePath += "index.html"; // By default serve the index.html, if it exists.
        }

        if (!CheckIfProvidedRelativePathExists(relativePath)) {
            throw new FileNotFoundException("File not found: " + relativePath);
        }

        File file = new File(webRoot, relativePath);

        String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());

        if (mimeType == null) {
            return "application/octet-stream";
        }

        return mimeType;
    }

    public byte[] getFileByteArrayData(String relativePath) throws FileNotFoundException, ReadFileException {
        if (checkIfEndsWithSlash(relativePath)) {
            relativePath += "index.html"; // By default serve the index.html, if it exists.
        }

        if (!CheckIfProvidedRelativePathExists(relativePath)) {
            throw new FileNotFoundException("File not found: " + relativePath);
        }

        File file = new File(webRoot, relativePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        try {
            fileInputStream.read(fileBytes);
            fileInputStream.close();
        } catch (IOException e) {
            throw new ReadFileException(e);
        }
        return fileBytes;
    }


}
