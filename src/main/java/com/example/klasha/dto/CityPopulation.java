package com.example.klasha.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityPopulation {
    private String city;
    private String country;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CityPopulationCount> populationCounts;

    public CityPopulationCount getRecentPopulation(){
        return this.populationCounts.get(0);
    }
}
