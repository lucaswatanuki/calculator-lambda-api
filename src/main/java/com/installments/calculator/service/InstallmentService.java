package com.installments.calculator.service;

import com.installments.calculator.dto.InstallmentDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InstallmentService {

    List<InstallmentDto> getInstallments(BigDecimal value, LocalDate startDate, int installments);
}
