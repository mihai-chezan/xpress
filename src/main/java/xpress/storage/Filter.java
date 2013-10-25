package xpress.storage;

import org.apache.commons.lang3.StringUtils;

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

    public boolean isEmpty() {
        return StringUtils.isBlank(tag) && mood == null && time == null && chartType == null;
    }

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

    public static final class Builder {
        String tag;
        Mood mood;
        TimeEnum time;
        ChartType chartType;

        public Filter build() {
            Filter filter = new Filter();
            if (tag != null) {
                filter.setTag(tag);
            }
            if (time != null) {
                filter.setTime(time);
            }
            if (mood != null) {
                filter.setMood(mood);
            }
            if (chartType != null) {
                filter.setChartType(chartType);
            }
            return filter;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder mood(Mood mood) {
            this.mood = mood;
            return this;
        }

        public Builder timeEnum(TimeEnum time) {
            this.time = time;
            return this;
        }

        public Builder chartType(ChartType chartType) {
            this.chartType = chartType;
            return this;
        }
    }
}
