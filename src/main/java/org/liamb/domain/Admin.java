package org.liamb.domain;

import lombok.ToString;
import org.liamb.interfaces.Reportable;
import org.liamb.service.LibraryManagementSystem;
import org.liamb.util.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@ToString(callSuper = true)
public class Admin extends User implements Reportable {

    public Admin(String name) {
        super(name);
    }

    public Admin(String id, String name) {
        super(id, name);
    }

    @Override
    public void generateReport() {
        //TODO write method, unit test and exception handling
    }

    public void backupData(LibraryManagementSystem libraryManagementSystem) {
        //TODO unit test
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
