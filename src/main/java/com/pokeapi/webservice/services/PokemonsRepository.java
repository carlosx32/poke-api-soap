package com.pokeapi.webservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pokeapi.guides.pokemons_web_service.Pokemon;

import java.util.ArrayList;
import java.util.List;

@Component
public class PokemonsRepository {

	PokemonApiServices pokemonApiServices;

	@Autowired
	public PokemonsRepository(PokemonApiServices pokemonApiServices) {
		this.pokemonApiServices = pokemonApiServices;
	}

	public List<Pokemon> getPokemons(int page) {

		List<Pokemon> list = new ArrayList<>(pokemonApiServices.getPokemonsByApi(page).values());
		return list;

	}

    public String getPokemonInfoByName(String pokemonName) {
		return pokemonApiServices.getPokemonByName(pokemonName);
    }
}
