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
import xpress.storage.Repository;
import xpress.storage.entity.VoteEntity;

/**
 * @author mcq
 *
 */
@Path("/vote")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VoteResource {

    private Repository repository;
    
    public VoteResource(Repository repository) {
        this.repository = repository;
    }

	@POST
	@Timed
	public VoteResponse addVote(Vote vote) {
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setTime(System.currentTimeMillis());
        voteEntity.setTag(vote.getTag());
        voteEntity.setMood(vote.getMood());
        repository.saveVote(voteEntity);
		return new VoteResponse(1, "ok");
	}

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
