package id.ic.aims.util.helper;

import java.util.HashMap;
import java.util.Map;

public class MyInfoNationalityMap {

    private static Map<String, String> nationalities = new HashMap<>();

    public MyInfoNationalityMap() {
    }

    public static String mapMyInfoNationality(String nationality) {
        String result = nationality;
        if (!nationality.isEmpty()) {
            if (nationalities.containsKey(nationality)) {
                result = nationalities.get(nationality);
            }
        }
        return result;
    }

    public static void buildNationalityMap(Map<String, String> map) {
        nationalities.putAll(map);
    }
}
