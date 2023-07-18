package id.ic.aims.util.helper;

import java.util.HashMap;
import java.util.Map;

public class MyInfoCountiesMap {

    private static Map<String, String> countries = new HashMap<>();

    public MyInfoCountiesMap() {
    }

    public static String mapMyInfoCountry(String country) {
        String result = country;
        if (!country.isEmpty()) {
            if (countries.containsKey(country)) {
                result = countries.get(country);
            } else if (country.contains("UNKNOWN")) {
                result = "Other";
            }
        }
        return result;
    }

    public static void buildCountiesMap(Map<String, String> map) {
        countries.putAll(map);
    }
}
