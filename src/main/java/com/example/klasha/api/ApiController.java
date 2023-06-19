package com.example.klasha.api;


import com.example.klasha.dto.CustomResponse;
import com.example.klasha.dto.ExchangeDto;
import com.example.klasha.service.KlashaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/klasha/countries")
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final KlashaService klashaService;

    @GetMapping
    public CustomResponse getCountryData(@RequestParam(value = "country", defaultValue = "Nigeria") String country){
        return klashaService.getCountryData(country);
    }

    @GetMapping("states")
    public CustomResponse getStatesData(@RequestParam(value = "country", defaultValue = "Nigeria") String country){
        return klashaService.getStates(country);
    }

    @GetMapping("exchange")
    public CustomResponse getExchangeData(ExchangeDto exchangeDto){
        return klashaService.getExchange(exchangeDto);
    }

    @GetMapping("cities")
    public CustomResponse getCities(@RequestParam(value = "numberOfCities", defaultValue = "10") int numberOfCities){
        return klashaService.getCitiesPopulation(numberOfCities);
    }
}
