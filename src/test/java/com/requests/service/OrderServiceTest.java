package com.requests.service;

import com.requests.dto.DeleteDTO;
import com.requests.dto.OrderRequest;
import com.requests.dto.OrderResponse;
import com.requests.exception.NumberRecordExecedLimit;
import com.requests.exception.RecordNotFoundException;
import com.requests.mapper.OrderMapper;
import com.requests.model.Customer;
import com.requests.model.Order;
import com.requests.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    public static final long ID = 1L;

    @Autowired
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private RecordNotFoundException recordNotFoundException;

    @BeforeEach
    public void setup(){

        MockitoAnnotations.initMocks(this);

    }


    @Test
    void findAll() {

        List<Order> orders = new ArrayList<>(
            Arrays.asList(
                new Order(1L, "ctrl", null, "product", 1.5, 1, new Customer(), null),
                new Order(2L, "ctrl", null, "product", 1.5, 1, new Customer(), null),
                new Order(3L, "ctrl", null, "product", 1.5, 1, new Customer(), null)));

        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderResponse> result = orderService.findAll();

        assertEquals(3, result.size());

    }

    @Test
    void savesuccess() {
        List<OrderRequest> orderRequests = new ArrayList<>(
            Arrays.asList(
                new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                new OrderRequest("ctrl", null, "product", 1.5, 6, new Customer()),
                new OrderRequest("ctrl", null, "product", 1.5, 11, new Customer())));

        List<OrderResponse> orderResponses = new ArrayList<>(
            Arrays.asList(
                new OrderResponse(1L, "ctrl", null, "product", 1.5, 1, new Customer(), null),
                new OrderResponse(2L, "ctrl", null, "product", 1.5, 6, new Customer(), null),
                new OrderResponse(3L, "ctrl", null, "product", 1.5, 11, new Customer(), null))
        );

        List<OrderResponse> result = orderService.save(orderRequests);

        assertEquals(orderResponses.size(), 3);
        assertTrue(orderResponses.get(0).id() == 1);
        assertTrue(orderResponses.get(1).id() == 2);
        assertTrue(orderResponses.get(2).id() == 3);

    }

    @Test
     void savefail() {
        List<OrderRequest> orderRequests = new ArrayList<>(
                Arrays.asList(
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer()),
                        new OrderRequest("ctrl", null, "product", 1.5, 1, new Customer())));

        Assertions.assertThrows(NumberRecordExecedLimit.class, () -> {
            orderService.save(orderRequests);
        });

    }

    @Test
    void update() {
        OrderRequest request = new OrderRequest("ctrl alterado", null, "product", 1.5, 1, new Customer());
        Order order = new Order(ID, "ctrl input", null, "product", 1.5, 1, new Customer(), null);
        OrderResponse response = new OrderResponse(1L,"ctrl alterado", LocalDate.now(), "product", 1.5, 1, new Customer(), 0D);

        when(orderRepository.findById(ID)).thenReturn(Optional.of(order));
        when(orderService.update(ID,request)).thenReturn(response);
        when(orderMapper.toResponse(order)).thenReturn(response);

        verify(orderRepository, times(1)).findById(ID);

        Assertions.assertEquals("ctrl alterado", response.control());
    }

    @Test
    void updateQuantityGreaterThan10() {
        OrderRequest request = new OrderRequest("ctrl alterado", null, "product", 1.5, 11, new Customer());
        Order order = new Order(ID, "ctrl input", null, "product", 1.5, 11, new Customer(), null);
        when(orderRepository.findById(ID)).thenReturn(Optional.of(order));

        orderService.update(ID, request);
        assertEquals(true, order.getQuantity() > 10);

    }

    @Test
    void updateQuantityGreaterThan5() {
        OrderRequest request = new OrderRequest("ctrl alterado", null, "product", 1.5, 6, new Customer());
        Order order = new Order(ID, "ctrl input", null, "product", 1.5, 6, new Customer(), null);
        when(orderRepository.findById(ID)).thenReturn(Optional.of(order));

        orderService.update(ID, request);
        assertEquals(true, order.getQuantity() > 5);

    }

    @Test
    void updateQuantityLessThan5() {
        OrderRequest request = new OrderRequest("ctrl alterado", null, "product", 1.5, 1, new Customer());
        Order order = new Order(ID, "ctrl input", null, "product", 1.5, 1, new Customer(), null);
        when(orderRepository.findById(ID)).thenReturn(Optional.of(order));

        orderService.update(ID, request);
        assertEquals(true, order.getQuantity() < 5);

    }

    @Test
    void delete() {
        Order order = new Order(ID, "ctrl input", null, "product", 1.5, 1, new Customer(), null);
        when(orderRepository.findById(ID)).thenReturn(Optional.of(order));
        orderService.delete(ID);
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void testDelete() {
        Order order = new Order(ID, "ctrl input", null, "product", 1.5, 1, new Customer(), null);
        List<DeleteDTO> listDelete = new ArrayList<>(Arrays.asList(
                new DeleteDTO(1L),
                new DeleteDTO(2L)
        ));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));

        orderService.delete(listDelete);
        verify(orderRepository, times(2)).delete(order);
    }

    @Test
    void testDeleteFail() {
        Order order = new Order(ID, "ctrl input", null, "product", 1.5, 1, new Customer(), null);
        List<DeleteDTO> listDelete = new ArrayList<>(Arrays.asList(
                new DeleteDTO(1L),
                new DeleteDTO(3L)
        ));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));

        //orderService.delete(listDelete);
        Assertions.assertThrows(RecordNotFoundException.class, () -> orderService.delete(listDelete));
    }

    @Test
    void findById() {
        Order order = new Order(ID, "ctrl input", null, "product", 1.5, 1, new Customer(), null);
        OrderResponse response = new OrderResponse(1L,"ctrl input", LocalDate.now(), "product", 1.5, 1, new Customer(), 0D);
        when(orderRepository.findById(ID)).thenReturn(Optional.of(order));
        when(orderMapper.toResponse(order)).thenReturn(response);
        OrderResponse result = orderService.findById(ID);

        assertEquals(1, result.id());
    }


}