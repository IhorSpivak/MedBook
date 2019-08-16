package mobi.medbook.android.ui.calendar;

import java.util.Calendar;

import mobi.medbook.android.types.visits.UserVisit;


public class VisitUtils {
    public static StatusVisit getStatus(UserVisit userVisit, long today) {
        //today in 00:00, visits in current day can be started all day
        if (userVisit.canceled_at != null)
            return StatusVisit.CANCELED;
        else if (userVisit.ended_at != null)
            return StatusVisit.ENDED;
        else if (userVisit.started_at != null)
            return StatusVisit.STARTED;
        else if (userVisit.time_to < today)
            return StatusVisit.FAILED;
        else if (userVisit.accepted_at != null && userVisit.partner.partner_accepted != null)
            return StatusVisit.ACCEPTED;
        else if (userVisit.accepted_at == null && userVisit.partner.partner_accepted != null)
            return StatusVisit.NEW;
        else if (userVisit.accepted_at != null && userVisit.partner.partner_accepted == null)
            return StatusVisit.PROCESSING;
        else return StatusVisit.EMPTY;
    }

    //LogUtils.logD(TAG, "current time GMT +0, 00:00 = " + today);
    public static long getTodayTime(){
        long currentTime = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTime);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(year, month, day, 0, 0, 0);
        return  cal.getTimeInMillis() / 1000;
    }
}
