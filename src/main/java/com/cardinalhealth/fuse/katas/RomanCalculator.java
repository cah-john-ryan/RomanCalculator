package com.cardinalhealth.fuse.katas;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.List;

public class RomanCalculator {
    List<String> romanNumbersEntered;
    BidiMap<Character, Integer> romanNumberToBase10Map;

    public RomanCalculator() {
        romanNumbersEntered = new ArrayList<>();
        setupRomanNumberHashMap();
    }

    private void setupRomanNumberHashMap() {
        romanNumberToBase10Map = new DualHashBidiMap();
        romanNumberToBase10Map.put('I', 1);
        romanNumberToBase10Map.put('V', 5);
        romanNumberToBase10Map.put('X', 10);
        romanNumberToBase10Map.put('L', 50);
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
            totalValue += romanNumberToBase10Map.get(romanNumberEntered.charAt(x));
        }
        return totalValue;
    }

    private String getRomanNumberValue(int totalValue) {
        StringBuilder result = new StringBuilder();
        int remainingValue = totalValue;
        remainingValue = buildRomanNumberAndDecrementRemainingValue(result, 50, remainingValue);
        remainingValue = buildRomanNumberAndDecrementRemainingValue(result, 10, remainingValue);
        remainingValue = buildRomanNumberAndDecrementRemainingValue(result, 5, remainingValue);
        buildRomanNumber(result, remainingValue, 'I');
        return result.toString();
    }

    private int buildRomanNumberAndDecrementRemainingValue(StringBuilder result, int base10ValueBeingProcessed, int remainingTotal) {
        Character romanCharacterBeingUsed = findRomanNumberFromBase10Value(base10ValueBeingProcessed);
        buildRomanNumber(result, remainingTotal / base10ValueBeingProcessed, romanCharacterBeingUsed);
        return remainingTotal % base10ValueBeingProcessed;
    }

    private void buildTheRunt(StringBuilder result, Character romanCharacterBeingUsed) {
        result.append('I');
        result.append(romanCharacterBeingUsed);
    }

    private boolean isShortByOne(int base10ValueBeingProcessed, int remainingTotal) {
        return remainingTotal == base10ValueBeingProcessed - 1;
    }

    private void buildRomanNumber(StringBuilder result, int howManyToAppend, Character romanCharacterBeingUsed) {

        appendRomanCharacterRepeatedly(result, howManyToAppend, romanCharacterBeingUsed);
    }

    private Character findRomanNumberFromBase10Value(int base10ValueBeingProcessed) {
        return romanNumberToBase10Map.inverseBidiMap().get(base10ValueBeingProcessed);
    }

    private void appendRomanCharacterRepeatedly(StringBuilder result, int howManyToAppend, Character romanCharacterBeingUsed) {
        for (int x = 0; x < howManyToAppend; x++) {
            result.append(romanCharacterBeingUsed);
        }
    }
}
