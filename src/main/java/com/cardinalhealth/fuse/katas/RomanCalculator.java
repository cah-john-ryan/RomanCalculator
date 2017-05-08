package com.cardinalhealth.fuse.katas;

import java.util.ArrayList;
import java.util.List;

public class RomanCalculator {
    List<String> romanNumbersEntered;

    public RomanCalculator() {
        romanNumbersEntered = new ArrayList<>();
    }

    public void enter(String romanNumberEntered) {
        romanNumbersEntered.add(romanNumberEntered);
    }

    public String add() {
        StringBuilder resultingRomanNumber = new StringBuilder();
        romanNumbersEntered.forEach(romanNumberEntered -> {
            resultingRomanNumber.append(romanNumberEntered);
        });
        return resultingRomanNumber.toString();
    }
}
