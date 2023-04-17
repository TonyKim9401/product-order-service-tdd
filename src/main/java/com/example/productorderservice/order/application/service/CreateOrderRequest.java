package com.example.productorderservice.order.application.service;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

record CreateOrderRequest(Long productId, int quantity) {
    CreateOrderRequest {
        notNull(productId, "상품 ID는 필수입니다.");
        isTrue(quantity > 0, "수량은 0보다 커야 합니다.");
    }
}
