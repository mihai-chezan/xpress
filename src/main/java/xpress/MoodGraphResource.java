/**
 * 
 */
package xpress;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import xpress.GraphResponse.GraphResponseElement;

import com.yammer.metrics.annotation.Timed;

/**
 * @author mcq
 *
 */
@Path("/graphs/moods/{timeinterval}")
@Produces(MediaType.APPLICATION_JSON)
public class MoodGraphResource {

	@GET
	@Timed
	public GraphResponse getMoodGraph(@PathParam("timeinterval") TimeEnum timeinterval) {
		List<Integer> data = new ArrayList<>();
		data.add(5);
		GraphResponseElement e = new GraphResponseElement(Mood.HAPPY.toString(), data);
		
		List<GraphResponseElement> series = new ArrayList<>();
		series.add(e);
		GraphResponse result = new GraphResponse(series);
		return result;
	}
}
