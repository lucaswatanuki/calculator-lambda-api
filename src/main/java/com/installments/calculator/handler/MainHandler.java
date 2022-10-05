package com.installments.calculator.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import com.installments.calculator.config.SpringConfig;
import com.installments.calculator.dto.Response;
import com.installments.calculator.service.InstallmentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MainHandler extends AbstractHandler<SpringConfig> implements RequestHandler<APIGatewayProxyRequestEvent, Response> {
    @Override
    public Response handleRequest(APIGatewayProxyRequestEvent proxyRequestEvent, Context context) {
        InstallmentService service = getApplicationContext().getBean(InstallmentService.class);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        Gson parser = new Gson();
        return Response.builder()
                .statusCode(200)
                .headers(headerMap)
                .isBase64Encoded(false)
                .body(parser.toJson(service.getInstallments(new BigDecimal(proxyRequestEvent.getQueryStringParameters().get("value")),
                        LocalDate.parse(proxyRequestEvent.getQueryStringParameters().get("startDate"), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        Integer.parseInt(proxyRequestEvent.getQueryStringParameters().get("installments")))))
                .build();
    }
}
