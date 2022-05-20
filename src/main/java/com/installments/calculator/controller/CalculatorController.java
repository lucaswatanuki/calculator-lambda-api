package com.installments.calculator.controller;

import com.installments.calculator.dto.InstallmentDto;
import com.installments.calculator.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/installments")
public class CalculatorController {

    private final InstallmentService service;

    @Autowired
    public CalculatorController(InstallmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<InstallmentDto> getInstallments(@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
                                                @RequestParam("installments") Integer installments,
                                                @RequestParam("value") BigDecimal value) {
        return service.getInstallments(value, startDate, installments);
    }
}
