package com.validation;

/**
 *
 * @author Barry Gray Kapelembe
 */
public class TheEqualizer {

    public String toUperAndLower(String input) {
        String toLower = input.toLowerCase();
        String equi = toLower.substring(0, 1).toUpperCase() + toLower.substring(1);
        return equi;
    }

}
