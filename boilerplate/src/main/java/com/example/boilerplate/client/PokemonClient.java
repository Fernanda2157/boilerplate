package com.example.boilerplate.client;

import com.example.boilerplate.dto.PokemonDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client responsible for HTTP communication with the PokeAPI.
 */
@Component
public class PokemonClient {
    
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    public PokemonClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PokemonDTO getPokemonById(Integer id) {
        try {
            return restTemplate.getForObject(BASE_URL + id, PokemonDTO.class);
        } catch (Exception e) {
            // If the API fails or the Pokemon doesn't exist, return a safe default
            return PokemonDTO.placeholder(); 
        }
    }
}