package com.example.productorderservice.product.application.service;

import com.example.productorderservice.product.domain.DiscountPolicy;

import static org.springframework.util.Assert.*;

public record AddProductRequest(String name, int price, DiscountPolicy discountPolicy) {
    public AddProductRequest {
        hasText(name, "상품명은 필수입니다.");
        isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
        notNull(discountPolicy, "할인 정책은 필수입니다.");
    }

}
