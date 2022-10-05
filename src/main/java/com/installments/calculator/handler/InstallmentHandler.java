package com.installments.calculator.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.installments.calculator.dto.InstallmentDto;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import java.util.List;

public class InstallmentHandler extends SpringBootRequestHandler<APIGatewayV2HTTPEvent, List<InstallmentDto>> {
}
