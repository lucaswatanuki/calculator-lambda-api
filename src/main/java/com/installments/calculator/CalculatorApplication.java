package com.installments.calculator;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.installments.calculator.dto.InstallmentDto;
import com.installments.calculator.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

@SpringBootApplication
@EnableFeignClients
public class CalculatorApplication {

	@Autowired
	InstallmentService service;

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

	@Bean
	public Function<APIGatewayProxyRequestEvent, List<InstallmentDto>> getInstallments(){
		return (proxyRequestEvent) -> service.getInstallments(new BigDecimal(proxyRequestEvent.getQueryStringParameters().get("value")),
				LocalDate.parse(proxyRequestEvent.getQueryStringParameters().get("startDate"), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				Integer.getInteger(proxyRequestEvent.getQueryStringParameters().get("installments")));
	}

}
