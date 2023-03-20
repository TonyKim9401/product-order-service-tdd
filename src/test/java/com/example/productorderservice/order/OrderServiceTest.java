package com.example.productorderservice.order;

import com.example.productorderservice.product.DiscountPolicy;
import com.example.productorderservice.product.Product;
import com.example.productorderservice.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

public class OrderServiceTest {

    private OrderService orderService;

    private OrderPort orderPort;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
        orderPort = new OrderPort() {
            @Override
            public Product getProductById(Long productId) {
                return new Product("상품명", 1000, DiscountPolicy.NONE);
            }

            @Override
            public void save(Order order) {
                orderRepository.save(order);
            }
        };
        orderService = new OrderService(orderPort);
    }

    @Test
    void 상품주문() {

        final Long productId = 1L;
        final int quantity = 2;
        final CreateOrderRequest request = new CreateOrderRequest(productId, quantity);
        orderService.createOrder(request);
    }

    private record CreateOrderRequest(Long productId, int quantity) {
            private CreateOrderRequest {
                notNull(productId, "상품 ID는 필수입니다.");
                isTrue(quantity > 0, "수량은 0보다 커야 합니다.");
            }
        }


    private class OrderService {

        private final OrderPort orderPort;

        public OrderService(final OrderPort orderPort) {
            this.orderPort = orderPort;
        }

        public void createOrder(final CreateOrderRequest request) {
            final Product product = orderPort.getProductById(request.productId);

            final Order order = new Order(product, request.quantity());

            orderPort.save(order);
        }
    }

    private class OrderAdapter implements OrderPort{
        private final ProductRepository productRepository;
        private final OrderRepository orderRepository;

        public OrderAdapter(ProductRepository productRepository, OrderRepository orderRepository) {
            this.productRepository = productRepository;
            this.orderRepository = orderRepository;
        }

        @Override
        public Product getProductById(final Long productId) {
            return productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        }

        @Override
        public void save(Order order) {
            orderRepository.save(order);
        }
    }

    private class Order {
        private final Product product;
        private final int quantity;
        private Long id;

        private Order(final Product product, final int quantity) {
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

    private class OrderRepository {


        private Map<Long, Order> persistence = new HashMap<>();
        private Long sequence = 0L;

        public void save(final Order order) {
            order.assignId(++sequence); // 주문시 id 값을 1씩 증가시킴
            persistence.put(order.getId(), order);
        }
    }

    private interface OrderPort {
        Product getProductById(final Long productId);
        void save(Order order);
    }
}
