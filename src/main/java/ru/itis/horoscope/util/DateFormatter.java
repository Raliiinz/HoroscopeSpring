package ru.itis.horoscope.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    private static final SimpleDateFormat DISPLAY_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat INPUT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatForDisplay(Date date) {
        return date != null ? DISPLAY_FORMAT.format(date) : "";
    }

    public static String formatForInput(Date date) {
        return date != null ? INPUT_FORMAT.format(date) : "";
    }
}
