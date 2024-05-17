package main.java.com.danyatheworst;

public class Validation {

    public static boolean isCodeValid(String code) {
        if (code == null || code.length() != 3) {
            return false;
        }
        for (char ch : code.toCharArray()) {
            if (!Character.isLetter(ch) || Character.UnicodeBlock.of(ch) != Character.UnicodeBlock.BASIC_LATIN) {
               return false;
            }
        }
        return true;
    }

}
