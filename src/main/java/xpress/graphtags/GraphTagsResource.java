package xpress.graphtags;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xpress.ChartType;
import xpress.Mood;
import xpress.TimeEnum;

import com.google.common.collect.Maps;
import com.yammer.metrics.annotation.Timed;

@Path("/graphs/tags")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class GraphTagsResource {

    @Autowired
    private TagRetriever tagRetriever;

    @GET
    @Timed
    public Map<String, Integer> getTags() {
        List<String> tags = tagRetriever.retrieve(TimeEnum.LAST_MONTH);
        return getDummyTags();
    }

    @GET
    @Timed
    @Path("{tagName}/{period:(.+)?}")
    public Map<String, Integer> getTagVotesDuringPeriod(@PathParam("tagName") String tagName, @PathParam("period") TimeEnum period) {
        return getDummyPeriodTags();
    }

    @GET
    @Timed
    @Path("{tagName}/{period}/{chartType:(.+)?}")
    public Map<String, Integer> getTagVotesDuringPeriodForChart(@PathParam("tagName") String tagName,
            @PathParam("period") TimeEnum period, @PathParam("chartType") ChartType chartType) {
        return getDummyPeriodTags();
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

    private Map<String, Integer> getDummyPeriodTags() {
        Map<String, Integer> result = Maps.newConcurrentMap();
        for (Mood mood : Mood.values()) {
            result.put(mood.name(), RANDOM_GENERATOR.nextInt(100));
        }
        return result;
    }

    public void setTagRetriever(TagRetriever tagRetriever) {
        this.tagRetriever = tagRetriever;
    }
}
