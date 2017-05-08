package com.cardinalhealth.fuse.katas;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.List;

public class RomanCalculator {
    List<String> romanNumbersEntered;
    BidiMap<Character, Integer> romanNumberMap;


    public RomanCalculator() {
        romanNumbersEntered = new ArrayList<>();
        setupRomanNumberHashMap();
    }

    private void setupRomanNumberHashMap() {
        romanNumberMap = new DualHashBidiMap( );
        romanNumberMap.put('I', 1);
        romanNumberMap.put('V', 5);
    }

    public void enter(String romanNumberEntered) {
        romanNumbersEntered.add(romanNumberEntered);
    }

    public String add() {
        int totalDecimalValue = getDecimalValueByAddition();
        return getRomanNumberValue(totalDecimalValue);
    }

    private int getDecimalValueByAddition() {
        int totalDecimalValue = 0;
        for (String romanNumberEntered : romanNumbersEntered) {
            totalDecimalValue += getDecimalValue(romanNumberEntered);
        }
        return totalDecimalValue;
    }

    private int getDecimalValue(String romanNumberEntered) {
        int totalValue = 0;
        for (int x = 0; x < romanNumberEntered.length(); x++) {
            totalValue += romanNumberMap.get(romanNumberEntered.charAt(x));
        }
        return totalValue;
    }

    private String getRomanNumberValue(int totalDecimalValue) {
        StringBuilder result = new StringBuilder();
        buildRomanNumber(result, totalDecimalValue/5, 5);
        buildRomanNumber(result, totalDecimalValue%5, 1);
        return result.toString();
    }

    private void buildRomanNumber(StringBuilder result, int numberOfFives, int decimalNumberBeingProcessed) {
        for (int x = 0; x < numberOfFives; x++) {
            result.append(romanNumberMap.inverseBidiMap().get(decimalNumberBeingProcessed).toString());
        }
    }
}
