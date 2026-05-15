package org.liamb.domain;

import lombok.ToString;
import org.liamb.interfaces.Reportable;
import org.liamb.service.LibraryManagementSystem;
import org.liamb.util.Constants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString(callSuper = true)
public class Admin extends User implements Reportable {

    public Admin(String id, String name) {
        super(id, name);
    }

    /**
     * generates and prints a detailed report of all borrowed, in-store and lost items
     * @param libraryManagementSystem the system containing the list of all items
     */
    @Override
    public void generateReport(LibraryManagementSystem libraryManagementSystem) {
        System.out.println("--- LIBRARY REPORT ---");
        List<Item> items = libraryManagementSystem.getItems();

        Map<Item.ItemStatus, List<Item>> categorizedItems = new HashMap<>();
        categorizedItems.put(Item.ItemStatus.IN_STORE, new ArrayList<>());
        categorizedItems.put(Item.ItemStatus.BORROWED, new ArrayList<>());
        categorizedItems.put(Item.ItemStatus.LOST, new ArrayList<>());

        for (Item item : items) {
            categorizedItems.get(item.getStatus()).add(item);
        }

        System.out.println("Total Items In-Store: " + categorizedItems.get(Item.ItemStatus.IN_STORE).size());
        System.out.println("Total Items Borrowed: " + categorizedItems.get(Item.ItemStatus.BORROWED).size());
        System.out.println("Total Items Lost:     " + categorizedItems.get(Item.ItemStatus.LOST).size());
        System.out.println("--------------------------------");

        for (Item.ItemStatus status : Item.ItemStatus.values()) {
            System.out.println("--- " + status + " ITEMS ---");
            List<Item> itemsInStatus = categorizedItems.get(status);

            if (itemsInStatus.isEmpty()) {
                System.out.println("  (No items currently have this status)");
            } else {
                for (Item item : itemsInStatus) {
                    System.out.println("  - [" + item.getClass().getSimpleName() + "] "
                            + item.getTitle() + " (ID: " + item.getId() + ")");
                }
            }
            System.out.println();
        }
        System.out.println("================================\n");
    }

    /**
     * backs up all of the users and items data into the items.csv and users.csv files,
     * overwriting previous data.
     * @param libraryManagementSystem the system containing the lists of all users and items
     */
    public void backupData(LibraryManagementSystem libraryManagementSystem) {
        File itemsFile = new File(Constants.ITEMS_CSV_PATH);
        File usersFile = new File(Constants.USERS_CSV_PATH);
        List<Item> items = libraryManagementSystem.getItems();
        List<User> users = libraryManagementSystem.getUsers();

        try (FileWriter fileWriter = new FileWriter(itemsFile)) {
            for (Item item : items) {
                String type = item.getClass().getSimpleName().toLowerCase();
                String id = item.getId();
                String status = item.getStatus().toString();
                String title = item.getTitle();

                if (item instanceof Book) {
                    Book book = (Book) item;
                    fileWriter.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                            type, id, status, title, book.getIsbn(), book.getAuthor(), book.getGenre()));
                } else if (item instanceof DVD) {
                    DVD dvd = (DVD) item;
                    fileWriter.write(String.format("%s,%s,%s,%s,%s,%d\n",
                            type, id, status, title, dvd.getDirector(), dvd.getDuration()));
                } else if (item instanceof Magazine) {
                    Magazine magazine = (Magazine) item;
                    fileWriter.write(String.format("%s,%s,%s,%s,%s,%s\n",
                            type, id, status, title, magazine.getIssueNumber(), magazine.getPublisher()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter(usersFile)) {
            for (User user : users) {
                StringBuilder itemIds = new StringBuilder();
                for (Item item : user.getBorrowedItems()) {
                    if (item == user.getBorrowedItems().getLast()) {
                        itemIds.append(item.getId());
                    } else {
                        itemIds.append(item.getId()).append(",");
                    }
                }
                String type = user.getClass().getSimpleName().toLowerCase();
                String id = user.getId();
                String name = user.getName();
                fileWriter.write(String.format("%s,%s,%s,%s\n",
                        type, id, name, itemIds.toString()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
