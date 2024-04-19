package com.pokeapi.webservice.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pokeapi.guides.pokemons_web_service.Pokemon;

import java.util.HashMap;
import java.util.Map;

@Service
public class PokemonApiServices {
    private RestTemplate restTemplate;
    private String baseURL;
    private final String URL_FORMAT= "%s?limit=%d&offset=%d";
    private final String URL_BY_NAME_FORMAT = "%s/%s";


    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${pokemon.api.endpoint}")
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public Map<String, Pokemon> getPokemonsByApi(int items) {
        String finalUrl = String.format(URL_FORMAT, baseURL, items,items );
        ResponseEntity<JsonNode> result = restTemplate.exchange(finalUrl, HttpMethod.GET, null, JsonNode.class );
        return mapPokemons(result.getBody());
    }

    public Map<String, Pokemon> mapPokemons(JsonNode result){
        Map<String, Pokemon> pokemons = new HashMap<>();
        JsonNode results = result.get("results");
        for (JsonNode pokemon: results ) {
           System.out.println(pokemon.asText());
           Pokemon newPokemon = new Pokemon();
           newPokemon.setName(pokemon.get("name").asText());
           newPokemon.setUrl(pokemon.get("url").asText());
           pokemons.put(newPokemon.getName(), newPokemon);
        }
        return pokemons;
    }

    public String getPokemonByName(String pokemonName) {

        String finalUrl = String.format(URL_BY_NAME_FORMAT, baseURL, pokemonName );
        ResponseEntity<JsonNode> result = restTemplate.exchange(finalUrl, HttpMethod.GET, null, JsonNode.class );
        return result.getBody().toString();

    }
}