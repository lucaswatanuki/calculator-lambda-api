package com.installments.calculator.integration;

import com.installments.calculator.client.BcbClient;
import com.installments.calculator.client.CalculatorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(profiles = "local")
public abstract class IntegrationTestSupport {

    @Autowired
    BcbClient bcbClient;

    @Autowired
    CalculatorClient calculatorClient;
}
