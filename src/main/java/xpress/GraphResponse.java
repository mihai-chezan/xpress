/**
 * 
 */
package xpress;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mcq
 *
 */
public class GraphResponse {
	@JsonProperty
	private final List<GraphResponseElement> series;
	
	public GraphResponse(@JsonProperty("series") List<GraphResponseElement> series) {
		super();
		this.series = series;
	}

	public List<GraphResponseElement> getContent() {
		return series;
	}
	
	public static class GraphResponseElement {
		@JsonProperty
		private final String name;
		@JsonProperty
		private final List<Integer> data;
		
		public GraphResponseElement(@JsonProperty("name") String name, @JsonProperty("data") List<Integer> data) {
			super();
			this.name = name;
			this.data = data;
		}
		public String getName() {
			return name;
		}
		public List<Integer> getData() {
			return data;
		}
	}
}
