package xpress;

/**
 * @author sechelc
 */
public class Query {
    String tag;
    Mood mood;
    TimeEnum time;

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
}
