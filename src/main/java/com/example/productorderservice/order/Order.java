package com.example.productorderservice.order;

import com.example.productorderservice.product.Product;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

public class Order {
    private final Product product;
    private final int quantity;
    private Long id;

    Order(final Product product, final int quantity) {
        notNull(product, "상품은 필수입니다.");
        isTrue(quantity > 0, "수량은 0보다 커야 합니다.");
        this.product = product;
        this.quantity = quantity;
    }

    public void assignId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
