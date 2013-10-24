/**
 * 
 */
package xpress;

/**
 * @author mcq
 *
 */
public class Vote {
	private final Mood mood;
	private final String tag;
	
	public Vote(Mood mood, String tag) {
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
	
}
