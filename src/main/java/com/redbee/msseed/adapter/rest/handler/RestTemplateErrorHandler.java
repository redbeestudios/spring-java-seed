package com.redbee.msseed.adapter.rest.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import com.redbee.msseed.adapter.rest.exception.RestClientGenericException;
import com.redbee.msseed.config.ErrorCode;

import java.io.IOException;
import java.util.Map;

public final class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    private final Map<HttpStatus, RuntimeException> exceptionsMap;

    public RestTemplateErrorHandler(Map<HttpStatus, RuntimeException> exceptionsMap) {
        this.exceptionsMap = exceptionsMap;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw exceptionsMap.getOrDefault(response.getStatusCode(),
                new RestClientGenericException(ErrorCode.WEB_CLIENT_GENERIC));
    }
}
