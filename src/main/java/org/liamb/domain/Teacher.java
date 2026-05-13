package org.liamb.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.liamb.Exceptions.ItemUnavailableException;
import org.liamb.util.Constants;

import java.util.List;

@ToString(callSuper = true)
public class Teacher extends User{

    public Teacher(String name) {
        super(name);
    }

    public Teacher(String id, String name) {
        super(id, name);
    }

    @Override
    public void borrowItem(Item item) throws ItemUnavailableException {
        //TODO unit test
        if (this.borrowedItems.size() >= Constants.MAX_ITEMS_TEACHER) {
            throw new ItemUnavailableException("Invalid operation: teacher has reached maximum borrow limit");
        }

        super.borrowItem(item);
    }
}
