package business;

public class Validation {

    public static void nonEmpty(String str) throws ValidationException {
        if(str.trim().isEmpty()) {
            throw new ValidationException("All fields must be non-empty!");
        }
    }

    public static void isIsbn(String isbn) throws ValidationException {
        if(!isbn.matches("^\\d{2}-\\d{5}$")) {
            throw new ValidationException("ISBN must match this format 21-11451");
        }
    }

    public static void isZip(String str) throws ValidationException {
        if(!str.matches("^\\d{5}$")) {
            throw new ValidationException("Zip must be numeric and consist of 5 characters!");
        }
    }

    public static void isTelephone(String tel) throws ValidationException {
        if (!tel.matches("\\d{3}-\\d{3}-\\d{4}")) {
            throw new ValidationException("Tel Number must match this format 123-123-1234");
        }
    }

    public static void isID(String id) throws ValidationException {
        if (!id.matches("^\\d+$")) {
            throw new ValidationException("ID must be numeric!");
        }
    }

}
