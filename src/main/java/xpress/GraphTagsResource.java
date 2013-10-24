package xpress;

import java.util.Map;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.collect.Maps;
import com.yammer.metrics.annotation.Timed;

@Path("/graphs/tags")
@Produces(MediaType.APPLICATION_JSON)
public class GraphTagsResource {

    @GET
    @Timed
    public Map<String, Integer> getTags() {
        return getDummyTags();
    }

    // TODO CP: remove this part
    private static final Random RANDOM_GENERATOR = new Random();

    private Map<String, Integer> getDummyTags() {
        Map<String, Integer> result = Maps.newConcurrentMap();
        for (Integer i = 0; i < 10; i++) {
            result.put(RandomStringUtils.randomAlphabetic(8), RANDOM_GENERATOR.nextInt(100));
        }
        return result;
    }
}
