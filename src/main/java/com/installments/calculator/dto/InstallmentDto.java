package com.installments.calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
public class InstallmentDto {

    @JsonProperty("mes")
    private String month;
    @JsonProperty("ano")
    private Integer year;
    private BigDecimal total;
    @JsonProperty("poupanca")
    private BigDecimal savingRate;
    @JsonProperty("taxaReferencial")
    private BigDecimal tr;
}
