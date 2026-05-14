package org.liamb.domain;

import lombok.ToString;
import org.liamb.Exceptions.ItemUnavailableException;
import org.liamb.util.Constants;
import org.liamb.util.Validation;

@ToString(callSuper = true)
public class Teacher extends User{

    public Teacher(String id, String name) {
        super(id, name);
    }
    /**
     * Borrows the specified item for the Teacher,
     * allowing up to 10 items to be borrowed.
     * @param item the item to be borrowed
     * @throws ItemUnavailableException Throws an exception if
     * teacher attempts to borrow more than 10 items or an item is not in the library.
     */
    @Override
    public void borrowItem(Item item) throws ItemUnavailableException {
        //TODO unit test
        if (!Validation.isItemAvailable(item)) {
            throw new ItemUnavailableException(
                    String.format("Invalid operation: the item %s is unavailable", item.getTitle()));
        }
        if (!Validation.canTeacherBorrow(this)) {
            throw new ItemUnavailableException("Invalid operation: teacher has reached maximum borrow limit");
        }

        super.borrowItem(item);
    }
}
