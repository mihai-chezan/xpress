package xpress.storage.entity;

import java.util.Map;

import xpress.Mood;

import com.google.common.collect.Maps;

public class TagByMood {

    private String tag;

    private Map<Mood, Integer> frequency;

    private Integer totalFrequency;

    public TagByMood() {
        frequency = Maps.newConcurrentMap();
        for (Mood mood : Mood.values()) {
            frequency.put(mood, 0);
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Map<Mood, Integer> getFrequency() {
        return frequency;
    }

    public Integer getTotalFrequency() {
        return totalFrequency;
    }

    public void setTotalFrequency(Integer totalFrequency) {
        this.totalFrequency = totalFrequency;
    }

}
