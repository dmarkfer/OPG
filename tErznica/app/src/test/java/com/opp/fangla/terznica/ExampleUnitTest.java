package com.opp.fangla.terznica;

import org.junit.Test;
import org.junit.internal.JUnitSystem;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        String[] tmp = "Opatovina ul.3c,10020,Zagreb,Hrvatska".split(",|.");

        for(String str : tmp) {
            System.out.println(str);
        }
    }
}