package org.liamb.service;

import lombok.Getter;
import org.liamb.Exceptions.InvalidIsbnException;
import org.liamb.Exceptions.ItemUnavailableException;
import org.liamb.domain.*;
import org.liamb.util.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
public class LibraryManagementSystem {
    private List<User> users = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    /**
     * reads the data from the users.csv and items.csv files and
     * initializes the users and items lists in the system
     */
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

    /**
     * Recursively searches for items matching the query by title or author (case-insensitive).
     * If multiple copies exist, the result contains only one distinct copy.
     * @param query the title or author to search for
     * @return a list of items containing the query in the title or author name
     */
    public List<Item> searchItemRecursive(String query) {
        //TODO unit test
        List<Item> results = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return results;
        }
        recursiveHelper(query.toLowerCase(), results, 0);
        return results;
    }

    private void recursiveHelper(String query, List<Item> results, int index) {
        if (index >= items.size()) {
            return;
        }

        Item currentItem = items.get(index);
        boolean isMatch = false;

        if (currentItem.getTitle().toLowerCase().contains(query)) {
            isMatch = true;
        } else if (currentItem instanceof Book) {
            Book book = (Book) currentItem;
            if (book.getAuthor().toLowerCase().contains(query)) {
                isMatch = true;
            }
        }

        if (isMatch) {
            boolean alreadyExists = false;

            for (Item existingItem: results) {
                if (existingItem.getClass().equals(currentItem.getClass()) &&
                existingItem.getTitle().equalsIgnoreCase(currentItem.getTitle())) {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                results.add(currentItem);
            }
        }
        recursiveHelper(query, results, index + 1);
    }

    /**
     * searches for items matching the query by title or author (case-insensitive) by using Java Stream
     * If multiple copies exist, the result contains only one distinct copy.
     * @param query the title or author to search for
     * @return a list of items containing the query in the title or author name
     */
    public List<Item> searchItemStream(String query) {
        //TODO unit test
        List<Item> results = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String lowerQuery = query.toLowerCase();

        items.stream()
                .filter(item -> {
                    if (item.getTitle().toLowerCase().contains(lowerQuery)) {
                        return true;
                    }
                    if (item instanceof Book) {
                        return ((Book) item).getAuthor().toLowerCase().contains(lowerQuery);
                    }
                    return false;
                })
                .forEach(item -> {
                    boolean alreadyExists = false;

                    for (Item existingItem : results) {
                        if (existingItem.getClass().equals(item.getClass()) &&
                        existingItem.getTitle().equalsIgnoreCase(item.getTitle())) {
                            alreadyExists = true;
                            break;
                        }
                    }

                    if (!alreadyExists) {
                        results.add(item);
                    }
                });
        return results;
    }

    /**
     * sorts the users list by name using the bubble sorting strategy
     */
    public void sortUsers() {
        //TODO unit test
        if (users == null || users.size() <= 1) {
            return;
        }

        for (int i = 0; i < users.size() - 1; i++) {
            for (int j = 0; j < users.size() - 1 - i; j++) {
                String name1 = users.get(j).getName();
                String name2 = users.get(j + 1).getName();

                if (name1.compareToIgnoreCase(name2) > 0) {
                    User temp = users.get(j);
                    users.set(j, users.get(j + 1));
                    users.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * sorts the items list by title using the selection sorting strategy
     */
    public void sortItems() {
        //TODO unit test
        if (items == null || items.size() <= 1) {
            return;
        }

        for (int i = 0; i < items.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < items.size(); j++) {
                String currentTitle = items.get(j).getTitle();
                String minTitle = items.get(minIdx).getTitle();

                if (currentTitle.compareToIgnoreCase(minTitle) < 0) {
                    minIdx = j;
                }
            }
            Item temp = items.get(minIdx);
            items.set(minIdx, items.get(i));
            items.set(i, temp);

        }
    }
}
