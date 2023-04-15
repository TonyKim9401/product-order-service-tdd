package com.example.productorderservice.payment;

import com.example.productorderservice.order.Order;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.security.spec.ECParameterSpec;

class PaymentServiceTest {

    private PaymentService paymentService;

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
        private PaymentPort paymentPort;

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
}
