package xpress.retrieve;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import xpress.Mood;
import xpress.TagCloud;
import xpress.storage.Filter;
import xpress.storage.Repository;
import xpress.storage.Utils;
import xpress.storage.entity.VoteEntity;

public class TagCloudRetriever {


    private final Repository repo;

    public TagCloudRetriever(Repository repo) {
        this.repo = repo;
    }

    public TagCloud retrieveTagCloudsFor(Mood mood) {
        Filter filter = new Filter();
        filter.setMood(mood);
        List<VoteEntity> voteEntities = repo.getVotes(filter);
        return buildTagCloudBasedOn(voteEntities);
    }

    private TagCloud buildTagCloudBasedOn(List<VoteEntity> voteEntities) {
        Map<String, Integer> map = new LinkedHashMap<>();
        if (voteEntities.isEmpty()) {
            return new TagCloud(1, map);
        }
        //Also keep track of oldest & most recent time used for voteEntities, regardless of tag names etc..
        long oldestTimeOfAll = voteEntities.get(0).getTime();
        long mostRecentTimeOfAll = voteEntities.get(0).getTime();

        /**
         * This map will collaps all voteEntities by tagName. For each tag, it will holds the sum of voteEntities, and the most recent time this tag was updated.
         */
        Map<String, VoteSummary> collapsedStats = new HashMap<String, VoteSummary>();
        for (VoteEntity voteEntity : voteEntities) {
            long voteTime = voteEntity.getTime();
            //if x is less than known oldest time..
            if (voteTime < oldestTimeOfAll) {
                oldestTimeOfAll = voteTime;
            }
            //if the most recent time is actually older than x..
            if (mostRecentTimeOfAll < voteTime) {
                mostRecentTimeOfAll = voteTime;
            }
            //sum up the voteEntities for each tag
            String tagName = voteEntity.getTag().trim();
            if (tagName != null) {
                VoteSummary voteSummary = collapsedStats.get(tagName);
                if (voteSummary == null) {
                    voteSummary = new VoteSummary(tagName, 1, voteEntity.getTime());
                } else {
                    voteSummary = new VoteSummary(tagName, voteSummary.getNumVotes() + 1, getMostRecentTimeForThisTag(voteEntity.getTime(),
                            voteSummary.getMostRecentTime()));
                }
                collapsedStats.put(tagName, voteSummary);
            }
        }
        //        System.out.println("Oldest of them all:" + Utils.prettyPrintDate(oldestTimeOfAll));
        //        System.out.println("Youngest of them all:" + Utils.prettyPrintDate(mostRecentTimeOfAll));
        //update the map so that it holds the 'weight' of each tag
        for (Entry<String, VoteSummary> entry : collapsedStats.entrySet()) {
            String tagName = entry.getKey();
            VoteSummary voteSummary = entry.getValue();
            //compute weights, 70% by numVotes and 30% by time
            int weigthByVotes = (int) (Math.ceil(0.9 * voteSummary.getNumVotes()));
            int weightByTime = (int) (Math.ceil(0.1 * computeWeightByTime(oldestTimeOfAll, mostRecentTimeOfAll, voteSummary
                    .getMostRecentTime())));
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
        if (x == oldestTime)
            return 0;
        if (x == mostRecentTime)
            return 100;

        long totalLenght = mostRecentTime - oldestTime;
        long lengthOfX = x - oldestTime;
        int percent = (int) Math.ceil(lengthOfX * 100 / totalLenght);

        //        System.out.println("Computed score for " + Utils.prettyPrintDate(x) + " vs interval [" + Utils.prettyPrintDate(oldestTime) + ","
        //                + Utils.prettyPrintDate(mostRecentTime) + "] was: " + percent);
        return (int) percent; // now safe to cast it since it < 100
    }

    /**
     * 
     * 
     * @author Tiberiu Rogojan
     *
     */
    private class VoteSummary {

        private final String tagName;
        private final int numVotes;
        private final long mostRecentTime;

        public VoteSummary(String tagName, int numVotes, long mostRecentTime) {
            this.tagName = tagName;
            this.numVotes = numVotes;
            this.mostRecentTime = mostRecentTime;
        }

        public String getTagName() {
            return tagName;
        }

        public int getNumVotes() {
            return numVotes;
        }

        public long getMostRecentTime() {
            return mostRecentTime;
        }

        @Override
        public String toString() {
            return "VoteSummary [tagName=" + tagName + ", numVotes=" + numVotes + ", mostRecentTime="
                    + Utils.prettyPrintDate(mostRecentTime) + "]";
        }

    }
}
