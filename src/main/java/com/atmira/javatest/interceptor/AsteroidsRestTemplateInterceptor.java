package com.atmira.javatest.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;


import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AsteroidsRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    protected static final String API_PARAMETER_KEY = "api_key";

    @Value("${api-key}")
    protected String API_KEY;

    /**
     * This interceptor will be invoked for every incoming request
     *
     * @param request
     * @param body
     * @param execution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        log.info("[REST TEMPLATE INTERCEPTOR] - REQUEST TO NASA: " + "[" + request.getMethod() + "]" + request.getURI());
        logRequest(request, body);

        //Modificamos la petición: añadir API key
        URI uri = UriComponentsBuilder.fromHttpRequest(request)
                .queryParam(API_PARAMETER_KEY, API_KEY)
                .build().toUri();

        HttpRequest modifiedRequest = new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return uri;
            }
        };

//        ClientHttpResponse response = execution.execute(request, body);
        ClientHttpResponse response = execution.execute(modifiedRequest, body);

//        InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8);
//        String strBody = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));

        log.info("[REST TEMPLATE INTERCEPTOR] - RESPONSE FROM NASA: " + "[Https status: " + response.getStatusCode() + "]");
        logResponse(response);

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("===========================REQUEST INFO BEGIN================================================");
            log.debug("URI         : {}", request.getURI());
            log.debug("Method      : {}", request.getMethod());
            log.debug("Headers     : {}", request.getHeaders());
            log.debug("Request body: {}", new String(body, "UTF-8"));
            log.debug("==========================REQUEST INFO END================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("============================RESPONSE INFO BEGIN==========================================");
            log.debug("Status code  : {}", response.getStatusCode());
            log.debug("Status text  : {}", response.getStatusText());
            log.debug("Headers      : {}", response.getHeaders());
            log.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));//TO-DO: Mientras refactoring y tratamiento de logs
            log.debug("=======================RESPONSE INFO END=================================================");
        }
    }
}
