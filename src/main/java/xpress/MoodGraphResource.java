/**
 * 
 */
package xpress;

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
@Path("/graphs/moods/{timeinterval}")
@Produces(MediaType.APPLICATION_JSON)
public class MoodGraphResource {
	private final MoodGraphGenerator gen;

	public MoodGraphResource(MoodGraphGenerator gen) {
		super();
		this.gen = gen;
	}

	@GET
	@Timed
	public GraphResponse getMoodGraph(@PathParam("timeinterval") TimeEnum timeinterval) {
		return gen.compute(timeinterval);
	}
}
