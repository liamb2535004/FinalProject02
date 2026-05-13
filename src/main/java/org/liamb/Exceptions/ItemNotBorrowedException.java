package org.liamb.Exceptions;

public class ItemNotBorrowedException extends RuntimeException {
    public ItemNotBorrowedException(String message) {
        super(message);
    }
}
