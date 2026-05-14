import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.Exceptions.ItemUnavailableException;
import org.liamb.domain.*;

public class StudentTest {
    @Test
    @DisplayName("default borrowItem")
    void testBorrowItem1() throws ItemUnavailableException {
        Student student = new Student("U0001", "Bob");
        Book book = new Book("B0001", Item.ItemStatus.IN_STORE, "title", "1234567890123", "author", "genre");

        student.borrowItem(book);

        Assertions.assertTrue(student.getBorrowedItems().contains(book));
        Assertions.assertEquals(Item.ItemStatus.BORROWED, book.getStatus());
    }
    @Test
    @DisplayName("cannot borrow DVD")
    public void testBorrowItem2() {
        Student student = new Student("U0002", "Bob");
        DVD dvd = new DVD("D0001", Item.ItemStatus.IN_STORE, "title", "author", 100);

        Assertions.assertThrows(RuntimeException.class, () -> {
            student.borrowItem(dvd);
        });

        Assertions.assertEquals(Item.ItemStatus.IN_STORE, dvd.getStatus());
        Assertions.assertTrue(student.getBorrowedItems().isEmpty());
    }

    @Test
    @DisplayName("cannot borrow Magazine")
    public void testBorrowItem3() {
        Student student = new Student("U0002", "Bob");
        Magazine magazine = new Magazine("M0001", Item.ItemStatus.IN_STORE, "title", "123", "publisher");

        Assertions.assertThrows(RuntimeException.class, () -> {
            student.borrowItem(magazine);
        });

        Assertions.assertEquals(Item.ItemStatus.IN_STORE, magazine.getStatus());
        Assertions.assertTrue(student.getBorrowedItems().isEmpty());
    }

    @Test
    @DisplayName("cannot borrow more than 5 books")
    public void testBorrowItem4() throws ItemUnavailableException {
        Student student = new Student("U0003", "name");

        for (int i = 1; i <= 5; i++) {
            String bookId = "B" + String.format("%04d", i);;
            student.borrowItem(new Book(bookId, Item.ItemStatus.IN_STORE, "Title " + i, "1234567890123", "Author", "Genre"));
        }

        Book sixthBook = new Book("B006", Item.ItemStatus.IN_STORE, "illegal book", "1234567890123", "Author", "Genre");

        Assertions.assertThrows(RuntimeException.class, () -> {
            student.borrowItem(sixthBook);
        });
    }

    @Test
    @DisplayName("cannot borrow unavailable item")
    public void testBorrowItem5() {
        Student student = new Student("U0004", "Diana");
        Book borrowedBook = new Book("B007", Item.ItemStatus.BORROWED, "1984", "1234567890123", "Orwell", "Dystopian");

        Assertions.assertThrows(ItemUnavailableException.class, () -> {
            student.borrowItem(borrowedBook);
        });
    }

    @Test
    @DisplayName("default returnItem")
    public void testReturnItem() throws ItemUnavailableException {
        Student student = new Student("U0005", "Eve");
        Book book = new Book("B008", Item.ItemStatus.IN_STORE, "The Hobbit", "1234567890123", "Tolkien", "Fantasy");

        student.borrowItem(book);
        Assertions.assertEquals(1, student.getBorrowedItems().size());

        student.returnItem(book);

        Assertions.assertFalse(student.getBorrowedItems().contains(book));
        Assertions.assertEquals(Item.ItemStatus.IN_STORE, book.getStatus());
    }
}
