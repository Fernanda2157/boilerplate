package com.example.boilerplate.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/**
 * Client responsible for HTTP communication with the Rick and Morty API.
 */
@Component
public class RickAndMortyClient {
    
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://rickandmortyapi.com/api/";

    public RickAndMortyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getCharacters() {
        return restTemplate.getForObject(BASE_URL + "character", Map.class);
    }
    public Map<String, Object> getLocations() {
        return restTemplate.getForObject(BASE_URL + "location", Map.class);
    }

    public Map<String, Object> getEpisodes() {
        return restTemplate.getForObject(BASE_URL + "episode", Map.class);
    }
}