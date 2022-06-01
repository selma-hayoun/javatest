package com.atmira.javatest.service;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.exception.AsyncThreadException;
import com.atmira.javatest.model.Asteroid;
import com.atmira.javatest.model.NearEarthObjects;
import com.atmira.javatest.utils.JavaTestConstants;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Slf4j
@NoArgsConstructor
@Service
public class AsteroidServiceImpl implements AsteroidServiceI {

//    @Autowired
//    @Setter
//    private RestTemplate restTemplate;
//
//    @Value("${api-nasa-endpoint}")
//    @Setter
//    private String apiNasaEndpoint;

    @Autowired
    private NasaAsyncCall nasaAsyncCall;

//    public AsteroidServiceImpl(NasaAsyncCall nasaAsyncCall) {
//        this.nasaAsyncCall = nasaAsyncCall;
//    }

    @Override
    public List<AsteroidDTO> findAllAsteroids(String planet, LocalDate dateStart, LocalDate dateEnd) {

        List<Asteroid> filteredAsteroids = new ArrayList<>();

        log.info("Inicio método findAllAsteroids " + Thread.currentThread().getName());

        //Del objeto NearEarthObjects extraemos los valores del mapa de objetos - Listas de asteroides
        //Y los vamos añadiendo a nuestra lista
        try {

            CompletableFuture<NearEarthObjects> nearEarthObjectsCompletableFuture =  nasaAsyncCall.apiCall(dateStart, dateEnd);

//            try {
                nearEarthObjectsCompletableFuture.get().getNearEarthObjects().values().forEach(filteredAsteroids::addAll);
//            }catch (CompletionException ex) {
//                throw ex.getCause();
//            }

        } catch(Throwable ex) {
//        } catch(InterruptedException | ExecutionException ex) {
//            throw new AsyncThreadException();
            log.error("Async thread error.");
        }

        log.info("Ejecutando método findAllAsteroids " + Thread.currentThread().getName());


        //Evaluamos: potencial riesgo de impacto, planeta que orbitan es el suministrado
        //Mapeamos a AsteroidDTO (calculo de diámetro medio y formateo de fecha)
        //Pasamos a lista
        List<AsteroidDTO> filteredAsteroidsDTO = filteredAsteroids
                .stream()
                .filter(a -> a.isPotentiallyHazardousAsteroid() && a.getCloseApproachData()[0].getOrbitingBody().equalsIgnoreCase(planet))
                .map(a -> new AsteroidDTO(
                        a.getName(),
                        (a.getEstimatedDiameter().getKilometers().get("estimated_diameter_min") + a.getEstimatedDiameter().getKilometers().get("estimated_diameter_max"))/2,
                        a.getCloseApproachData()[0].getRelativeVelocity().get("kilometers_per_hour"),
                        new SimpleDateFormat("yyyy-MM-dd").format(a.getCloseApproachData()[0].getCloseApproachDate()),
                        a.getCloseApproachData()[0].getOrbitingBody()
                ))
                .collect(Collectors.toList());

        //Ordenamos según diámetro (reversed: de mayor a menor)
        filteredAsteroidsDTO.sort(Comparator.comparing(AsteroidDTO::getDiameter, Comparator.nullsLast(Comparator.naturalOrder())).reversed());//Comparator null-safe

        //Enviamos los 3 más grandes de diametro
        return filteredAsteroidsDTO.stream().limit(3).collect(Collectors.toList());
    }

////    @Async("asyncExecutor")
//    @Async("ASYNCEXECUTOR")
//    public CompletableFuture<NearEarthObjects> apiCall(LocalDate dateStart, LocalDate dateEnd) throws InterruptedException {
////    protected CompletableFuture<NearEarthObjects> apiCall(LocalDate dateStart, LocalDate dateEnd) throws InterruptedException {//Tiene que ser public
//
//        log.info("Ejecutando método apiCall " + Thread.currentThread().getName());
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiNasaEndpoint)
//                .queryParam(JavaTestConstants.API_PARAMETER_START_DATE, dateStart)
//                .queryParam(JavaTestConstants.API_PARAMETER_END_DATE, dateEnd);
//
//        String uriBuilder = builder.build().encode().toUriString();
//
//        ResponseEntity<NearEarthObjects> responseEntity = restTemplate.getForEntity(uriBuilder, NearEarthObjects.class);
//
//        //Delay para pruebas
//        Thread.sleep(5000L);
//
//        return CompletableFuture.completedFuture(responseEntity.getBody());
//    }

}
