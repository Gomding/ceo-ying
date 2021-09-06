package com.hululuuuu.ceoying;

public class NumberUtil {

    public static String parseStringToSignedNumber(int number) {
        if (number < 0) {
            return "-" + number;
        }
        return "+" + number;
    }
}
