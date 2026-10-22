package com.example.boilerplate.controller;

import com.example.boilerplate.dto.CharacterPokemonDTO;
import com.example.boilerplate.service.IntegrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/integration")
public class IntegrationController {

    private final IntegrationService integrationService;

    public IntegrationController(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @GetMapping("/characters-with-pokemon")
    public List<CharacterPokemonDTO> getCharactersWithPokemon() {
        return integrationService.getCharactersWithPokemon();
    }
}