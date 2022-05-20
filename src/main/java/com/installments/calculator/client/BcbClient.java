package com.installments.calculator.client;

import com.installments.calculator.dto.BcbDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "bcbClient", url = "${bcb-url}", configuration = ClientConfiguration.class)
public interface BcbClient {

    @GetMapping("bcdata.sgs.{codigo}/dados")
    List<BcbDto> getTaxFromBcB(@PathVariable("codigo") String codigo,
                               @RequestParam("formato") String formato,
                               @RequestParam("dataInicial") String dataInicial,
                               @RequestParam("dataFinal") String dataFinal);
}
