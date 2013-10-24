package xpress.retrieve;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import xpress.Mood;
import xpress.TagCloud;
import xpress.Vote;
import xpress.storage.Filter;
import xpress.storage.Repository;

public class TagCloudRetriever {

    public class VoteSummary {

        private String tagName;
        private int numVotes;
        private long mostRecentTime;

        public VoteSummary(String tagName, int numVotes, long mostRecentTime) {
            this.tagName = tagName;
            this.numVotes = numVotes;
            this.mostRecentTime = mostRecentTime;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public int getNumVotes() {
            return numVotes;
        }

        public void setNumVotes(int numVotes) {
            this.numVotes = numVotes;
        }

        public long getMostRecentTime() {
            return mostRecentTime;
        }

        public void setMostRecentTime(long mostRecentTime) {
            this.mostRecentTime = mostRecentTime;
        }

    }

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
        if (votes.isEmpty()) {
            return new TagCloud(1, map);
        }
        //Also keep track of oldest & most recent time used for votes, regardless of tag names etc..
        long oldestTimeOfAll = votes.get(0).getTime();
        long mostRecentTimeOfAll = votes.get(0).getTime();
        /**
         * This map will collaps all votes by tagName. For each tag, it will holds the sum of votes, and the most recent time this tag was updated.
         */
        Map<String, VoteSummary> collapsedStats = new HashMap<String, VoteSummary>();
        for (Vote vote : votes) {
            long voteTime = vote.getTime();
            //if x is less than known oldest time..
            if (voteTime < oldestTimeOfAll) {
                oldestTimeOfAll = voteTime;
            }
            //if the most recent time is actually older than x..
            if (mostRecentTimeOfAll < voteTime) {
                mostRecentTimeOfAll = voteTime;
            }
            //sum up the votes for each tag
            String tagName = vote.getTag();
            VoteSummary voteSummary = collapsedStats.get(tagName);
            if (voteSummary == null) {
                voteSummary = new VoteSummary(tagName, 1, vote.getTime());
            } else {
                voteSummary = new VoteSummary(tagName, voteSummary.getNumVotes() + 1, getMostRecentTimeForThisTag(vote.getTime(),
                        voteSummary.getMostRecentTime()));
            }
            collapsedStats.put(tagName, voteSummary);
        }
        //update the map so that it holds the 'weight' of each tag
        for (Entry<String, VoteSummary> entry : collapsedStats.entrySet()) {
            String tagName = entry.getKey();
            VoteSummary voteSummary = entry.getValue();
            int weigthByVotes = (int) (0.5 * voteSummary.getNumVotes()); //50% is by numVotes
            int weightByTime = (int) (0.5 * computeWeightByTime(oldestTimeOfAll, mostRecentTimeOfAll, voteSummary.getMostRecentTime())); // 50 % by time
            map.put(tagName, weigthByVotes + weightByTime);
        }

        return new TagCloud(1, map);
    }

    private long getMostRecentTimeForThisTag(long a, long b) {
        return Math.max(a, b);
    }

    private int computeWeightByTime(long oldestTime, long mostRecentTime, long x) {
        //oldest---------------d1--------------X---d2----mostRecent
        //try to give percentage of how close x is to d2. if it's up to 100 % then it's 100 weight..
        long d1 = x - oldestTime;
        long d2 = mostRecentTime - x;
        long value = 0;
        if (d2 != 0) {
            value = d1 / d2;
        } else {
            value = d1;
        }
        while (value > 100) {
            value = value / 10;
        }
        return (int) value; // now safe to cast it since it < 100
    }

}
