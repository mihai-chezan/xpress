/**
 * 
 */
package xpress;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author mcq
 *       a
 */
public class Vote {
	@JsonProperty
	private final Mood mood;
	@JsonProperty
	private final String tag;
    private long time;

    public Vote(@JsonProperty("mood") Mood mood, @JsonProperty("tag") String tag) {
		super();
		this.mood = mood;
		this.tag = tag;
	}
	
	public Mood getMood() {
		return mood;
	}
	
	public String getTag() {
		return tag;
	}

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
