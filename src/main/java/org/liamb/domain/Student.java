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

    @Override
    public void borrowItem(Item item) throws ItemUnavailableException {
        //TODO unit test
        if (!(item instanceof Book)) {
            throw new ItemUnavailableException("Invalid operation: Students may only borrow books");
        }
        if (this.borrowedItems.size() >= Constants.MAX_BOOKS_STUDENT) {
            throw new ItemUnavailableException("Invalid operation: student has reached maximum borrow limit");
        }

        super.borrowItem(item);
    }
}
