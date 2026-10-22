package com.example.boilerplate.service;

import com.example.boilerplate.client.PokemonClient;
import com.example.boilerplate.client.RickAndMortyClient;
import com.example.boilerplate.dto.CharacterDTO;
import com.example.boilerplate.dto.CharacterPokemonDTO;
import com.example.boilerplate.dto.PokemonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class IntegrationServiceImpl implements IntegrationService {

    private static final Logger log = LoggerFactory.getLogger(IntegrationServiceImpl.class);
    
    private final RickAndMortyClient rickAndMortyClient;
    private final PokemonClient pokemonClient;
    
    // Cache to avoid repeated calls to the PokeAPI for the same ID
    private final ConcurrentHashMap<Integer, PokemonDTO> pokemonCache = new ConcurrentHashMap<>();

    public IntegrationServiceImpl(RickAndMortyClient rickAndMortyClient, PokemonClient pokemonClient) {
        this.rickAndMortyClient = rickAndMortyClient;
        this.pokemonClient = pokemonClient;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CharacterPokemonDTO> getCharactersWithPokemon() {
        log.info("Starting nested select process for Rick and Morty characters");
        
        Map<String, Object> response = rickAndMortyClient.getCharacters(null);
        if (response == null || response.get("results") == null) {
            log.warn("No characters found in Rick and Morty API");
            return List.of();
        }

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        // We limit to 10 for the demo to avoid overloading the external APIs
        return results.stream()
                .limit(10)
                .map(this::mapToCharacterAndPokemon)
                .collect(Collectors.toList());
    }

    /**
     * Nested Select: For each character, we fetch its Pokemon using the character's ID.
     */
    private CharacterPokemonDTO mapToCharacterAndPokemon(Map<String, Object> charData) {
        Integer id = (Integer) charData.get("id");
        String name = (String) charData.get("name");
        String species = (String) charData.get("species");
        
        CharacterDTO character = new CharacterDTO(id, name, species);

        // Use computeIfAbsent to cache the Pokemon and avoid redundant API calls
        PokemonDTO pokemon = pokemonCache.computeIfAbsent(id, key -> {
            log.info("Fetching Pokemon data for ID: {}", key);
            return pokemonClient.getPokemonById(key);
        });
        
        return new CharacterPokemonDTO(character, pokemon);
    }
}