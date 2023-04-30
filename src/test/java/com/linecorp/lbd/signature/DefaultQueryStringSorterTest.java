package com.linecorp.lbd.signature;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DefaultQueryStringSorterTest {

    @Test
    public void test1() {
        String queryString = "?b=b&a=a";
        String sorted = DefaultQueryStringSorter.createInstance().sort(queryString);
        String expected = "?a=a&b=b";
        assertEquals(expected, sorted);
    }

    @Test
    public void test2() {
        String queryString = "b=b&a=a";
        String sorted = DefaultQueryStringSorter.createInstance().sort(queryString);
        String expected = "a=a&b=b";
        assertEquals(expected, sorted);
    }
}
