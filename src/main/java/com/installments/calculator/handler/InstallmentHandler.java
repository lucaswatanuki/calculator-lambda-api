package com.installments.calculator.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.installments.calculator.dto.InstallmentDto;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import java.util.List;

public class InstallmentHandler extends SpringBootRequestHandler<APIGatewayProxyRequestEvent, List<InstallmentDto>> {
}
