/**
 * 
 */
package xpress;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import xpress.retrieve.TagCloudRetriever;

import com.yammer.metrics.annotation.Timed;

/**
 * @author mcq
 *  fancy comment
 */
@Path("/tags/{mood}")
@Produces(MediaType.APPLICATION_JSON)
public class TagCloudResource {

    private final TagCloudRetriever tagCloudRetriever;

    public TagCloudResource(TagCloudRetriever tagCloudRetriever) {
        this.tagCloudRetriever = tagCloudRetriever;
    }

    @GET
	@Timed
	public TagCloud getTagCloud(@PathParam("mood") Mood mood) {
        return tagCloudRetriever.retrieveTagCloudsFor(mood);
	}
}
