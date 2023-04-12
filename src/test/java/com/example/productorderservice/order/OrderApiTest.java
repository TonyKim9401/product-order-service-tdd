package com.example.productorderservice.order;

import com.example.productorderservice.ApiTest;
import com.example.productorderservice.product.ProductSteps;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderApiTest extends ApiTest {

    /**
     * Spring Boot test
     */

    /*@Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;*/


    /*
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
    }*/

    @Test
    void 상품주문() {

        ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성());

        final var request = OrderSteps.상품주문요청_생성();

        final var response = OrderSteps.상품주문요청(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());


        /**
         * Spring Boot test
         */
//        productService.addProduct(ProductSteps.상품등록요청_생성());
//        orderService.createOrder(request);
    }

}
