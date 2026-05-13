package org.liamb.service;

import org.liamb.Exceptions.InvalidIsbnException;
import org.liamb.Exceptions.ItemUnavailableException;
import org.liamb.domain.*;
import org.liamb.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class LibraryManagementSystem {
    private static List<User> users = new ArrayList<>();
    private static List<Item> items = new ArrayList<>();

    public void loadCsvFiles() {
        //TODO unit test
        File itemsFile = new File(Constants.ITEMS_CSV_PATH);
        File usersFile = new File(Constants.USERS_CSV_PATH);

        try {
            try (Scanner scanner = new Scanner(itemsFile)) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String[] elements = line.split(",");

                    String type = elements[0];
                    String id = elements[1];
                    Item.ItemStatus status = elements[2].equalsIgnoreCase("borrowed")
                            ? Item.ItemStatus.BORROWED : (elements[2].equalsIgnoreCase("in_store")
                            ? Item.ItemStatus.IN_STORE : Item.ItemStatus.LOST);
                    String title = elements[3];

                    if (type.equalsIgnoreCase("book") && elements.length >= 7) {
                        try {
                            String isbn = elements[4];
                            String author = elements[5];
                            String genre = elements[6];
                            items.add(new Book(id, status, title, isbn, author, genre));
                        } catch (InvalidIsbnException e) {
                            System.out.println("skipped invalid book " + title + ": " + e.getMessage());
                        }
                    } else if (type.equalsIgnoreCase("dvd") && elements.length >= 6) {
                        String director = elements[4];
                        int duration = Integer.parseInt(elements[5]);
                        items.add(new DVD(id, status, title, director, duration));
                    } else if (type.equalsIgnoreCase("magazine") && elements.length >= 6) {
                        String issueNumber = elements[4];
                        String publisher = elements[5];
                        items.add(new Magazine(id, status, title, issueNumber, publisher));
                    }
                }
            }

            try (Scanner scanner = new Scanner(usersFile)) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String[] elements = line.split(",");

                    String type = elements[0];
                    String id = elements[1];
                    String name = elements[2];

                    User newUser = null;
                    switch (type.toLowerCase()) {
                        case "student" -> newUser = new Student(id, name);
                        case "teacher" -> newUser = new Teacher(id, name);
                        case "admin" -> newUser = new Admin(id, name);
                    }

                    if (newUser != null) {
                        if (elements.length > 3) {
                            for (int i = 3; i < elements.length; i++) {
                                String borrowedItemId = elements[i].trim();

                                for (Item item : items) {
                                    if (item.getId().equalsIgnoreCase(borrowedItemId)) {
                                        try {
                                            newUser.borrowItem(item);
                                        } catch (ItemUnavailableException e) {
                                            System.out.println("Warning: Could not load borrowed item " + name + ": " + e.getMessage());
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        users.add(newUser);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Warning: a CSV file is missing " + e.getMessage());
        }
    }

    public Item searchItemRecursive(String query) {
        //TODO write method, unit test and exception handling
    }

    public Item searchItemStream(String query) {
        //TODO write method, unit test and exception handling
    }

    public void sortUsers() {
        //TODO write method, unit test and exception handling
        //bubble
    }

    public void sortItems() {
        //TODO write method, unit test and exception handling
        //Insertion
    }
}
