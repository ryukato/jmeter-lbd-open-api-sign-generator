package com.linecorp.lbd.signature;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

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
