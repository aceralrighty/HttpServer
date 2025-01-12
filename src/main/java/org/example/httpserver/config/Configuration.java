package org.example.httpserver.config;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Configuration {
    private int port;
    private String webRoot;
}
