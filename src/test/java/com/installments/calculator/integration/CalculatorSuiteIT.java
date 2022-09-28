package com.installments.calculator.integration;

import com.installments.calculator.dto.InstallmentDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorSuiteIT extends IntegrationTestSupport{

    @Test
    public void shouldGetInstallments() {
        final LocalDate startTime = LocalDate.of(2021, 1, 1);

        final List<InstallmentDto> installments = this.calculatorClient
                .getInstallments(startTime, 12, new BigDecimal(2000));

        assertThat(installments).isNotNull();
        assertThat(installments.size()).isEqualTo(12);
        assertThat(installments).extracting(InstallmentDto::getYear).containsOnly(2021);
    }
}
