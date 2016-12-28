package service;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeService {
    public static String formattedDate(Date date) {
        if (date == null) {
            return ("");
        } else {
            Locale local = new Locale("ru","RU");
            String[] russianMonth =
                    {
                            "января",
                            "февраля",
                            "марта",
                            "апреля",
                            "мая",
                            "июня",
                            "июля",
                            "августа",
                            "сентября",
                            "октября",
                            "ноября",
                            "декабря"
                    };
            DateFormatSymbols russSymbol = new DateFormatSymbols(local);
            russSymbol.setMonths(russianMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy года", russSymbol);
            return sdf.format(date);
        }
    }
}
