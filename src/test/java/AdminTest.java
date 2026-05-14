import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.liamb.domain.Admin;
import org.liamb.service.LibraryManagementSystem;
import org.liamb.util.Constants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminTest {
    private final String ITEMS_BACKUP_PATH = Constants.ITEMS_CSV_PATH;
    private final String USERS_BACKUP_PATH = Constants.USERS_CSV_PATH;

    @Test
    void testGenerateReportDoesNotThrowException() {
        LibraryManagementSystem system = new LibraryManagementSystem();
        Admin admin = new Admin("U0000", "Admin User");

        system.addNewStudent("Test Student");
        system.addNewBook("Test Book", "1234567890123", "Author", "Genre");

        Assertions.assertDoesNotThrow(() -> {
            admin.generateReport(system);
        });
    }

    @Test
    void testBackupDataCreatesFiles() throws IOException {
        LibraryManagementSystem system = new LibraryManagementSystem();
        Admin admin = new Admin("U0001", "Admin User");

        system.addNewStudent("Test Student");
        system.addNewBook("Test Book", "1234567890123", "Author", "Genre");

        File itemsBackup = new File(ITEMS_BACKUP_PATH);
        File usersBackup = new File(USERS_BACKUP_PATH);

        admin.backupData(system);

        Assertions.assertTrue(itemsBackup.exists());
        Assertions.assertTrue(usersBackup.exists());

        List<String> itemLines = new ArrayList<>();
        try (Scanner scanner = new Scanner(itemsBackup)) {
            while (scanner.hasNextLine()) {
                itemLines.add(scanner.nextLine());
            }
        }

        List<String> userLines = new ArrayList<>();
        try (Scanner userScanner = new Scanner(usersBackup)) {
            while (userScanner.hasNextLine()) {
                userLines.add(userScanner.nextLine());
            }
        }

        Assertions.assertFalse(itemLines.isEmpty());
        Assertions.assertFalse(userLines.isEmpty());

        StringBuilder itemsBuilder = new StringBuilder();
        for (String line : itemLines) {
            itemsBuilder.append(line).append("\n");
        }
        String allItemsText = itemsBuilder.toString();

        StringBuilder usersBuilder = new StringBuilder();
        for (String line : userLines) {
            usersBuilder.append(line).append("\n");
        }
        String allUsersStr = usersBuilder.toString();

        Assertions.assertTrue(allItemsText.contains("Test Book"));
        Assertions.assertTrue(allItemsText.contains("1234567890123"));
        Assertions.assertTrue(allUsersStr.contains("Test Student"));

        if (itemsBackup.exists()) {
            itemsBackup.delete();
        }
        if (usersBackup.exists()) {
            usersBackup.delete();
        }
    }
}
