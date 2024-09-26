package com.requests.exception;

public class RecordNotFoundException extends  RuntimeException{

    public RecordNotFoundException() {
        super("Record not found.");
    }
}
