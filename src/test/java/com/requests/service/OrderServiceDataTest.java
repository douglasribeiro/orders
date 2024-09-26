package com.requests.service;

import com.requests.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class OrderServiceDataTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void savaOrderTest(){
//        Order order = new Order();
//
//        orderRepository.save(order);
//
//        Assertions.assertThat(order.getId()).isGreaterThan(0);
    }
}
