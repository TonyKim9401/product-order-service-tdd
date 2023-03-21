package com.example.productorderservice.order;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
class OrderRepository {


    private Map<Long, Order> persistence = new HashMap<>();
    private Long sequence = 0L;

    public void save(final Order order) {
        order.assignId(++sequence); // 주문시 id 값을 1씩 증가시킴
        persistence.put(order.getId(), order);
    }
}
