package com.atmira.javatest.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
public class AsteroidsRestTemplateInterceptor implements ClientHttpRequestInterceptor {

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
        log.info("[REST TEMPLATE INTERCEPTOR] - REQUEST: " + request.toString());

        ClientHttpResponse response = execution.execute(request, body);
        log.info("[REST TEMPLATE INTERCEPTOR] - RESPONSE: " + response.toString());

        return response;
    }
}
