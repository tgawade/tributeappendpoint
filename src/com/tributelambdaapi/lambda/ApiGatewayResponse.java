package com.tributelambdaapi.lambda;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import org.apache.log4j.Logger;

public class ApiGatewayResponse {
	private Integer statusCode;

    private Object body;

    private Map<String, String> headers;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ApiGatewayResponse(Integer statusCode, Object body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.headers = headers;
        init(body);
    }

    private void init(Object body) {
        try {
            this.body = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            //log.error("Error while serializing body to JSON", e);
            throw new RuntimeException("Failed to convert body to JSON");
        }
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
