package org.example.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20;
    private static final int CR = 0x0D;
    private static final int LF = 0x0A;


    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException, IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest request = new HttpRequest();

        parseRequestLine(reader, request);
        parseHeaders(reader, request);
        parseBody(reader, request);
        return request;
    }

    private void parseBody(InputStreamReader reader, HttpRequest request) {

    }

    private void parseHeaders(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder processingDataBuffer = new StringBuilder();
        boolean crlfFound = false;

        int _byte;
        while ((_byte = reader.read()) >= 0) {
            if (_byte == CR) {
                _byte = reader.read();
                if (_byte == LF) {
                    if (!crlfFound) {
                        crlfFound = true;

                        // Do Things like processing
                        processSingleHeaderField(processingDataBuffer, request);
                        // Clear the buffer
                        processingDataBuffer.delete(0, processingDataBuffer.length());
                    } else {
                        // Two CRLF received, end of Headers section
                        return;
                    }
                } else {
                    throw new HttpParsingException(HttpStatusCode.ClIENT_ERROR_400_BAD_REQUEST);
                }
            } else {
                crlfFound = false;
                // Append to Buffer
                processingDataBuffer.append((char) _byte);
            }
        }
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        int _byte;
        StringBuilder processingDataBuffer = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;


        while ((_byte = reader.read()) >= 0) {
            if (_byte == CR) {
                _byte = reader.read();
                if (_byte == LF) {
                    LOGGER.debug("Request Line VERSION to Process: {}", processingDataBuffer.toString());
                    if (!methodParsed || !requestTargetParsed) {
                        throw new HttpParsingException(HttpStatusCode.ClIENT_ERROR_400_BAD_REQUEST);
                    }
                    try {
                        request.setlHttpVersion(processingDataBuffer.toString());
                    } catch (BadHttpVersionException e) {
                        throw new HttpParsingException(HttpStatusCode.ClIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                } else {
                    throw new HttpParsingException(HttpStatusCode.ClIENT_ERROR_400_BAD_REQUEST);
                }
            }
            if (_byte == SP) {
                if (!methodParsed) {
                    LOGGER.debug("Request Line METHOD to Process: {}", processingDataBuffer);
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                    LOGGER.debug("Request Line REQ TARGET to Process: {}", processingDataBuffer);
                    request.setRequestTarget(processingDataBuffer.toString());
                    requestTargetParsed = true;
                } else {
                    throw new HttpParsingException(HttpStatusCode.ClIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            } else {
                processingDataBuffer.append((char) _byte);
                if (!methodParsed) {
                    if (processingDataBuffer.length() > HttpMethod.MAX_LENGTH) {
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }

    private void processSingleHeaderField(StringBuilder processingDataBuffer, HttpRequest request) throws HttpParsingException {
        String rawHeaderField = processingDataBuffer.toString();
        Pattern pattern = Pattern.compile("^(?<fieldName>[!#$%&’*+\\-./^_‘|˜\\dA-Za-z]+):\\s?(?<fieldValue>[!#$%&’*+\\-./^_‘|˜(),:;<=>?@[\\\\]{}\" \\dA-Za-z]+)\\s?$");

        Matcher matcher = pattern.matcher(rawHeaderField);
        if (matcher.matches()) {
            // We found a proper header
            String fieldName = matcher.group("fieldName");
            String fieldValue = matcher.group("fieldValue");
            request.addHeader(fieldName, fieldValue);
        } else {
            throw new HttpParsingException(HttpStatusCode.ClIENT_ERROR_400_BAD_REQUEST);
        }
    }

}
