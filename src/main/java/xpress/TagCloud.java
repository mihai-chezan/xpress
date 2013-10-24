/**
 * 
 */
package xpress;

import java.util.Map;

/**
 * @author mcq
 *
 */
public class TagCloud {
	private final long id;
	private final Map<String, Integer> content;
	
	public TagCloud(long id, Map<String, Integer> content) {
		super();
		this.id = id;
		this.content = content;
	}
	public long getId() {
		return id;
	}
	public Map<String, Integer> getContent() {
		return content;
	}
}
