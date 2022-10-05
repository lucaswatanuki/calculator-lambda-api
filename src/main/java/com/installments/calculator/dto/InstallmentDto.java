package com.installments.calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
public class InstallmentDto {

    @JsonProperty(value = "mes")
    private String month;
    @JsonProperty(value = "ano")
    private Integer year;
    private BigDecimal total;
    @JsonProperty(value = "poupanca")
    private BigDecimal savingRate;
    @JsonProperty(value = "taxaReferencial")
    private BigDecimal tr;
}
