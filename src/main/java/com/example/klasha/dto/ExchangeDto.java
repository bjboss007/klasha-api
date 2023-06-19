package com.example.klasha.dto;


import lombok.Data;

@Data
public class ExchangeDto {
    private String sourceCountry;
    private String targetCurrency;
    private Double amount;
}
