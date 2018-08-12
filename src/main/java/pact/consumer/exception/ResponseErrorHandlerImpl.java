package pact.consumer.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.*;

import java.io.IOException;

public class ResponseErrorHandlerImpl extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public ResponseErrorHandlerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        if (HttpStatus.NOT_FOUND.equals(statusCode)) {
            UserNotFoundException userNotFoundException = objectMapper.readValue(getResponseBody(response), UserNotFoundException.class);
            throw userNotFoundException;
        }

        super.handleError(response, statusCode);
    }
}
