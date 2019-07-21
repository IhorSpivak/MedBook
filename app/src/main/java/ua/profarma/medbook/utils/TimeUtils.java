package ua.profarma.medbook.utils;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;

public class TimeUtils {
    public static String getMonthAndYearString(int month, int year) {

        return String.format("%s %s", getMonthName(month), String.valueOf(year));
    }

    public static String getMonthName(int monthNum) {
        switch (monthNum) {
            case 0:
                return Core.get().LocalizationControl().getText(R.id.calendar_january);
            case 1:
                return Core.get().LocalizationControl().getText(R.id.calendar_february);
            case 2:
                return Core.get().LocalizationControl().getText(R.id.calendar_march);
            case 3:
                return Core.get().LocalizationControl().getText(R.id.calendar_april);
            case 4:
                return Core.get().LocalizationControl().getText(R.id.calendar_may);
            case 5:
                return Core.get().LocalizationControl().getText(R.id.calendar_june);
            case 6:
                return Core.get().LocalizationControl().getText(R.id.calendar_july);
            case 7:
                return Core.get().LocalizationControl().getText(R.id.calendar_august);
            case 8:
                return Core.get().LocalizationControl().getText(R.id.calendar_september);
            case 9:
                return Core.get().LocalizationControl().getText(R.id.calendar_october);
            case 10:
                return Core.get().LocalizationControl().getText(R.id.calendar_november);
            case 11:
                return Core.get().LocalizationControl().getText(R.id.calendar_december);
        }
        return Core.get().LocalizationControl().getText(R.id.calendar_january);
    }

    public static String getMonthName_s(int monthNum) {
        switch (monthNum) {
            case 0:
                return Core.get().LocalizationControl().getText(R.id.calendar_january_s);
            case 1:
                return Core.get().LocalizationControl().getText(R.id.calendar_february_s);
            case 2:
                return Core.get().LocalizationControl().getText(R.id.calendar_march_s);
            case 3:
                return Core.get().LocalizationControl().getText(R.id.calendar_april_s);
            case 4:
                return Core.get().LocalizationControl().getText(R.id.calendar_may_s);
            case 5:
                return Core.get().LocalizationControl().getText(R.id.calendar_june_s);
            case 6:
                return Core.get().LocalizationControl().getText(R.id.calendar_july_s);
            case 7:
                return Core.get().LocalizationControl().getText(R.id.calendar_august_s);
            case 8:
                return Core.get().LocalizationControl().getText(R.id.calendar_september_s);
            case 9:
                return Core.get().LocalizationControl().getText(R.id.calendar_october_s);
            case 10:
                return Core.get().LocalizationControl().getText(R.id.calendar_november_s);
            case 11:
                return Core.get().LocalizationControl().getText(R.id.calendar_december_s);
        }
        return Core.get().LocalizationControl().getText(R.id.calendar_january_s);
    }

    //cal.get(Calendar.DAY_OF_WEEK);
    public static String getDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return Core.get().LocalizationControl().getText(R.id.calendar_sunday);
            case 2:
                return Core.get().LocalizationControl().getText(R.id.calendar_monday);

            case 3:
                return Core.get().LocalizationControl().getText(R.id.calendar_tuesday);
            case 4:
                return Core.get().LocalizationControl().getText(R.id.calendar_wednesday);

            case 5:
                return Core.get().LocalizationControl().getText(R.id.calendar_thursday);

            case 6:
                return Core.get().LocalizationControl().getText(R.id.calendar_friday);

            case 7:
                return Core.get().LocalizationControl().getText(R.id.calendar_saturday);
        }
        return Core.get().LocalizationControl().getText(R.id.calendar_sunday);
    }

    public  static String getFormatMinutes(int minutes){
        if(minutes < 10)
            return "0" + minutes;
        else return String.valueOf(minutes);
    }
    public  static String getFormatHours(int hours){
        if(hours < 10)
            return " " + hours;
        else return String.valueOf(hours);
    }
}
