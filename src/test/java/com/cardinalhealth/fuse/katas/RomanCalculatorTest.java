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

    @Test
    public void I_plus_II() throws Exception {
        assertRomanCalculatorAddition("I", "II", "III");
    }

    @Test
    public void II_plus_III() throws Exception {
        assertRomanCalculatorAddition("II", "III", "V");
    }

    @Test
    public void II_plus_V() throws Exception {
        assertRomanCalculatorAddition("II", "V", "VII");
    }

    @Test
    public void III_plus_III() throws Exception {
        assertRomanCalculatorAddition("III", "III", "VI");
    }

    @Test
    public void V_plus_V() throws Exception {
        assertRomanCalculatorAddition("V", "V", "X");
    }

    @Test
    public void X_plus_X() throws Exception {
        assertRomanCalculatorAddition("X", "X", "XX");
    }

    @Test
    public void XV_plus_X() throws Exception {
        assertRomanCalculatorAddition("XV", "X", "XXV");
    }

    @Test
    public void XX_plus_XXX() throws Exception {
        assertRomanCalculatorAddition("XX", "XXX", "L");
    }

    @Test
    public void II_plus_II() throws Exception {
        assertRomanCalculatorAddition("II", "II", "IV");
    }

    @Test
    public void VII_plus_II() throws Exception {
        assertRomanCalculatorAddition("VII", "II", "IX");
    }

    @Test
    public void XXXII_plus_XVII() throws Exception {
        assertRomanCalculatorAddition("XXXII", "XVII", "IL");
    }

    private void assertRomanCalculatorAddition(String firstRomanNumber, String secondRomanNumber, String expectedRomanNumberResult) {
        subject.enter(firstRomanNumber);
        subject.enter(secondRomanNumber);
        assertEquals(expectedRomanNumberResult, subject.add());
    }
}