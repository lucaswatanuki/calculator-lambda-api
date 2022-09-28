package com.installments.calculator.client;

import com.installments.calculator.dto.InstallmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "calculatorClient", url = "${url}", configuration = ClientConfiguration.class)
public interface CalculatorClient {

    @GetMapping("/api/v1/installments")
    List<InstallmentDto> getInstallments(@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
                                         @RequestParam("installments") Integer installments,
                                         @RequestParam("value") BigDecimal value);
}
