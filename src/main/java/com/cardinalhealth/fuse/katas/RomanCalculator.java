package com.cardinalhealth.fuse.katas;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RomanCalculator {
    List<String> romanNumbersEntered;
    BidiMap<Character, Integer> romanNumberToBase10Map;
    Map<Character, Character> parentRomanNumberMap;

    public RomanCalculator() {
        romanNumbersEntered = new ArrayList<>();
        setupRomanNumberHashMap();
        setupParentRomanNumberMap();
    }

    private void setupRomanNumberHashMap() {
        romanNumberToBase10Map = new DualHashBidiMap();
        romanNumberToBase10Map.put('I', 1);
        romanNumberToBase10Map.put('V', 5);
        romanNumberToBase10Map.put('X', 10);
        romanNumberToBase10Map.put('L', 50);
    }

    private void setupParentRomanNumberMap() {
        parentRomanNumberMap = new HashMap<>();
        parentRomanNumberMap.put('I', 'V');
        parentRomanNumberMap.put('V', 'X');
        parentRomanNumberMap.put('X', 'L');
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
        buildRomanNumber(result, remainingValue, 1);
        return result.toString();
    }

    private int buildRomanNumberAndDecrementRemainingValue(StringBuilder result, int base10ValueBeingProcessed, int remainingTotal) {
        buildRomanNumber(result, remainingTotal / base10ValueBeingProcessed, base10ValueBeingProcessed);
        return remainingTotal % base10ValueBeingProcessed;
    }

    private void buildRomanNumber(StringBuilder result, int howManyToAppend, int base10ValueBeingProcessed) {
        Character romanCharacterBeingUsed = findRomanNumberFromBase10Value(base10ValueBeingProcessed);
        if (howManyToAppend == 4) {
            moveItOnUp(result, romanCharacterBeingUsed);
        } else {
            appendRomanCharacterRepeatedly(result, howManyToAppend, romanCharacterBeingUsed);
        }
    }

    private Character findRomanNumberFromBase10Value(int base10ValueBeingProcessed) {
        return romanNumberToBase10Map.inverseBidiMap().get(base10ValueBeingProcessed);
    }

    private void moveItOnUp(StringBuilder result, Character romanCharacterBeingUsed) {
        result.append(romanCharacterBeingUsed);
        Character parentRomanNumber = findParentRomanNumber(romanCharacterBeingUsed);
        result.append(parentRomanNumber.toString());
    }

    private Character findParentRomanNumber(Character romanCharacterBeingUsed) {
        return parentRomanNumberMap.get(romanCharacterBeingUsed);
    }

    private void appendRomanCharacterRepeatedly(StringBuilder result, int howManyToAppend, Character romanCharacterBeingUsed) {
        for (int x = 0; x < howManyToAppend; x++) {
            result.append(romanCharacterBeingUsed);
        }
    }
}
