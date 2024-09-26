package com.requests.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "order_table")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 10, unique = true)
    private String control;

    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dateOrder = LocalDate.now();

    @NotNull
    @Column(nullable = false, length = 100)
    private String product;

    @NotNull
    @Column(nullable = false)
    private Double price;

    private Integer quantity;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Customer customer;

    private Double valueTotal;

    @PrePersist
    public void prePersist() {
        if (quantity == null || quantity == 0) {
            quantity = 1;
        }
        valueTotal = quantity > 10 ? (quantity * price) * .9
            : quantity > 5 ? (quantity * price) * .95
            : quantity * price;
    }


//    public Order(Long id){
//        this.id = id;
//    }
//
//    public Order() {}
//
//    public Order(Long id, String control, LocalDate dateOrder, String product,
//                 Double price, int quantity, Customer customer, Double valueTotal) {
//        this.id = id;
//        this.control = control;
//        this.dateOrder = dateOrder;
//        this.product = product;
//        this.price = price;
//        this.quantity = quantity;
//        this.customer = customer;
//        this.valueTotal = valueTotal;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getControl() {
//        return control;
//    }
//
//    public void setControl(String control) {
//        this.control = control;
//    }
//
//    public LocalDate getDateOrder() {
//        return dateOrder;
//    }
//
//    public void setDateOrder(LocalDate dateOrder) {
//        this.dateOrder = dateOrder;
//    }
//
//    public String getProduct() {
//        return product;
//    }
//
//    public void setProduct(String product) {
//        this.product = product;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public Double getValueTotal() {
//        return valueTotal;
//    }
//
//    public void setValueTotal(Double valueTotal) {
//        this.valueTotal = valueTotal;
//    }
}
