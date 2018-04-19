package com.github.beetrox.wed18aprunittest;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TwoStringsTest {

    @Test
    public void canAdd() {

        //given
        TwoStrings twoStrings = new TwoStrings("one", "two");

        //when
        String result = twoStrings.add();

        //then
        assertEquals("Error adding", "onetwo", result);
    }

    @Test
    public void canSubtract() {

        //given
        TwoStrings twoStrings = new TwoStrings("Missan", "Cleo");
        TwoStrings oneIsShorter = new TwoStrings("Cleo", "Missan");
        TwoStrings sameLength = new TwoStrings("Miss", "Cleo");

        //when
        String result1 = twoStrings.subtract();
        String result2 = oneIsShorter.subtract();
        String result3 = sameLength.subtract();

        //then
        assertEquals("Error subtracting", "an", result1);
        assertEquals("Error subtracting", "an", result2);
        assertEquals("Error subtracting", "", result3);
    }
}
