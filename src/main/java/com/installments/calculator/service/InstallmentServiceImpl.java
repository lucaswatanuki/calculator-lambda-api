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
    private static final String TAXA_POUPANCA = "196";
    private static final String TAXA_TR = "7811";

    final Predicate<BcbDto> isFirstDay = x -> x.getData().getDayOfMonth() == 1;

    @Autowired
    public InstallmentServiceImpl(BcbClient bcbClient) {
        this.bcbClient = bcbClient;
    }

    @Override
    public List<InstallmentDto> getInstallments(BigDecimal value, LocalDate startDate, int installments) {
        final LocalDate endDate = startDate.plusMonths(installments);

        List<BcbDto> poupancaTax = bcbClient.getTaxFromBcB(TAXA_POUPANCA,
                "json",
                startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        List<BcbDto> trTax = bcbClient.getTaxFromBcB(TAXA_TR,
                "json",
                startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        List<InstallmentDto> parcelasPorMes = poupancaTax.stream()
                .filter(isFirstDay)
                .map(taxaDto -> InstallmentDto.builder()
                        .year(taxaDto.getData().getYear())
                        .month(taxaDto.getData().getMonth()
                                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                                .toUpperCase())
                        .poupanca(taxaDto.getValor().movePointLeft(2))
                        .build())
                .collect(Collectors.toList());

        for (int i = 0; i < parcelasPorMes.size(); i++) {
            if (i == 0) {
                parcelasPorMes.get(i)
                        .setTr(trTax.get(0).getValor());
                parcelasPorMes.get(i).setTotal(value
                        .add(parcelasPorMes.get(i)
                                .getPoupanca()
                                .multiply(value)
                                .add(trTax.get(0)
                                        .getValor()
                                        .movePointLeft(2)
                                        .multiply(value)))
                        .setScale(2, RoundingMode.HALF_EVEN));
            } else {
                parcelasPorMes.get(i)
                        .setTr(trTax.get(i).getValor());
                parcelasPorMes.get(i).setTotal(parcelasPorMes.get(i - 1)
                        .getTotal()
                        .add(parcelasPorMes.get(i)
                                .getPoupanca()
                                .multiply(value)
                                .add(trTax.get(i).getValor()
                                        .movePointLeft(2)
                                        .multiply(value)))
                        .setScale(2, RoundingMode.HALF_EVEN));
            }
        }

        return parcelasPorMes;
    }

}
