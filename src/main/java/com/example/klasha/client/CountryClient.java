package com.example.klasha.client;


import com.example.klasha.config.FeignClientConfig;
import com.example.klasha.dto.CapitalDTO;
import com.example.klasha.dto.CityPopulation;
import com.example.klasha.dto.ClientResponse;
import com.example.klasha.dto.CountryStateDTO;
import com.example.klasha.dto.CurrencyDTO;
import com.example.klasha.dto.LocationDTO;
import com.example.klasha.dto.PopulationDTO;
import com.example.klasha.dto.StateDTO;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@FeignClient(value = "country-client", url = "https://countriesnow.space/api/v0.1/countries", configuration = FeignClientConfig.class)
public interface CountryClient {

    @GetMapping("/population/q")
    ClientResponse<PopulationDTO> getPopulationData(@RequestParam("country") String country);

    @GetMapping("/positions/q")
    ClientResponse<LocationDTO> getLocationData(@RequestParam("country") String country);

    @GetMapping("/population/cities/filter/q")
    ClientResponse<List<CityPopulation>> getCitiesPopulations(@RequestParam("country") String country);


    @GetMapping("/capital/q")
    ClientResponse<CapitalDTO> getCapitalData(@RequestParam("country") String country);

    @GetMapping("/currency/q")
    ClientResponse<CurrencyDTO> getCurrencyData(@RequestParam("country") String country);

    @GetMapping("/state/cities/q")
    ClientResponse<List<String>> getCitiesData(@RequestParam("country") String country, @RequestParam("state") String state);

    @GetMapping("/states/q")
    ClientResponse<CountryStateDTO> getStateData(@RequestParam("country") String country);
}
