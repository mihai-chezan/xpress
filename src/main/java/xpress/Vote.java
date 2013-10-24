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
    @JsonProperty
    private long time;

    public Vote(@JsonProperty("mood") Mood mood, @JsonProperty("tag") String tag, @JsonProperty long time) {
		super();
		this.mood = mood;
		this.tag = tag;
        this.time = time;
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
}
