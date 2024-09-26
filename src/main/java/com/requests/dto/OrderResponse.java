package com.requests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.requests.model.Customer;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

public record OrderResponse(
        Long id,

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
        Customer customer,

        Double valueTotal
) {
}
