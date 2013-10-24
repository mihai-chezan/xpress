/**
 * 
 */
package xpress;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author mcq
 *       a
 */
public class Vote implements Comparable<Vote>{
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

    @Override
    public int compareTo(Vote o) {
        return Long.compare(this.time, o.time);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mood == null) ? 0 : mood.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        result = prime * result + (int) (time ^ (time >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vote other = (Vote) obj;
        if (mood != other.mood)
            return false;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (time != other.time)
            return false;
        return true;
    }
}
