package com.requests.exception;

public class NumberRecordExecedLimit  extends  RuntimeException{

    public NumberRecordExecedLimit() {
        super("Number of orders is max 10");
    }
}
