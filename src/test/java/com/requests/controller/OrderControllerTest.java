package com.requests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.requests.dto.OrderRequest;
import com.requests.dto.OrderResponse;
import com.requests.model.Customer;
import com.requests.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnTheRecordOfTheInformedIdentifier() throws Exception {
        Long id = 1L;
        OrderResponse order = new OrderResponse
                (1L, "ctrl", null, "product", 1.5, 1, new Customer(1l, "name"), null);
        when(orderService.findById(id)).thenReturn(order);
        mockMvc.perform(get("/api/order/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andDo(print());
    }


    @Test
    void shouldReturnListOfOrders() throws Exception {
        List<OrderResponse> orders = new ArrayList<>(
        Arrays.asList(
            new OrderResponse(1L, "ctrl", null, "product", 1.5, 1, new Customer(), null),
            new OrderResponse(2L, "ctrl", null, "product", 1.5, 1, new Customer(), null),
            new OrderResponse(3L, "ctrl", null, "product", 1.5, 1, new Customer(), null)));
        when(orderService.findAll()).thenReturn(orders);
        mockMvc.perform(get("/api/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(orders.size()))
                .andDo(print());
    }

    @Test
    void shouldCreateNewOrder() throws Exception {
        List<OrderResponse> orders = new ArrayList<>(
                Arrays.asList(
                        new OrderResponse(1L, "ctrl", null, "product", 1.5, 1, new Customer(), null),
                        new OrderResponse(2L, "ctrl", null, "product", 1.5, 1, new Customer(), null),
                        new OrderResponse(3L, "ctrl", null, "product", 1.5, 1, new Customer(), null)));

        mockMvc.perform(post("/api/order").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orders)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldUpdateRecord() throws Exception {
        long id = 1L;

        OrderRequest request = new OrderRequest(
                "ctrl", null, "product", 1.5, 1, new Customer());
        OrderResponse order = new OrderResponse
                (1L, "ctrl", null, "product", 1.5, 1, new Customer(), null);
        OrderResponse orderUpdated = new OrderResponse
                (1L, "ctrl updated", null, "product", 3.0, 5, new Customer(), null);

        when(orderService.findById(id)).thenReturn(order);
        when(orderService.update(id, request)).thenReturn(orderUpdated);

        mockMvc.perform(patch("/api/order/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldDeleteRecord() throws Exception {
        long id = 1L;

        doNothing().when(orderService).delete(id);
        mockMvc.perform(delete("/api/order/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void shouldReturnOk() throws Exception {

        mockMvc.perform(get("/api/order/hello"))
                .andDo(print());
    }

}