package com.requests.mapper;

import com.requests.dto.OrderRequest;
import com.requests.dto.OrderResponse;
import com.requests.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }
        return new OrderResponse(
                order.getId(),
                order.getControl(),
                order.getDateOrder(),
                order.getProduct(),
                order.getPrice(),
                order.getQuantity(),
                order.getCustomer(),
                order.getValueTotal());    }

    public Order toEntity(OrderRequest orderRequest) {
        if (orderRequest == null) {
            return null;
        }
        return new Order(
            null,
            orderRequest.control(),
            orderRequest.dateOrder(),
            orderRequest.product(),
            orderRequest.price(),
            orderRequest.quantity(),
            orderRequest.customer(),
             orderRequest.quantity() > 10  ? (orderRequest.quantity() * orderRequest.price()) * .9
                     : orderRequest.quantity() > 5 ? (orderRequest.quantity() * orderRequest.price()) * .95
                     : orderRequest.quantity() * orderRequest.price());
    }
}
