package com.cardinalhealth.fuse.katas;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        subject.enter("I");
        subject.enter("I");
        assertEquals("II", subject.add());
    }
}