package org.liamb.domain;

import lombok.ToString;
import org.liamb.Exceptions.ItemUnavailableException;
import org.liamb.util.Constants;

import java.util.List;

@ToString(callSuper = true)
public class Student extends User{

    public Student(String name) {
        super(name);
    }

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
        if (!(item instanceof Book)) {
            throw new ItemUnavailableException("Invalid operation: Students may only borrow books");
        }
        if (this.borrowedItems.size() >= Constants.MAX_BOOKS_STUDENT) {
            throw new ItemUnavailableException("Invalid operation: student has reached maximum borrow limit");
        }
        if (item.getStatus() != Item.ItemStatus.IN_STORE) {
            throw new ItemUnavailableException(
                    String.format("Invalid operation: the item %s is unavailable", item.getTitle()));
        }

        super.borrowItem(item);
    }
}
