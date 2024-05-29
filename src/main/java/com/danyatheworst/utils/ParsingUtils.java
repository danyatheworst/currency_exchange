package main.java.com.danyatheworst.utils;

import java.util.HashMap;
import java.util.Map;

public class ParsingUtils {
    public static Map<String, String> parse(String formFields) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formFields.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";
            map.put(key, value);
        }
        return map;
    }
}
