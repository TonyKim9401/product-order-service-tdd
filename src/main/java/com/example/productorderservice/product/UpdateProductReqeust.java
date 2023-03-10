package com.example.productorderservice.product;

import static org.springframework.util.Assert.*;

record UpdateProductRequest(
        String name,
        int price,
        DiscountPolicy discountPolicy) {
    UpdateProductRequest {
        hasText(name, "상품명은 필수입니다.");
        isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
        notNull(discountPolicy, "할인 정책은 필수입니다.");
    }
}
