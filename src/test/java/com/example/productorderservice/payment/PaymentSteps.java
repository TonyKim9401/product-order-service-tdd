package com.example.productorderservice.payment;

import com.example.productorderservice.payment.application.service.PaymentRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class PaymentSteps {
    public static PaymentRequest 주문결제요청_생성() {
        final Long orderId = 1L;
        final String cardNumber = "1234-1234-1234-1234";
        return new PaymentRequest(orderId, cardNumber);
    }

    static ExtractableResponse<Response> 주문결제요청(PaymentRequest request) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/payments")
                .then().log().all()
                .extract();
    }
}