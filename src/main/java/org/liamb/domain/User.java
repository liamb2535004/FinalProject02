package org.liamb.domain;

import lombok.*;
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

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    /**
     * Borrows the specified item for the User.
     * @param item the item to be borrowed
     * @throws ItemUnavailableException Throws an exception if an item is not in the library.
     */
    public void borrowItem(Item item) throws ItemUnavailableException {
        //TODO unit test
        if (item.getStatus() != Item.ItemStatus.IN_STORE) {
            throw new ItemUnavailableException(
                    String.format("Invalid operation: the item %s is unavailable", item.getTitle()));
        }

        this.borrowedItems.add(item);
        item.setStatus(Item.ItemStatus.BORROWED);
    }

    /**
     * returns item from user's borrowed items list back to the library
     * @param item the item to be returned
     * @throws ItemNotBorrowedException if the item is not in the user's borrowed items list
     */
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
