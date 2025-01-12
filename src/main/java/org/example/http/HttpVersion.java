package org.example.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1", 1, 1);

    public final String LITERAL;
    public final int MAJOR;
    public final int MINOR;

    HttpVersion(String literal, int major, int minor) {
        LITERAL = literal;
        MAJOR = major;
        MINOR = minor;
    }

    private static final Pattern httpVersionPattern = Pattern.compile("^HTTP/(?<major>\\d+).(?<minor>\\d+)");

    public static HttpVersion getBestCompatibleVersion(String version) throws BadHttpVersionException {
        Matcher matcher = httpVersionPattern.matcher(version);
        if (!matcher.find() || matcher.groupCount() != 2) {
            throw new BadHttpVersionException("Invalid HTTP version: " + version);
        }
        int major = Integer.parseInt(matcher.group("major"));
        int minor = Integer.parseInt(matcher.group("minor"));
        HttpVersion tempBestCompatibleVersion = null;
        for (HttpVersion httpVersion : HttpVersion.values()) {
            if (httpVersion.LITERAL.equals(version)) {
                return httpVersion;
            } else {
                if (httpVersion.MAJOR == major) {
                    if (httpVersion.MINOR < minor) {
                        tempBestCompatibleVersion = httpVersion;
                    }
                }
            }
        }
        return tempBestCompatibleVersion;
    }
}
