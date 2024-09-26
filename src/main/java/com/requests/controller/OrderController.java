package com.requests.controller;

import com.requests.dto.OrderRequest;
import com.requests.dto.OrderResponse;
import com.requests.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<OrderResponse>> findAll(){
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<OrderResponse>> save(@RequestBody List<OrderRequest> orderRequest){
        return  new ResponseEntity<>(orderService.save(orderRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody OrderRequest orderRequest){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        orderService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
