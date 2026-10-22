package com.example.boilerplate.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Component
public class RickAndMortyClient {
    
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://rickandmortyapi.com/api/";

    public RickAndMortyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Accepts 'name' parameter for filtering
    public Map<String, Object> getCharacters(String name) {
        String url = BASE_URL + "character";
        if (name != null && !name.trim().isEmpty()) {
            url += "?name=" + name.trim();
        }
        return restTemplate.getForObject(url, Map.class);
    }

    public Map<String, Object> getLocations() {
        return restTemplate.getForObject(BASE_URL + "location", Map.class);
    }

    public Map<String, Object> getEpisodes() {
        return restTemplate.getForObject(BASE_URL + "episode", Map.class);
    }
}