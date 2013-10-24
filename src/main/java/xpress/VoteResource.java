/**
 * 
 */
package xpress;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yammer.metrics.annotation.Timed;

/**
 * @author mcq
 *
 */
@Path("/vote")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VoteResource {

	@POST
	@Timed
	public VoteResponse addVote(Vote vote) {
		return new VoteResponse(1, "ok");
	}
}
