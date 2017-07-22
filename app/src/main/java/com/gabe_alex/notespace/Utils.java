package com.gabe_alex.notespace;

import android.content.Context;
import android.text.format.DateUtils;

import java.util.Date;

public class Utils {
    /**
     * Overload for DateUtils.getRelativeTimespanString(), which also takes a transitionResolution param, for switching from relative to absolute DateTime
     */
    public static CharSequence getRelativeTimespanString(Context context, long time, long minResolution, long transitionResolution) {
        long currentTime = new Date().getTime();
        long timeDiff = currentTime - time;

        if (timeDiff < transitionResolution) {
            return DateUtils.getRelativeTimeSpanString(time, currentTime, minResolution);
        } else {
            return DateUtils.formatDateTime(context, time, 0);
        }
    }
}
