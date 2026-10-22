package com.example.boilerplate.dto;

public record PokemonDTO(String name, int height) {
    // Fallback method in case the external API fails or returns no data
    public static PokemonDTO placeholder() {
        return new PokemonDTO("Unknown", 0);
    }
}