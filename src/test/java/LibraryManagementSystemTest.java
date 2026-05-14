import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.domain.Item;
import org.liamb.domain.User;
import org.liamb.service.LibraryManagementSystem;
import org.liamb.util.Constants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LibraryManagementSystemTest {
    @Test
    @DisplayName("default search title -> book with title")
    void testSearchItemRecursive1() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "9876543210987", "author", "genre");

        List<Item> foundByTitle = system.searchItemRecursive("title");

        Assertions.assertNotNull(foundByTitle);
        Assertions.assertFalse(foundByTitle.isEmpty());
        Assertions.assertEquals("title", foundByTitle.get(0).getTitle());
    }

    @Test
    @DisplayName("search author name -> book with author name")
    void testSearchItemRecursive2() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "1234567890123", "author", "genre");

        List<Item> foundByAuthor = system.searchItemRecursive("author");

        Assertions.assertNotNull(foundByAuthor);
        Assertions.assertFalse(foundByAuthor.isEmpty());
        Assertions.assertEquals("title", foundByAuthor.get(0).getTitle());
    }

    @Test
    @DisplayName("no item found -> empty list")
    void testSearchItemRecursive3() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "9876543210987", "author", "genre");

        List<Item> notFound = system.searchItemRecursive("");

        Assertions.assertTrue(notFound == null || notFound.isEmpty());
    }

    @Test
    @DisplayName("duplicate items -> 1 result")
    void testSearchRecursive4() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "1234567890123", "author", "genre");
        system.addNewBook("title", "1234567890123", "author", "genre");

        List<Item> recursiveResult = system.searchItemRecursive("title");

        Assertions.assertNotNull(recursiveResult);
        Assertions.assertEquals(1, recursiveResult.size());
        Assertions.assertEquals("title", recursiveResult.get(0).getTitle());
    }

    @Test
    @DisplayName("title-case insensitive")
    void testSearchItemRecursive5() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "1234567890123", "author", "genre");

        List<Item> foundByTitle = system.searchItemRecursive("TITLE");

        Assertions.assertNotNull(foundByTitle);
        Assertions.assertFalse(foundByTitle.isEmpty());
        Assertions.assertEquals("title", foundByTitle.get(0).getTitle());
    }

    @Test
    @DisplayName("title-case insensitive")
    void testSearchItemStream1() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "1234567890123", "author", "genre");

        List<Item> foundByTitle = system.searchItemStream("TITLE");

        Assertions.assertNotNull(foundByTitle);
        Assertions.assertFalse(foundByTitle.isEmpty());
        Assertions.assertEquals("title", foundByTitle.get(0).getTitle());
    }

    @Test
    @DisplayName("search by \"author\" -> result contains \"author\"")
    void testSearchItemStream2() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "1234567890123", "author", "genre");

        List<Item> foundByAuthor = system.searchItemStream("author");

        Assertions.assertNotNull(foundByAuthor);
        Assertions.assertFalse(foundByAuthor.isEmpty());
        Assertions.assertEquals("title", foundByAuthor.get(0).getTitle());
    }

    @Test
    @DisplayName("search duplicate copies -> return one result")
    void testSearchStream3() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "1234567890123", "author", "genre");
        system.addNewBook("title", "1234567890123", "author", "genre");

        List<Item> recursiveResult = system.searchItemStream("title");

        Assertions.assertNotNull(recursiveResult);
        Assertions.assertEquals(1, recursiveResult.size());
        Assertions.assertEquals("title", recursiveResult.get(0).getTitle());
    }

    @Test
    @DisplayName("default search title -> book with title")
    void testSearchItemStream4() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "9876543210987", "author", "genre");

        List<Item> foundByTitle = system.searchItemStream("title");

        Assertions.assertNotNull(foundByTitle);
        Assertions.assertFalse(foundByTitle.isEmpty());
        Assertions.assertEquals("title", foundByTitle.get(0).getTitle());
    }

    @Test
    @DisplayName("no item found -> empty list")
    void testSearchItemStream5() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("title", "9876543210987", "author", "genre");

        List<Item> notFound = system.searchItemStream("");

        Assertions.assertTrue(notFound == null || notFound.isEmpty());
    }

    @Test
    @DisplayName("Titles: Z, A, M -> A, M, Z")
    void testSortItems() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewBook("Z", "1234567890123", "author Z", "genre");
        system.addNewBook("A", "1234567890123", "author A", "genre");
        system.addNewBook("M", "1234567890123", "author M", "genre");

        system.sortItems();
        List<Item> sortedItems = system.getItems();

        Assertions.assertEquals("A", sortedItems.get(0).getTitle());
        Assertions.assertEquals("M", sortedItems.get(1).getTitle());
        Assertions.assertEquals("Z", sortedItems.get(2).getTitle());
    }

    @Test
    @DisplayName("Names: z, a, m -> a, m, z")
    void testSortUsers() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.addNewStudent("z");
        system.addNewStudent("a");
        system.addNewTeacher("m");

        system.sortUsers();
        List<User> sortedUsers = system.getUsers();

        Assertions.assertEquals("a", sortedUsers.get(0).getName());
        Assertions.assertEquals("m", sortedUsers.get(1).getName());
        Assertions.assertEquals("z", sortedUsers.get(2).getName());
    }

    @Test
    public void testLoadCsvFiles() throws IOException {
        File itemsFile = new File(Constants.ITEMS_CSV_PATH);
        File usersFile = new File(Constants.USERS_CSV_PATH);

        File itemsBackup = new File(Constants.ITEMS_CSV_PATH + ".bak");
        File usersBackup = new File(Constants.USERS_CSV_PATH + ".bak");

        if (itemsFile.exists()) {
            itemsFile.renameTo(itemsBackup);
        }
        if (usersFile.exists()) {
            usersFile.renameTo(usersBackup);
        }

        try (FileWriter itemWriter = new FileWriter(itemsFile)) {
            itemWriter.write("Book,B001,IN_STORE,Test Book,1234567890123,Author,Genre\n");
        }
        try (FileWriter userWriter = new FileWriter(usersFile)) {
            userWriter.write("Student,U001,Test Student\n");
        }

        LibraryManagementSystem system = new LibraryManagementSystem();
        system.loadCsvFiles();

        List<Item> loadedItems = system.getItems();
        List<User> loadedUsers = system.getUsers();

        Assertions.assertFalse(loadedItems.isEmpty());
        Assertions.assertFalse(loadedUsers.isEmpty());

        Assertions.assertEquals("Test Book", loadedItems.get(0).getTitle());
        Assertions.assertEquals("Test Student", loadedUsers.get(0).getName());

        if (itemsFile.exists()) {
            itemsFile.delete();
        }
        if (usersFile.exists()) {
            usersFile.delete();
        }

        if (itemsBackup.exists()) {
            itemsBackup.renameTo(itemsFile);
        }
        if (usersBackup.exists()) {
            usersBackup.renameTo(usersFile);
        }
    }
}
