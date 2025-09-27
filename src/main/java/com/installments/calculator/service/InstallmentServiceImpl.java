package com.installments.calculator.service;

import com.installments.calculator.client.BcbClient;
import com.installments.calculator.dto.BcbDto;
import com.installments.calculator.dto.InstallmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class InstallmentServiceImpl implements InstallmentService{

    private final BcbClient bcbClient;
    private static final String SAVINGS_TAX = "196";
    private static final String RT = "7811";
    private static final String START_END_DATE_PATTERN = "dd/MM/yyyy";
    private static final String FORMAT = "json";

    final Predicate<BcbDto> isFirstDay = x -> x.getDate().getDayOfMonth() == 1;

    @Autowired
    public InstallmentServiceImpl(BcbClient bcbClient) {
        this.bcbClient = bcbClient;
    }

    @Override
    public List<InstallmentDto> getInstallments(BigDecimal value, LocalDate startDate, int installments) {
        final LocalDate endDate = startDate.plusMonths(installments > 0 ? installments - 1 : 0);

        final List<BcbDto> savingRateTax = getSavingRateTax(SAVINGS_TAX, startDate, endDate);
        final List<BcbDto> trTax = getSavingRateTax(RT, startDate, endDate);

        final List<InstallmentDto> monthlyInstallments = savingRateTax.stream()
                .filter(isFirstDay)
                .map(taxaDto -> InstallmentDto.builder()
                        .year(taxaDto.getDate().getYear())
                        .month(taxaDto.getDate().getMonth()
                                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                                .toUpperCase())
                        .savingRate(taxaDto.getValue().movePointLeft(2))
                        .build())
                .collect(Collectors.toList());

        for (int i = 0; i < monthlyInstallments.size(); i++) {
            if (i == 0) {
                monthlyInstallments.get(i)
                        .setTr(trTax.get(0).getValue());
                monthlyInstallments.get(i).setTotal(value
                        .add(monthlyInstallments.get(i)
                                .getSavingRate()
                                .multiply(value)
                                .add(trTax.get(0)
                                        .getValue()
                                        .movePointLeft(2)
                                        .multiply(value)))
                        .setScale(2, RoundingMode.HALF_EVEN));
            } else {
                monthlyInstallments.get(i)
                        .setTr(trTax.get(i).getValue());
                monthlyInstallments.get(i).setTotal(monthlyInstallments.get(i - 1)
                        .getTotal()
                        .add(monthlyInstallments.get(i)
                                .getSavingRate()
                                .multiply(value)
                                .add(trTax.get(i).getValue()
                                        .movePointLeft(2)
                                        .multiply(value)))
                        .setScale(2, RoundingMode.HALF_EVEN));
            }
        }

        return monthlyInstallments;
    }

    private List<BcbDto> getSavingRateTax(String taxType, LocalDate startDate, LocalDate endDate) {
        return bcbClient.getTaxFromBcB(taxType,
                FORMAT,
                startDate.format(DateTimeFormatter.ofPattern(START_END_DATE_PATTERN)),
                endDate.format(DateTimeFormatter.ofPattern(START_END_DATE_PATTERN)));
    }

}
