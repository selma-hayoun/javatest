package com.atmira.javatest.interceptor;

import com.atmira.javatest.utils.JavaTestConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AsteroidsRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Value("${api-key}")
    private String apiKey;

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

        log.info("[REST TEMPLATE INTERCEPTOR] - REQUEST TO NASA: [{}]{}", request.getMethod(), request.getURI());
        logRequest(request, body);

        //Modificamos la petición: añadir API key
        URI uri = UriComponentsBuilder.fromHttpRequest(request)
                .queryParam(JavaTestConstants.API_PARAMETER_KEY, apiKey)
                .build().toUri();

        HttpRequest modifiedRequest = new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return uri;
            }
        };

        ClientHttpResponse response = execution.execute(modifiedRequest, body);

        log.info("[REST TEMPLATE INTERCEPTOR] - RESPONSE FROM NASA: [Https status:{}]", response.getStatusCode());
        logResponse(response);

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("===========================REQUEST INFO BEGIN================================================");
            log.debug("URI         : {}", request.getURI());
            log.debug("Method      : {}", request.getMethod());
            log.debug("Headers     : {}", request.getHeaders());
            log.debug("Request body: {}", new String(body, StandardCharsets.UTF_8));
            log.debug("==========================REQUEST INFO END================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("============================RESPONSE INFO BEGIN==========================================");
            log.debug("Status code  : {}", response.getStatusCode());
            log.debug("Status text  : {}", response.getStatusText());
            log.debug("Headers      : {}", response.getHeaders());
            log.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            log.debug("=======================RESPONSE INFO END=================================================");
        }
    }
}
