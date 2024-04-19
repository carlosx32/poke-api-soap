package com.pokeapi.webservice;

import com.pokeapi.webservice.services.PokemonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pokeapi.guides.pokemons_web_service.GetAllPokemonRequest;
import pokeapi.guides.pokemons_web_service.GetAllPokemonResponse;
import pokeapi.guides.pokemons_web_service.GetPokemonInfoByNameResponse;
import pokeapi.guides.pokemons_web_service.GetPokemonInfoByNameRequest;

import java.util.Collection;

@Endpoint
public class PokemonEndpoint {
	private static final String NAMESPACE_URI = "http://pokeapi/guides/pokemons-web-service";

	private PokemonsRepository pokemonsRepository;

	@Autowired
	public PokemonEndpoint(PokemonsRepository pokemonsRepository) {
		this.pokemonsRepository = pokemonsRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPokemonRequest")
	@ResponsePayload
	public GetAllPokemonResponse getPokemon(@RequestPayload GetAllPokemonRequest request) {
		GetAllPokemonResponse response = new GetAllPokemonResponse();
		int page = request.getPage().intValue() * 10;
		Collection pokemons = pokemonsRepository.getPokemons( page );
		response.getPokemon().addAll(pokemons);
		return response;
	}



	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPokemonInfoByNameRequest")
	@ResponsePayload
	public pokeapi.guides.pokemons_web_service.GetPokemonInfoByNameResponse getPokemonInfoByNameRequest(@RequestPayload GetPokemonInfoByNameRequest request) {
		GetPokemonInfoByNameResponse response = new GetPokemonInfoByNameResponse();
		response.setJsonData(pokemonsRepository.getPokemonInfoByName(request.getName()));
		return response;
	}


}
