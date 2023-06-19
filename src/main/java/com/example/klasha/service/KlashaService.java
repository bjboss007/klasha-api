package com.example.klasha.service;


import com.example.klasha.client.CountryClient;
import com.example.klasha.dto.CapitalDTO;
import com.example.klasha.dto.CityPopulation;
import com.example.klasha.dto.CountryDataDto;
import com.example.klasha.dto.CountryStateDTO;
import com.example.klasha.dto.CurrencyDTO;
import com.example.klasha.dto.CurrencyExchangeDto;
import com.example.klasha.dto.CustomResponse;
import com.example.klasha.dto.ExchangeDto;
import com.example.klasha.dto.LocationDTO;
import com.example.klasha.dto.PopulationCount;
import com.example.klasha.dto.PopulationDTO;
import com.example.klasha.dto.StateDTO;
import com.example.klasha.helpers.FileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.klasha.helpers.GlobalResponseHandler.generic200Response;
import static com.example.klasha.helpers.GlobalResponseHandler.generic404NotFoundRequest;

@Service
@RequiredArgsConstructor
public class KlashaService {


    private final CountryClient client;
    private final FileReader fileReader;

    public CustomResponse getCountryData(String country){


        CurrencyDTO currencyData = client.getCurrencyData(country).getData();
        PopulationDTO populationData = client.getPopulationData(country).getData();
        LocationDTO locationData = client.getLocationData(country).getData();
        CapitalDTO capitalData = client.getCapitalData(country).getData();
        int size = populationData.getPopulationCounts().size();
        PopulationCount populationCount = populationData.getPopulationCounts().get(size - 1);

        CountryDataDto countryDataDto = CountryDataDto
                .builder()
                .capital(capitalData.getCapital())
                .currency(currencyData.getCurrency())
                .iso2(currencyData.getIso2())
                .iso3(currencyData.getIso3())
                .population(populationCount.getValue())
                .location(locationData)
                .build();

        return generic200Response("Country Data fetched!", countryDataDto);
    }

    public CustomResponse getStates(String country){
         CountryStateDTO stateDTOs = client.getStateData(country).getData();



        List<StateDTO> states = stateDTOs.getStates().stream()
                .peek(stateDTO -> {
                    List<String> cities = client.getCitiesData(country, getStateName(stateDTO)).getData();
                    stateDTO.setCites(cities);
                }).collect(Collectors.toList());

        return generic200Response("States fetched successfully", states);
    }

    public CustomResponse getExchange(ExchangeDto exchangeDto){
        CurrencyDTO sourceCountry = client.getCurrencyData(exchangeDto.getSourceCountry()).getData();


        String rateKey  = sourceCountry.getCurrency() + "-" + exchangeDto.getTargetCurrency();
        Double rate = fileReader.getRate(rateKey.toUpperCase());
        if(rate == null) return generic404NotFoundRequest("No exchange rate found for "+ rateKey);

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        double result = exchangeDto.getAmount() * rate;

        CurrencyExchangeDto currencyExchangeDto = CurrencyExchangeDto
                .builder()
                .sourceCurrency(sourceCountry.getCurrency())
                .targetCurency(exchangeDto.getTargetCurrency())
                .amount(decimalFormat.format(result))
                .build();

        return generic200Response("Exchange successfully done", currencyExchangeDto);

    }

    public CustomResponse getCitiesPopulation(int limit){
        List<String> countries = Arrays.asList("Ghana", "New Zealand", "Italy");
        List<CityPopulation> cities = countries.stream()
                .flatMap(name -> client.getCitiesPopulations(name).getData().parallelStream())
                .sorted(Comparator.comparingDouble(op -> -op.getRecentPopulation().getValue()))
                .limit(limit).toList();

        return generic200Response("Cities fetched", cities);
    }
    private String getStateName(StateDTO stateDTO) {
        return stateDTO.getName().equalsIgnoreCase("Lagos State") ? stateDTO.getName().split(" ")[0] : stateDTO.getName();
    }
}
