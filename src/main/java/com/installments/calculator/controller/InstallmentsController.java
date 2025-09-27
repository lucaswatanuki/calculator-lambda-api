package com.installments.calculator.controller;

import com.installments.calculator.dto.InstallmentDto;
import com.installments.calculator.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class InstallmentsController {

    private final InstallmentService installmentService;

    @Autowired
    public InstallmentsController(InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @GetMapping("/api/v1/installments")
    public ResponseEntity<List<InstallmentDto>> getInstallments(@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
                                                                @RequestParam("installments") Integer installments,
                                                                @RequestParam("value") BigDecimal value) {
        return ResponseEntity.ok(installmentService.getInstallments(value, startDate, installments));
    }
}
