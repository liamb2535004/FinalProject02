package org.liamb.domain;

import lombok.ToString;

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
    public void borrowItem(Item item) {
        //TODO borrow item with limit of 5 items
    }
}
