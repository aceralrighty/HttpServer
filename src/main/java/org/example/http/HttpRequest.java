package org.example.http;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Set;

public class HttpRequest extends HttpMessage {
    @Getter
    private HttpMethod method;
    @Getter
    private String requestTarget;
    @Getter
    private String originalHttpVersion; // literal from request

    @Getter
    private HttpVersion bestCompatibleVersion;

    @Getter
    @Setter
    private HashMap<String, String> headers = new HashMap<>();

    void setlHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if (this.bestCompatibleVersion == null) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_VERSION_NOT_SUPPORTED);
        }
    }

    HttpRequest() {

    }

    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    public String getHeader(String name) {
        return headers.get(name.toLowerCase());
    }

    void setMethod(String methodName) throws HttpParsingException {
        for (HttpMethod method : HttpMethod.values()) {
            if (methodName.equals(method.name())) {
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }

    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget == null || requestTarget.length() == 0) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
    }

    void addHeader(String name, String value) {
        headers.put(name.toLowerCase(), value);
    }
}
