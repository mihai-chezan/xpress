package xpress.retrieve;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import xpress.Filter;
import xpress.Mood;
import xpress.Repository;
import xpress.TagCloud;
import xpress.Vote;

public class TagCloudRetriever {

    private final Repository repo;

    public TagCloudRetriever(Repository repo) {
        this.repo = repo;
    }

    public TagCloud retrieveTagCloudsFor(Mood mood) {
        Filter filter = new Filter();
        filter.setMood(mood);
        List<Vote> votes = repo.getVotes(filter);
        return buildTagCloudBasedOn(votes);
    }

    private TagCloud buildTagCloudBasedOn(List<Vote> votes) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Vote vote : votes) {
            String tagName = vote.getTag();
            Integer numVotes = map.get(tagName);
            if (numVotes == null) {
                numVotes = Integer.valueOf(1);
            } else {
                numVotes++;
            }
            map.put(tagName, numVotes);
        }
        return new TagCloud(1, map);
    }

}
