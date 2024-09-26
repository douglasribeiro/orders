package com.requests.service;

import com.requests.dto.DeleteDTO;
import com.requests.dto.OrderRequest;
import com.requests.dto.OrderResponse;
import com.requests.exception.NumberRecordExecedLimit;
import com.requests.exception.RecordNotFoundException;
import com.requests.mapper.OrderMapper;
import com.requests.model.Order;
import com.requests.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public List<OrderResponse> findAll(){
        List<Order> orders = orderRepository.findAll();
        return  orders.stream().map(orderMapper::toResponse).toList();
    }

    public List<OrderResponse> save(List<OrderRequest> orderRequest) {
        if(orderRequest.size() > 10) {
            throw new NumberRecordExecedLimit();
        }
        List<Order> result = orderRequest.stream().map(orderUnit ->
                orderRepository.save(orderMapper.toEntity(orderUnit))).toList();
        return result.stream().map(orderMapper::toResponse).toList();
    }

    public OrderResponse update(Long id, OrderRequest orderRequest) {
        Order obj = orderRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        obj.setPrice(orderRequest.price());
        obj.setCustomer(orderRequest.customer());
        obj.setQuantity(orderRequest.quantity());
        obj.setControl(orderRequest.control());
        obj.setProduct(orderRequest.product());
        obj.setValueTotal(orderRequest.quantity() > 10 ? (orderRequest.quantity() * orderRequest.price()) *.9
                : orderRequest.quantity() > 5 ? (orderRequest.quantity() * orderRequest.price()) * .95
                : orderRequest.quantity() * orderRequest.price());
        //Order orderUp = orderRepository.save(obj);
        return orderMapper.toResponse(orderRepository.save(obj));
    }

    public void delete(Long id) {
        orderRepository.delete(orderRepository.findById(id).orElseThrow(RecordNotFoundException::new));
    }

    public void delete(List<DeleteDTO> listDeleteDTO) {
        listDeleteDTO.forEach(obj ->
                orderRepository.delete(orderRepository.findById(obj.id()).orElseThrow(RecordNotFoundException::new)));

    }

    public OrderResponse findById(Long id) {
        OrderResponse response = orderMapper.toResponse(orderRepository.findById(id).orElseThrow(RecordNotFoundException::new));
        return response;
    }
}
