package org.liamb.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.liamb.Exceptions.ItemNotBorrowedException;
import org.liamb.Exceptions.ItemUnavailableException;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public abstract class User {
    protected String id;
    protected String name;
    protected List<Item> borrowedItems;

    private static int nextId = 1;

    public User(String name) {
        this.id = String.format("%04d", nextId++);
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }
    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    public void borrowItem(Item item) throws ItemUnavailableException {
        //TODO unit test
        if (item.getStatus() != Item.ItemStatus.IN_STORE) {
            throw new ItemUnavailableException(
                    String.format("Invalid operation: the item %s is unavailable", item.getTitle()));
        }

        this.borrowedItems.add(item);
        item.setStatus(Item.ItemStatus.BORROWED);
    }

    public void returnItem(Item item) throws ItemNotBorrowedException {
        //TODO unit test
        if (!this.borrowedItems.contains(item)) {
            throw new ItemNotBorrowedException(
                    String.format("Invalid operation: the item %s has not been borrowed", item.getTitle()));
        }
        this.borrowedItems.remove(item);
        item.setStatus(Item.ItemStatus.IN_STORE);
    }
}
