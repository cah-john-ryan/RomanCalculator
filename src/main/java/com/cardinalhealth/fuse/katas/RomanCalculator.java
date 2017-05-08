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
        for(int x = 0; x < totalDecimalValue/5; x++) {
            result.append(romanNumberMap.inverseBidiMap().get(5).toString());
        }
        for(int x = 0; x < totalDecimalValue%5; x++) {
            result.append(romanNumberMap.inverseBidiMap().get(1).toString());
        }
        return result.toString();
    }
}
