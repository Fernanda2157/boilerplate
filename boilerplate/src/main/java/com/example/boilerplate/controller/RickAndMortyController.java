package com.example.boilerplate.controller;

import com.example.boilerplate.service.RickAndMortyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/rick-and-morty")
@CrossOrigin(origins = "*")
public class RickAndMortyController {

    private final RickAndMortyService service;

    public RickAndMortyController(RickAndMortyService service) {
        this.service = service;
    }

    @GetMapping("/characters")
    public Map<String, Object> getCharacters() {
        return service.getCharacters();
    }

    @GetMapping("/locations")
    public Map<String, Object> getLocations() {
        return service.getLocations();
    }

    @GetMapping("/episodes")
    public Map<String, Object> getEpisodes() {
        return service.getEpisodes();
    }
}