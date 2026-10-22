package com.example.boilerplate.service;


import java.util.Map;

public interface RickAndMortyService {
    Map<String, Object> getCharacters();
    Map<String, Object> getLocations();
    Map<String, Object> getEpisodes();
}