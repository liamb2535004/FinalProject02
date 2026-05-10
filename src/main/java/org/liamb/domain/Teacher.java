package org.liamb.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@ToString(callSuper = true)
public class Teacher extends User{

    public Teacher(String name) {
        super(name);
    }

    public Teacher(String id, String name, List<Item> borrowedItems) {
        super(id, name, borrowedItems);
    }

    @Override
    public void borrowItem(Item item) {
        //TODO borrow item with limit of 10 items
    }
}
