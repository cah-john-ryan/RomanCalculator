package com.cardinalhealth.fuse.katas;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RomanCalculator {
    private List<String> romanNumbersEntered;
    private BidiMap<Character, Integer> romanNumberAndBase10Map;
    private Integer[] romanNumberDecimalValuesProgression = {1000, 500, 100, 50, 10, 5, 1};
    private Character[] romanNumberDecimalValueProgression = {'M', 'D', 'C', 'L', 'X', 'V', 'I'};

    RomanCalculator() {
        romanNumbersEntered = new ArrayList<>();
        setupRomanNumberBidiMap();
    }

    void enter(String romanNumberEntered) {
        romanNumbersEntered.add(romanNumberEntered);
    }

    String add() {
        int totalDecimalValue = getDecimalValueByAddition();
        romanNumbersEntered.clear();
        return getRomanNumberValue(totalDecimalValue);
    }

    private void setupRomanNumberBidiMap() {
        romanNumberAndBase10Map = new DualHashBidiMap<>();
        for (int x = 0; x < romanNumberDecimalValueProgression.length; x++) {
            romanNumberAndBase10Map.put(romanNumberDecimalValueProgression[x], romanNumberDecimalValuesProgression[x]);
        }
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
            int currentValue = romanNumberAndBase10Map.get(romanNumberEntered.charAt(x));
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
        for (Integer romanNumberDecimalValue : romanNumberDecimalValuesProgression) {
            remainingTotal = buildRepeatedRomanCharacters(romanNumberStringBuilder, romanNumberDecimalValue, remainingTotal);
        }
        return romanNumberStringBuilder.toString();
    }

    private int buildRepeatedRomanCharacters(StringBuilder romanNumberStringBuilder, int romanNumberDecimalValue, int remainingTotal) {
        Character romanCharacterBeingUsed = findRomanNumberFromBase10Value(romanNumberDecimalValue);
        if (remainingTotal >= romanNumberDecimalValue) {
            remainingTotal = buildRepeatedRomanCharacters(romanNumberStringBuilder, remainingTotal, romanNumberDecimalValue, romanCharacterBeingUsed);
        }
        remainingTotal = buildPotentialLesserRomanCharacters(romanNumberStringBuilder, romanNumberDecimalValue, remainingTotal);
        return remainingTotal;
    }

    private int buildRepeatedRomanCharacters(StringBuilder romanNumberStringBuilder, int remainingTotal, int romanNumberDecimalValue, Character romanCharacterBeingUsed) {
        int howManyToAppend = remainingTotal / romanNumberDecimalValue;
        for (int x = 0; x < howManyToAppend; x++) {
            romanNumberStringBuilder.append(romanCharacterBeingUsed);
        }
        return remainingTotal % romanNumberDecimalValue;
    }

    private int buildPotentialLesserRomanCharacters(StringBuilder romanNumberStringBuilder, int romanNumberDecimalValue, int remainingTotal) {
        for (int lesserRomanNumberDecimalValue : getChildRomanNumberValues(romanNumberDecimalValue)) {
            if (shouldLesserRomanCharacterBeUsed(remainingTotal, romanNumberDecimalValue, lesserRomanNumberDecimalValue)) {
                remainingTotal -= buildLesserRomanCharacter(romanNumberStringBuilder, romanNumberDecimalValue, lesserRomanNumberDecimalValue);
                break;
            }
        }
        return remainingTotal;
    }

    private int buildLesserRomanCharacter(StringBuilder romanNumberStringBuilder, int romanNumberDecimalValue, int childRomanNumberDecimalValue) {
        romanNumberStringBuilder.append(getLesserBeforeBiggerFormat(childRomanNumberDecimalValue, romanNumberDecimalValue));
        return romanNumberDecimalValue - childRomanNumberDecimalValue;
    }

    private Integer[] getChildRomanNumberValues(int romanNumberDecimalValue) {
        return Arrays.stream(romanNumberDecimalValuesProgression)
                .filter(childRomanNumberDecimalValue -> childRomanNumberDecimalValue < romanNumberDecimalValue)
                .toArray(Integer[]::new);
    }

    private boolean shouldLesserRomanCharacterBeUsed(int remainingTotal, int romanNumberDecimalValue, int childRomanNumberDecimalValue) {
        return (remainingTotal >= romanNumberDecimalValue - childRomanNumberDecimalValue) && (romanNumberDecimalValue - childRomanNumberDecimalValue != childRomanNumberDecimalValue);
    }

    private String getLesserBeforeBiggerFormat(int childRomanNumberDecimalValue, int romanNumberDecimalValue) {
        return String.valueOf(findRomanNumberFromBase10Value(childRomanNumberDecimalValue)) +
                findRomanNumberFromBase10Value(romanNumberDecimalValue);
    }

    private Character findRomanNumberFromBase10Value(int base10Value) {
        return romanNumberAndBase10Map.inverseBidiMap().get(base10Value);
    }
}
