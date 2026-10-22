package com.example.boilerplate.service;

import com.example.boilerplate.client.RickAndMortyClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RickAndMortyServiceImpl implements RickAndMortyService {

    private final RickAndMortyClient client;

    public RickAndMortyServiceImpl(RickAndMortyClient client) {
        this.client = client;
    }

    @Override
    public Map<String, Object> getCharacters() {
        return client.getCharacters();
    }

    @Override
    public Map<String, Object> getLocations() {
        return client.getLocations();
    }

    @Override
    public Map<String, Object> getEpisodes() {
        return client.getEpisodes();
    }
}