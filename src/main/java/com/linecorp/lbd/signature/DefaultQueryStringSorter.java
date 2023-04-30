package com.linecorp.lbd.signature;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultQueryStringSorter {

    public String sort(String queryString) {
        if (queryString == null) {
            return "";
        }
        String modified = queryString;
        boolean hasStartingChar = modified.startsWith("?");
        if (hasStartingChar) {
            modified = modified.substring(1, modified.length());
        }

        Stream<String> stream = Arrays.stream(modified.split("&"));
        if (stream.anyMatch(s -> !s.contains("="))) {
            return "";
        } else {
            String collected = Arrays.stream(modified.split("&")).map(s -> s.split("="))
                                     .sorted(Comparator.comparing(o -> o[0]))
                                     .map(strings -> strings[0] + "=" + strings[1])
                                     .collect(Collectors.joining("&"));
            if (hasStartingChar) {
                return "?" + collected;
            } else {
                return collected;
            }
        }
    }

    public static DefaultQueryStringSorter createInstance() {
        return new DefaultQueryStringSorter();
    }
}
