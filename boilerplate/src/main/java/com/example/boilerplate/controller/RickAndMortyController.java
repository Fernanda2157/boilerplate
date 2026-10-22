package com.example.boilerplate.controller;

import com.example.boilerplate.service.RickAndMortyService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/rick-and-morty")
@CrossOrigin(origins = "*")
public class RickAndMortyController {

    private final RickAndMortyService service;

    public RickAndMortyController(RickAndMortyService service) {
        this.service = service;
    }

    // Accepts an optional 'name' query parameter from the frontend
    @GetMapping("/characters")
    public Map<String, Object> getCharacters(@RequestParam(required = false) String name) {
        return service.getCharacters(name);
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