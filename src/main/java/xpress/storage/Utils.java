package xpress.storage;

import org.joda.time.Interval;
import xpress.TimeEnum;

public class Utils {
    private final static long ONE_DAY = 1000 * 60 * 60 * 24;
    private final static long ONE_MONTH = ONE_DAY * 30;
    private final static long ONE_YEAR = ONE_MONTH * 12;

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
}