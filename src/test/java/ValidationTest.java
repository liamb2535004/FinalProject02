import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.domain.*;
import org.liamb.util.Constants;
import org.liamb.util.Validation;

public class ValidationTest {
    @Test
    public void testIsValidISBN() {
        //13 digits
        Assertions.assertTrue(Validation.isValidISBN("1234567890123"));

        //12 digits
        Assertions.assertFalse(Validation.isValidISBN("1234567890"));

        //14 digits
        Assertions.assertFalse(Validation.isValidISBN("12345678901234")); // Too long (14 digits)

        //contains non-number
        Assertions.assertFalse(Validation.isValidISBN("123456789012a")); // Contains a letter

        //empty || null
        Assertions.assertFalse(Validation.isValidISBN(""));
        Assertions.assertFalse(Validation.isValidISBN(null));
    }

    @Test
    public void testIsItemAvailable() {
        //IN_STORE
        Book availableBook = new Book("B0001", Item.ItemStatus.IN_STORE, "title", "1234567890123", "author", "genre");
        Assertions.assertTrue(Validation.isItemAvailable(availableBook));

        //LOST || BORROWED
        Book borrowedBook = new Book("B0002", Item.ItemStatus.BORROWED, "title", "1234567890123", "author", "genre");
        Assertions.assertFalse(Validation.isItemAvailable(borrowedBook));
    }

    @Test
    public void testCanStudentBorrow() {
        Student student = new Student("U0001", "name");
        Book book = new Book("B0001", Item.ItemStatus.IN_STORE, "title", "1234567890123", "author", "genre");
        DVD dvd = new DVD("D0001", Item.ItemStatus.IN_STORE, "movie", "director", 100);

        //borrowing a book returns true (if student has 0 books)
        Assertions.assertTrue(Validation.canStudentBorrow(student, book));

        //borrowing a dvd returns false
        Assertions.assertFalse(Validation.canStudentBorrow(student, dvd));

        for (int i = 0; i < Constants.MAX_BOOKS_STUDENT; i++) {
            student.getBorrowedItems().add(new Book("B10" + i, Item.ItemStatus.BORROWED, "T", "1234567890123", "A", "G"));
        }

        // student has 5 books returns false
        Assertions.assertFalse(Validation.canStudentBorrow(student, book));
    }

    @Test
    public void testCanTeacherBorrow() {
        Teacher teacher = new Teacher("U0001", "name");

        //returns true if teacher has 0 books
        Assertions.assertTrue(Validation.canTeacherBorrow(teacher));

        for (int i = 0; i < Constants.MAX_ITEMS_TEACHER; i++) {
            String dvdId = "D" + String.format("%04d", i);
            teacher.getBorrowedItems().add(new DVD(dvdId, Item.ItemStatus.BORROWED, "title", "director", 100));
        }

        //returns false if teacher has 10 items
        Assertions.assertFalse(Validation.canTeacherBorrow(teacher));
    }
}
