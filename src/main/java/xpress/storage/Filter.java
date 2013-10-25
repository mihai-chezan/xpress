package xpress.storage;

import xpress.Mood;
import xpress.TimeEnum;
import xpress.graphtags.ChartType;

/**
 * @author sechelc
 */
public class Filter {
    String tag;
    Mood mood;
    TimeEnum time;
    ChartType chartType;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public TimeEnum getTime() {
        return time;
    }

    public void setTime(TimeEnum time) {
        this.time = time;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }
}
