package com.cardinalhealth.fuse.katas;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RomanCalculatorTest {
    private RomanCalculator subject;

    @Before
    public void setup() throws Exception {
        subject = new RomanCalculator();
    }

    @Test
    public void iExist() throws Exception {
        assertNotNull(subject);
    }

    @Test
    public void I_plus_I() throws Exception {
        assertRomanCalculatorAddition("I", "I", "II");
    }

    private void assertRomanCalculatorAddition(String firstRomanNumber, String secondRomanNumber, String expectedRomanNumberResult) {
        subject.enter(firstRomanNumber);
        subject.enter(secondRomanNumber);
        assertEquals(expectedRomanNumberResult, subject.add());
    }
}