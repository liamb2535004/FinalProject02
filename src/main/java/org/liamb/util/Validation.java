package org.liamb.util;

import org.liamb.domain.Book;
import org.liamb.domain.Item;
import org.liamb.domain.Student;
import org.liamb.domain.Teacher;

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

        if (isbn.length() != 13) {
            return false;
        }

        for (int i = 0; i < 13; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * checks if the item is available to be borrowed
     * @param item item to be borrowed
     * @return true if available, false if unavailable
     */
    public static boolean isItemAvailable(Item item) {
        //TODO unit test
        return item != null && item.getStatus() == Item.ItemStatus.IN_STORE;
    }

    /**
     * checks if student can borrow book
     * @param student the student borrowing the book
     * @param item the item (book) to be borrowed
     * @return true if book can be borrowed, false if not
     */
    public static boolean canStudentBorrow(Student student, Item item) {
        //TODO unit test
        return (item instanceof Book) && (student.getBorrowedItems().size() < Constants.MAX_BOOKS_STUDENT);
    }

    /**
     * checks if teacher can borrow book
     * @param teacher the teacher borrowing the item
     * @return true if item can be borrowed, false if not
     */
    public static boolean canTeacherBorrow(Teacher teacher) {
        //TODO unit test
        return teacher.getBorrowedItems().size() < Constants.MAX_ITEMS_TEACHER;
    }
}
