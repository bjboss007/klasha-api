package com.example.klasha.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDataDto {

    private Long population;
    private String capital;
    private LocationDTO location;
    private String currency;
    private String iso2;
    private String iso3;
}

