package com.example.klasha.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchangeDto {
    private String sourceCurrency;
    private String targetCurency;
    private String amount;
}
