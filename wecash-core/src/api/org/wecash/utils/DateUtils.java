package org.wecash.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    /**
     * @param year
     * @param month from 1 to 12
     * @param day from 1 to 31
     */
    public static Date newDate(int year, int month, int day) {
        return new Date(year - YEAR_OFFSET, month-1, day);
    }

    protected static int YEAR_OFFSET = 1900;
    
    public static String dat2str(Date date){
        return dat2str(date, "dd/MM/yyyy HH:mm:ss");
    }
    
    /**
     * Some example format
     * dd.MM.yy                         09.04.98
     * yyyy.MM.dd G 'at' hh:mm:ss z     1998.04.09 AD at 06:15:55 PDT
     * EEE, MMM d, ''yy                 Thu, Apr 9, '98
     * h:mm a                           6:15 PM
     * H:mm                             18:15
     * H:mm:ss:SSS                      18:15:55:624
     * K:mm a,z                         6:15 PM,PDT
     * yyyy.MMMMM.dd GGG hh:mm aaa      1998.April.09 AD 06:15 PM 
     * 
     * @see http://java.sun.com/docs/books/tutorial/i18n/format/simpleDateFormat.html
     * @param date
     * @param format
     * @return
     */
    public static String dat2str(Date date, String format){
        if(date==null)
            return "";
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }
    
    public static int parseMonth3LettersFrench(String sd) {
        if (sd.toLowerCase().endsWith("jan"))
            return 0;
        else if (sd.toLowerCase().endsWith("fév"))
            return 1;
        else if (sd.toLowerCase().endsWith("mar"))
            return 2;
        else if (sd.toLowerCase().endsWith("avr"))
            return 3;
        else if (sd.toLowerCase().endsWith("mai"))
            return 4;
        else if (sd.toLowerCase().endsWith("jun"))
            return 5;
        else if (sd.toLowerCase().endsWith("juin"))
            return 5;
        else if (sd.toLowerCase().endsWith("jul"))
            return 6;
        else if (sd.toLowerCase().endsWith("juil"))
            return 6;
        else if (sd.toLowerCase().endsWith("aoû"))
            return 7;
        else if (sd.toLowerCase().endsWith("sep"))
            return 8;
        else if (sd.toLowerCase().endsWith("oct"))
            return 9;
        else if (sd.toLowerCase().endsWith("nov"))
            return 10;
        else if (sd.toLowerCase().endsWith("déc"))
            return 11;
        else
            return -1;
    }
}
