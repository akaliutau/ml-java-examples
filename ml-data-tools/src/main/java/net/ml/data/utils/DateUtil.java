package net.ml.data.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class DateUtil {

    // List of all date formats that we want to parse.
    // Add your own format here.
    private static List<SimpleDateFormat> dateFormats = new ArrayList<>();

    static {
        dateFormats.add(new SimpleDateFormat("MM/dd/yyyy"));
        dateFormats.add(new SimpleDateFormat("dd.MM.yyyy"));
        dateFormats.add(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a"));
        dateFormats.add(new SimpleDateFormat("dd.MM.yyyy hh:mm:ss a"));
        dateFormats.add(new SimpleDateFormat("dd.MMM.yyyy"));
        dateFormats.add(new SimpleDateFormat("dd-MMM-yyyy"));
    }

    /**
     * Convert String with various formats into java.util.Date
     * @param input Date as a string
     * @return java.util.Date object if input string is parsed successfully else
     *         returns null
     */
    public static Date convertToDate(String input) {
        Date date = null;
        if (null == input) {
            return null;
        }
        for (SimpleDateFormat format : dateFormats) {
            try {
                format.setLenient(false);
                date = new Date(format.parse(input).getTime());
            } catch (ParseException e) {
                // try other formats
            }
            if (date != null) {
                break;
            }
        }

        return date;
    }

}