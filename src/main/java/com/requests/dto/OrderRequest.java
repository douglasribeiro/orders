package com.requests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.requests.model.Customer;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

public record OrderRequest(
        @NotNull
        String control,

        @JsonFormat(pattern="dd/MM/yyyy")
        LocalDate dateOrder,

        @NotNull
        String product,

        @NotNull
        Double price,

        int quantity,

        @NotNull
        Customer customer
) {
        public OrderRequest {
                if (dateOrder == null){
                        dateOrder = LocalDate.now();
                }
//                valueTotal = quantity > 5 ? (price * quantity) * .95
//                        : quantity > 10 ? (price * quantity) * .90
//                        : price * quantity;
        }
}
