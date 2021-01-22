package net.ml.data.utils;

import org.apache.commons.lang3.StringUtils;

public class ISBNUtil {
    
    public static long getISBNAsLong(String str) {
        if (StringUtils.isAllBlank(str)) {
            throw new IllegalArgumentException("isbn cannot be empty");
        }
        String isbn = str.toUpperCase();
        int len = isbn.length();
        char checkSum = isbn.charAt(len - 1);
        char head = isbn.charAt(0);
        if (head < '0' || head > '9' || len != 10) {
            throw new IllegalArgumentException("unsupported isbn");
        }
        return Long.valueOf(isbn.substring(0, len - 1)) * 100 + (checkSum == 'X' ? 10 : Long.valueOf("" + checkSum));
    }
    
    public static String getISBN(Long isbn) {
        long check = isbn % 100;
        return String.format("%09d%s", (isbn / 100), check == 10 ? "X" : check);
    }
}
