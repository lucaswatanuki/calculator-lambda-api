package com.installments.calculator.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
public class InstallmentDto {

    private String month;
    private Integer year;
    private BigDecimal total;
    private BigDecimal poupanca;
    private BigDecimal tr;
}
