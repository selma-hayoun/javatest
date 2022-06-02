package com.atmira.javatest.service;

import com.atmira.javatest.exception.NotSupportedPlanetException;
import com.atmira.javatest.model.NearEarthObjects;
import com.atmira.javatest.utils.JavaTestConstants;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class NasaAsyncCall {

    @Autowired
    @Setter
    private RestTemplate restTemplate;

    @Value("${api-nasa-endpoint}")
    @Setter
    private String apiNasaEndpoint;

//    public NasaAsyncCall(RestTemplate restTemplate, @Value("${api-nasa-endpoint}") String apiNasaEndpoint) {
//        this.restTemplate = restTemplate;
//        this.apiNasaEndpoint = apiNasaEndpoint;
//    }

    //    @Async("asyncExecutor")
//    @Async("ASYNCEXECUTOR")
    @Async
//    public CompletableFuture<NearEarthObjects> apiCall(LocalDate dateStart, LocalDate dateEnd) throws Exception {
      public void apiCall(LocalDate dateStart, LocalDate dateEnd) throws Exception {
//    protected CompletableFuture<NearEarthObjects> apiCall(LocalDate dateStart, LocalDate dateEnd) throws InterruptedException {//Tiene que ser public

        log.info("Ejecutando método apiCall " + Thread.currentThread().getName());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiNasaEndpoint)
                .queryParam(JavaTestConstants.API_PARAMETER_START_DATE, dateStart)
                .queryParam(JavaTestConstants.API_PARAMETER_END_DATE, dateEnd);

        String uriBuilder = builder.build().encode().toUriString();

        ResponseEntity<NearEarthObjects> responseEntity = restTemplate.getForEntity(uriBuilder, NearEarthObjects.class);

        throw new Exception("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + Thread.currentThread().getName());

        //Delay para pruebas
//        Thread.sleep(5000L);

//        return CompletableFuture.completedFuture(responseEntity.getBody());
    }
}
