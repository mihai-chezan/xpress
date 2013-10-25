package xpress.storage.entity;

import java.util.Map;

import xpress.Mood;

public class TagByMood {

    private String tag;

    private Map<Mood, Integer> frequency;

    private Integer totalFrequency;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Map<Mood, Integer> getFrequency() {
        return frequency;
    }

    public void setFrequency(Map<Mood, Integer> frequency) {
        this.frequency = frequency;
    }

    public Integer getTotalFrequency() {
        return totalFrequency;
    }

    public void setTotalFrequency(Integer totalFrequency) {
        this.totalFrequency = totalFrequency;
    }

}
