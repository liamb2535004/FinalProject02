package org.liamb.Exceptions;

public class InvalidIsbnException extends RuntimeException {
    public InvalidIsbnException(String badIsbn) {
        super(determineMessage(badIsbn));
    }

    private static String determineMessage(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return "Invalid operation: ISBN cannot be null or empty";
        }
        String cleanIsbn = isbn.replace("-", "");

        for (char c : cleanIsbn.toCharArray()) {
            if (!(Character.isDigit(c) || c == 'X' || c == 'x')) {
                return "Invalid operation: ISBN must contain only dashes, numbers and/or X";
            }
        }

        if (cleanIsbn.length() != 10 && cleanIsbn.length() != 13) {
            return "Invalid operation: ISBN must contain exactly 10 or 13 digits";
        } else {
            return "Invalid operation: ISBN format is incorrect";
        }
    }
}
