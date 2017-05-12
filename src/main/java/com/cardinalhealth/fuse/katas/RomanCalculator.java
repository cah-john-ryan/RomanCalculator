package com.cardinalhealth.fuse.katas;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RomanCalculator {
    List<String> romanNumbersEntered;
    BidiMap<Character, Integer> romanNumberToBase10Map;
    Integer[] romanNumberDecimalValuesArray = {1, 5, 10, 50, 100, 500, 1000};

    public RomanCalculator() {
        romanNumbersEntered = new ArrayList<>();
        setupRomanNumberBidiMap();
    }

    private void setupRomanNumberBidiMap() {
        romanNumberToBase10Map = new DualHashBidiMap();
        romanNumberToBase10Map.put('I', 1);
        romanNumberToBase10Map.put('V', 5);
        romanNumberToBase10Map.put('X', 10);
        romanNumberToBase10Map.put('L', 50);
        romanNumberToBase10Map.put('C', 100);
        romanNumberToBase10Map.put('D', 500);
        romanNumberToBase10Map.put('M', 1000);
    }

    public void enter(String romanNumberEntered) {
        romanNumbersEntered.add(romanNumberEntered);
    }

    public String add() {
        int totalDecimalValue = getDecimalValueByAddition();
        romanNumbersEntered.clear();
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
        Integer previousValue = null;
        for (int x = romanNumberEntered.length() - 1; x >= 0; x--) {
            int currentValue = romanNumberToBase10Map.get(romanNumberEntered.charAt(x));
            if (previousValue == null || currentValue >= previousValue) {
                totalValue += currentValue;
            } else {
                totalValue -= currentValue;
            }
            previousValue = currentValue;
        }
        return totalValue;
    }

    private String getRomanNumberValue(int totalValue) {
        StringBuilder romanNumberStringBuilder = new StringBuilder();
        int remainingTotal = totalValue;
        remainingTotal = buildRomanNumberAndDecrementRemainingValue(romanNumberStringBuilder, 1000, remainingTotal);
        remainingTotal = buildRomanNumberAndDecrementRemainingValue(romanNumberStringBuilder, 500, remainingTotal);
        remainingTotal = buildRomanNumberAndDecrementRemainingValue(romanNumberStringBuilder, 100, remainingTotal);
        remainingTotal = buildRomanNumberAndDecrementRemainingValue(romanNumberStringBuilder, 50, remainingTotal);
        remainingTotal = buildRomanNumberAndDecrementRemainingValue(romanNumberStringBuilder, 10, remainingTotal);
        remainingTotal = buildRomanNumberAndDecrementRemainingValue(romanNumberStringBuilder, 5, remainingTotal);
        buildRomanNumber(romanNumberStringBuilder, remainingTotal, 'I');
        return romanNumberStringBuilder.toString();
    }

    private int buildRomanNumberAndDecrementRemainingValue(StringBuilder romanNumberStringBuilder, int base10ValueBeingProcessed, int remainingTotal) {
        Character romanCharacterBeingUsed = findRomanNumberFromBase10Value(base10ValueBeingProcessed);
        if (remainingTotal >= base10ValueBeingProcessed) {
            buildRomanNumber(romanNumberStringBuilder, remainingTotal / base10ValueBeingProcessed, romanCharacterBeingUsed);
            remainingTotal = remainingTotal % base10ValueBeingProcessed;
        }
        remainingTotal = buildPotentialShortStopRomanNumbers(romanNumberStringBuilder, base10ValueBeingProcessed, remainingTotal, romanCharacterBeingUsed);
        return remainingTotal;
    }

    private int buildPotentialShortStopRomanNumbers(StringBuilder romanNumberStringBuilder, int base10ValueBeingProcessed, int remainingTotal, Character romanCharacterBeingUsed) {
        for (int childRomanNumberDecimalValue : getChildRomanNumberValuesDescending(base10ValueBeingProcessed)) {
            if (isAShortStop(remainingTotal, base10ValueBeingProcessed, childRomanNumberDecimalValue)) {
                remainingTotal -= buildShortStopRomanNumber(romanNumberStringBuilder, base10ValueBeingProcessed, childRomanNumberDecimalValue);
                break;
            }
        }
        return remainingTotal;
    }

    private int buildShortStopRomanNumber(StringBuilder romanNumberStringBuilder, int base10ValueBeingProcessed, int childRomanNumberDecimalValue) {
        romanNumberStringBuilder.append(getShortStopFormat(childRomanNumberDecimalValue, base10ValueBeingProcessed));
        return base10ValueBeingProcessed - childRomanNumberDecimalValue;
    }

    private void buildRomanNumber(StringBuilder romanNumberStringBuilder, int howManyToAppend, Character romanCharacterBeingUsed) {
        appendRomanCharacterRepeatedly(romanNumberStringBuilder, howManyToAppend, romanCharacterBeingUsed);
    }

    private Integer[] getChildRomanNumberValuesDescending(int base10ValueBeingProcessed) {
        return Arrays.stream(romanNumberDecimalValuesArray)
                .filter(childRomanNumberDecimalValue -> childRomanNumberDecimalValue < base10ValueBeingProcessed)
                .sorted((n1, n2) -> n2 - n1)
                .toArray(Integer[]::new);
    }

    private boolean isAShortStop(int remainingTotal, int base10ValueBeingProcessed, int childRomanNumberDecimalValue) {
        return (remainingTotal >= base10ValueBeingProcessed - childRomanNumberDecimalValue) && (base10ValueBeingProcessed - childRomanNumberDecimalValue != childRomanNumberDecimalValue);
    }

    private String getShortStopFormat(int childRomanNumberDecimalValue, int base10ValueBeingProcessed) {
        return new StringBuilder()
                .append(findRomanNumberFromBase10Value(childRomanNumberDecimalValue))
                .append(findRomanNumberFromBase10Value(base10ValueBeingProcessed)).toString();
    }

    private void appendRomanCharacterRepeatedly(StringBuilder romanNumberStringBuilder, int howManyToAppend, Character romanCharacterBeingUsed) {
        for (int x = 0; x < howManyToAppend; x++) {
            romanNumberStringBuilder.append(romanCharacterBeingUsed);
        }
    }

    private Character findRomanNumberFromBase10Value(int base10ValueBeingProcessed) {
        return romanNumberToBase10Map.inverseBidiMap().get(base10ValueBeingProcessed);
    }
}
