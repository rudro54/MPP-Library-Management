package librarysystem;

import dataaccess.DataAccessFacade;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
enum StorageType {
    BOOKS, USERS, ORDERS; // Add other types if needed
}
public class StorageReader {
    public static final String OUTPUT_DIR = System.getProperty("user.dir")
            + "/src/dataaccess/storage";
    //private static final String OUTPUT_DIR = "path_to_your_storage_folder"; // Change this to the correct folder path

    public static Object readFromStorage(DataAccessFacade.StorageType type) {
        ObjectInputStream in = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            in = new ObjectInputStream(Files.newInputStream(path));
            return in.readObject(); // Deserialize and return the object
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Example: Reading the BOOKS storage file
        Object books = readFromStorage(DataAccessFacade.StorageType.BOOKS);

        if (books != null) {
            System.out.println("Deserialized Object: " + books);
        } else {
            System.out.println("Failed to read the books file.");
        }
    }
}