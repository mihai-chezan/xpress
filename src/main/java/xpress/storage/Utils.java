package xpress.storage;

import java.util.concurrent.TimeUnit;

import org.joda.time.Interval;

import xpress.TimeEnum;

public class Utils {
    private final static long ONE_HOUR = TimeUnit.HOURS.toMillis(1);
    private final static long ONE_DAY = TimeUnit.DAYS.toMillis(1);
    private final static long ONE_WEEK = TimeUnit.DAYS.toMillis(7);
    private final static long ONE_MONTH = TimeUnit.DAYS.toMillis(30);
    private final static long ONE_YEAR = TimeUnit.DAYS.toMillis(365);

    public static Interval getIntervalFormTimeEnum(TimeEnum timeRange) {
        long now = System.currentTimeMillis();
        switch (timeRange) {
        case LAST_DAY:
            return new Interval(now - ONE_DAY, now);
        case LAST_MONTH:
            return new Interval(now - ONE_MONTH, now);
        case LAST_YEAR:
            return new Interval(now - ONE_YEAR, now);
        default:
            return new Interval(0, now);

        }
    }

    public static long getTimestampForTimeEnum(TimeEnum period) {
        long now = System.currentTimeMillis();
        switch (period) {
        case LAST_HOUR:
            return now - ONE_HOUR;
        case LAST_WEEK:
            return now - ONE_WEEK;
        case LAST_DAY:
            return now - ONE_DAY;
        case LAST_MONTH:
            return now - ONE_MONTH;
        case LAST_YEAR:
            return now - ONE_YEAR;
        case ALL:
            return 0;
        default:
            return now - ONE_WEEK;

        }
    }
}