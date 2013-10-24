/**
 * 
 */
package xpress;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yammer.metrics.annotation.Timed;

/**
 * @author mcq
 *
 */
@Path("/tags/{mood}")
@Produces(MediaType.APPLICATION_JSON)
public class TagCloudResource {

	@GET
	@Timed
	public TagCloud getTagCloud(@PathParam("mood") Mood mood) {
		Map<String, Integer> tagCloud = new HashMap<>();
		
		if (mood == Mood.HAPPY) {
			tagCloud.put("cougar", 1);
			tagCloud.put("mantis", 1);
			tagCloud.put("mantis performance", 4);
			tagCloud.put("team", 10);
		} else if (mood == Mood.UNHAPPY) {
			tagCloud.put("cougar", 10);
			tagCloud.put("mantis", 5);
			tagCloud.put("food quality", 15);
			tagCloud.put("food quantity", 8);
		} else {
			tagCloud.put("cougar", 10);
			tagCloud.put("mantis", 5);
			tagCloud.put("mantis performance", 2);
		}
		return new TagCloud(1, tagCloud);
	}
}
