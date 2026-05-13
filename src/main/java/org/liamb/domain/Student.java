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

    public Student(String id, String name, List<Item> borrowedItems) {
        super(id, name, borrowedItems);
    }

    @Override
    public void borrowItem(Item item) throws ItemUnavailableException {
        //TODO borrow item with limit of 5 items, unit test and exception handling
        if (!(item instanceof Book)) {
            throw new ItemUnavailableException("Invalid operation: Students may only borrow books");
        }
        if (this.borrowedItems.size() >= Constants.MAX_BOOKS_STUDENT) {
            throw new ItemUnavailableException("Invalid operation: student has reached maximum borrow limit");
        }

        super.borrowItem(item);
    }
}
