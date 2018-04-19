package com.github.beetrox.wed18aprunittest;

public class TwoStrings {

    private String stringOne;
    private String stringTwo;

    public TwoStrings(String stringOne, String stringTwo) {
        this.stringOne = stringOne;
        this.stringTwo = stringTwo;
    }

    public String add() {
        return stringOne + stringTwo;
    }

    public String subtract() {
        if (stringTwo.length() >= stringOne.length())
            return stringTwo.substring(stringOne.length());

        return stringOne.substring(stringTwo.length());

    }
}
