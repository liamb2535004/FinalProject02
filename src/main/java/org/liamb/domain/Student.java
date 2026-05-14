package org.liamb.domain;

import lombok.ToString;
import org.liamb.Exceptions.ItemUnavailableException;
import org.liamb.util.Constants;
import org.liamb.util.Validation;

@ToString(callSuper = true)
public class Student extends User{

    public Student(String id, String name) {
        super(id, name);
    }

    /**
     * Borrows the specified book for the Student,
     * allowing up to 5 books to be borrowed.
     * @param item the item to be borrowed (students may only borrow books)
     * @throws ItemUnavailableException Throws an exception if
     * Student attempts to borrow more than 5 books or another item type, or if item is not in the library.
     */
    @Override
    public void borrowItem(Item item) throws ItemUnavailableException {
        //TODO unit test
        if (!Validation.isItemAvailable(item)) {
            throw new ItemUnavailableException("Invalid operation: This item is currently not available.");
        }
        if (!Validation.canStudentBorrow(this, item)) {
            throw new RuntimeException("Invalid operation: Borrowing limit reached or invalid item type.");
        }

        super.borrowItem(item);
    }
}
