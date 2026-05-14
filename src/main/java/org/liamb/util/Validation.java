package org.liamb.util;

public class Validation {
    /**
     * validates if the isbn adheres to the correct formatting
     * @param isbn the isbn to be validated
     * @return true if the isbn is valid, false if it is invalid
     */
    public static boolean isValidISBN(String isbn) {
        //TODO unit test
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        StringBuilder numsOnly = new StringBuilder();
        for (char c : isbn.toCharArray()) {
            if (c != '-' && c != ' ') {
                numsOnly.append(c);
            }
        }
        String cleanIsbn = numsOnly.toString();
        if (cleanIsbn.length() == 10) {
            return isValidISBN10(cleanIsbn);
        } else if (cleanIsbn.length() == 13) {
            return isValidISBN13(cleanIsbn);
        }
        return false;
    }

    private static boolean isValidISBN10(String isbn) {
        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
        }

        char last = isbn.charAt(9);
        if (!Character.isDigit(last) && last != 'X' && last != 'x') {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (10 - i) * (isbn.charAt(i) - '0');
        }
        sum += (last == 'X' || last == 'x') ? 10 : (last - '0');
        return sum % 11 == 0;
    }

    private static boolean isValidISBN13(String isbn) {
        for (int i = 0; i < 13; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
        }

        int sum = 0;
        for (int i = 0; i < 13; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        return sum % 10 == 0;
    }
}
