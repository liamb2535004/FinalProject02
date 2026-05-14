import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.Exceptions.ItemUnavailableException;
import org.liamb.domain.*;

public class TeacherTest {
    @Test
    @DisplayName("All types can be borrowed")
    public void testBorrowItem1() throws ItemUnavailableException {
        Teacher teacher = new Teacher("U0001", "name");
        Book book = new Book("B0001", Item.ItemStatus.IN_STORE, "title", "1234567890123", "author", "genre");
        DVD dvd = new DVD("D0001", Item.ItemStatus.IN_STORE, "title", "director", 120);
        Magazine magazine = new Magazine("M0001", Item.ItemStatus.IN_STORE, "title", "123", "publisher");

        teacher.borrowItem(book);
        teacher.borrowItem(dvd);
        teacher.borrowItem(magazine);

        Assertions.assertEquals(3, teacher.getBorrowedItems().size());
        Assertions.assertTrue(teacher.getBorrowedItems().contains(book));
        Assertions.assertTrue(teacher.getBorrowedItems().contains(dvd));
        Assertions.assertTrue(teacher.getBorrowedItems().contains(magazine));

        Assertions.assertEquals(Item.ItemStatus.BORROWED, book.getStatus());
        Assertions.assertEquals(Item.ItemStatus.BORROWED, dvd.getStatus());
        Assertions.assertEquals(Item.ItemStatus.BORROWED, magazine.getStatus());
    }

    @Test
    @DisplayName("cannot borrow > 10 items")
    public void testBorrowItem2() throws ItemUnavailableException {
        Teacher teacher = new Teacher("U0001", "name");

        for (int i = 1; i <= 10; i++) {
            String dvdId = "D" + String.format("%04d", i);
            teacher.borrowItem(new DVD(dvdId, Item.ItemStatus.IN_STORE, "title" + i, "director", 100));
        }

        Magazine eleventhItem = new Magazine("M0001", Item.ItemStatus.IN_STORE, "title", "123", "publisher");

        Assertions.assertThrows(ItemUnavailableException.class, () -> {
            teacher.borrowItem(eleventhItem);
        });

        Assertions.assertEquals(10, teacher.getBorrowedItems().size());
        Assertions.assertEquals(Item.ItemStatus.IN_STORE, eleventhItem.getStatus());
    }

    @Test
    @DisplayName("cannot borrow unavailable items")
    public void testBorrowItem3() {
        Teacher teacher = new Teacher("U0002", "Dr. Jones");
        DVD lostDvd = new DVD("D0001", Item.ItemStatus.LOST, "title", "director", 100);

        Assertions.assertThrows(ItemUnavailableException.class, () -> {
            teacher.borrowItem(lostDvd);
        });

        Assertions.assertTrue(teacher.getBorrowedItems().isEmpty());
    }

    @Test
    @DisplayName("default returnItem")
    public void testReturnItem() throws ItemUnavailableException {
        Teacher teacher = new Teacher("U0001", "name");
        Magazine magazine = new Magazine("M0001", Item.ItemStatus.IN_STORE, "title", "123", "publisher");

        teacher.borrowItem(magazine);
        Assertions.assertEquals(1, teacher.getBorrowedItems().size());

        teacher.returnItem(magazine);

        Assertions.assertFalse(teacher.getBorrowedItems().contains(magazine));
        Assertions.assertEquals(Item.ItemStatus.IN_STORE, magazine.getStatus());
    }
}
