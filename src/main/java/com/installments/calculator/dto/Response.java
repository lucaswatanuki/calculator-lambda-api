package com.installments.calculator.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class Response implements Serializable {
    Integer statusCode;
    Object body;
    Map<String, String> headers;
    Boolean isBase64Encoded;


}
