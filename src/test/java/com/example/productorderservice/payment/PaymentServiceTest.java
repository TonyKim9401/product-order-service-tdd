package com.example.productorderservice.payment;

import com.example.productorderservice.order.Order;
import com.example.productorderservice.product.DiscountPolicy;
import com.example.productorderservice.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class PaymentServiceTest {

    private PaymentService paymentService;
    private PaymentPort paymentPort;

    @BeforeEach
    void setUp() {
        paymentPort = new PaymentAdapter();
        paymentService = new PaymentService(paymentPort);
    }

    @Test
    void 상품주문() {
        final Long orderId = 1L;
        final String cardNumber = "1234-1234-1234-1234";
        final PaymentRequest request = new PaymentRequest(orderId, cardNumber);

        paymentService.payment(request);
    }

    private record PaymentRequest(Long orderId, String cardNumber) {
        private PaymentRequest {
                Assert.notNull(orderId, "주문 ID는 필수입니다.");
                Assert.hasText(cardNumber, "카드 번호는 필수입니다.");
        }
    }

    private class PaymentService {
        private final PaymentPort paymentPort;

        private PaymentService(PaymentPort paymentPort) {
            this.paymentPort = paymentPort;
        }

        public void payment(final PaymentRequest request) {
            Order order = paymentPort.getOrder(request.orderId);

            final Payment payment = new Payment(order, request.cardNumber());
            paymentPort.pay(payment);
            paymentPort.save(payment);
        }
    }

    private interface PaymentPort {
        Order getOrder(Long orderId);

        void pay(Payment payment);

        void save(Payment payment);
    }

    private record Payment(Order order, String cardNumber) {
        private Payment {
            Assert.notNull(order, "주문은 필수입니다.");
            Assert.hasText(cardNumber, "카드 번호는 필수입니다.");
        }
    }

    private class PaymentAdapter implements PaymentPort {

        private PaymentGateway paymentGateway;

        @Override
        public Order getOrder(Long orderId) {
            return new Order(new Product("상품1", 1000, DiscountPolicy.NONE), 2);
        }

        @Override
        public void pay(Payment payment) {
            paymentGateway.excute(payment);
        }

        @Override
        public void save(Payment payment) {

        }
    }

    private interface PaymentGateway {
        void excute(Payment payment);
    }

    public class ConsolePaymentGateway implements PaymentGateway {
        @Override
        public void excute(Payment payment) {
            System.out.println("결제완료");
        }
    }
}
