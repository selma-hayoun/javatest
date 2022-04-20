package com.atmira.javatest.service;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.model.Asteroid;
import com.atmira.javatest.model.NearEarthObjects;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AsteroidServiceImpl implements AsteroidServiceI {

    @Autowired
    @Setter
    private RestTemplate restTemplate;

    protected static final String API_REQUEST_ENDPOINT = "https://api.nasa.gov/neo/rest/v1/feed";
    protected static final String API_PARAMETER_START_DATE = "start_date";
    protected static final String API_PARAMETER_END_DATE = "end_date";
    protected static final String API_PARAMETER_KEY = "api_key";

    @Value("${api-key}")
    protected String API_KEY;

    @Override
    public List<AsteroidDTO> findAllAsteroids(String planet, LocalDate dateStart, LocalDate dateEnd) {

        List<Asteroid> filteredAsteroids = new ArrayList<>();

        //Del objeto NearEarthObjects extraemos los valores del mapa de objetos - Listas de asteroides
        //Y los vamos añadiendo a nuestra lista
        apiCall(dateStart, dateEnd).getNear_earth_objects().values().forEach(filteredAsteroids::addAll);

        //Evaluamos: potencial riesgo de impacto, planeta que orbitan es el suministrado
        //Mapeamos a AsteroidDTO (calculo de diámetro medio y formateo de fecha)
        //Pasamos a lista
        List<AsteroidDTO> filteredAsteroidsDTO = filteredAsteroids
                .stream()
                .filter(a -> a.is_potentially_hazardous_asteroid() && a.getClose_approach_data()[0].getOrbiting_body().equalsIgnoreCase(planet))
                .map(a -> new AsteroidDTO(
                        a.getName(),
                        (a.getEstimated_diameter().getKilometers().get("estimated_diameter_min") + a.getEstimated_diameter().getKilometers().get("estimated_diameter_max"))/2,
                        a.getClose_approach_data()[0].getRelative_velocity().get("kilometers_per_hour"),
                        new SimpleDateFormat("yyyy-MM-dd").format(a.getClose_approach_data()[0].getClose_approach_date()),
                        a.getClose_approach_data()[0].getOrbiting_body()
                ))
                .collect(Collectors.toList());

        //Ordenamos según diámetro (reversed: de mayor a menor)
        filteredAsteroidsDTO.sort(Comparator.comparing(AsteroidDTO::getDiameter).reversed());

        //Enviamos los 3 más grandes de diametro
        return filteredAsteroidsDTO.stream().limit(3).collect(Collectors.toList());
    }

    protected NearEarthObjects apiCall(LocalDate dateStart, LocalDate dateEnd){
        //URL de la petición
//        String API_REQUEST = API_REQUEST_ENDPOINT + "?" + API_PARAMETER_START_DATE + "=" + dateStart
//                + "&" + API_PARAMETER_END_DATE + "=" + dateEnd + "&" + API_PARAMETER_KEY + "=" + API_KEY;

//        Consultar forma de implementación de esta forma de envío de parámetros
//        MultiValueMap<String, String> params = new MultiValueMap<String, String>() {};
//        params.put(API_PARAMETER_KEY, API_KEY);
//        params.put(API_PARAMETER_START_DATE, dateStart.toString());
//        params.put(API_PARAMETER_END_DATE, dateEnd.toString());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_REQUEST_ENDPOINT)
                .queryParam(API_PARAMETER_START_DATE, dateStart)
                .queryParam(API_PARAMETER_END_DATE, dateEnd)
                .queryParam(API_PARAMETER_KEY, API_KEY);

        String uriBuilder = builder.build().encode().toUriString();

//        ResponseEntity<NearEarthObjects> responseEntity = restTemplate.exchange(API_REQUEST_ENDPOINT, HttpMethod.GET, null, NearEarthObjects.class, params);
        ResponseEntity<NearEarthObjects> responseEntity = restTemplate.getForEntity(uriBuilder, NearEarthObjects.class);

        return responseEntity.getBody();
    }

}
