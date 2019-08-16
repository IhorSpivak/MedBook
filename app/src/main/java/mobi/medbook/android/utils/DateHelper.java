package mobi.medbook.android.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mobi.medbook.android.App;

public class DateHelper {

    public static final SimpleDateFormat sdfFacebook = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    public static final SimpleDateFormat sdfVk = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    public static final SimpleDateFormat sdfServer = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    public static final SimpleDateFormat sdfOrder = new SimpleDateFormat("HH:mm",Locale.getDefault());
    public static final SimpleDateFormat sdfOrder1 = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
    public static final SimpleDateFormat sdfEditProfile = new SimpleDateFormat("dd MMM yyyy",new Locale(App.getLanguage().equals("ru") ? "rus" : "uk", "UA"));


    public static final SimpleDateFormat sdfServerTime = new SimpleDateFormat("HH:mm");

    public static String parseFacebookDate(String dateString){
        if(dateString!=null){
            try {
                return sdfServer.format(sdfFacebook.parse(dateString));
            } catch (Exception ignored) {
                return "";
            }
        }
        return "";
    }

    public static String parseDateForDateFlight(String dateString){
        if(dateString!=null){
            try {
                return sdfOrder1.format(sdfServer.parse(dateString));
            } catch (Exception ignored) {
                return "";
            }
        }
        return "";
    }

    public static String parseDateForServerDateBirth(String dateString){
        if(dateString!=null){
            try {
                return sdfServer.format(sdfOrder1.parse(dateString));
            } catch (Exception ignored) {
                return "";
            }
        }
        return "";
    }


    public static String parseVkDate(String dateString){
        if(dateString!=null){
            try {
                return sdfServer.format(sdfVk.parse(dateString));
            } catch (Exception ignored) {
                return "";
            }
        }
        return "";
    }

    public static String getDateForServerFromCalendar(Calendar calendar){
        return sdfServer.format(calendar.getTime());
    }

    public static String getTimeForServerFromCalendar(Calendar calendar){
        return sdfServerTime.format(calendar.getTime());
    }

    public static String getStringFromCalendarForOrder(Calendar calendar){
        return sdfOrder.format(calendar.getTime());

    }

    public static String getStringFromCalendarForEditProfile(Calendar calendar){
        return sdfEditProfile.format(calendar.getTime());

    }
}