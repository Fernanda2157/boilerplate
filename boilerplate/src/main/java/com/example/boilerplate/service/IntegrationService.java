package com.example.boilerplate.service;

import com.example.boilerplate.dto.CharacterPokemonDTO;
import java.util.List;

public interface IntegrationService {
    List<CharacterPokemonDTO> getCharactersWithPokemon();
}