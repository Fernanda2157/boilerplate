package com.example.boilerplate.service;

import java.util.Map;

public interface RickAndMortyService {
    Map<String, Object> getCharacters(String name); // Updated
    Map<String, Object> getLocations();
    Map<String, Object> getEpisodes();
}