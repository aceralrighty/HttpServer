package org.example.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.httpserver.util.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    /**
     * my singleton
     */
    private static ConfigurationManager myConfigurationManager;

    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    /**
     * Used to load a configuration file by the provided path
     *
     * @param configFilePath
     */
    public void loadConfigurationFile(String configFilePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(configFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try{
            while(( i = fileReader.read()) != -1){
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException("Error while reading configuration file", e);
        }
        JsonNode conf = null;
        try {
            conf = JsonParser.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Unable to parse configuration file", e);
        }
        try {
            myCurrentConfiguration = JsonParser.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Unable to parse configuration file, internally", e);
        }
    }

    /**
     * Returns the current loaded configuration
     */
    public Configuration getCurrentConfiguration() throws HttpConfigurationException {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No current configuration found");
        }
        return myCurrentConfiguration;
    }
}
