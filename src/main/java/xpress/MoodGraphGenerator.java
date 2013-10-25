/**
 * 
 */
package xpress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import xpress.GraphResponse.GraphResponseElement;
import xpress.storage.Filter;
import xpress.storage.Repository;
import xpress.storage.entity.VoteEntity;

/**
 * @author mcq
 * 
 */
public class MoodGraphGenerator {
    private final Repository repo;
    private final Map<TimeEnum, Long> splitIntervalMap;
    private final Map<TimeEnum, Integer> numberOfDataPoints;

    public MoodGraphGenerator(Repository repo) {
        super();
        this.repo = repo;
        splitIntervalMap = new HashMap<>();
        splitIntervalMap.put(TimeEnum.ALL, TimeUnit.DAYS.toMillis(30));
        splitIntervalMap.put(TimeEnum.LAST_YEAR, TimeUnit.DAYS.toMillis(30));
        splitIntervalMap.put(TimeEnum.LAST_MONTH, TimeUnit.DAYS.toMillis(1));
        splitIntervalMap.put(TimeEnum.LAST_WEEK, TimeUnit.DAYS.toMillis(1));
        splitIntervalMap.put(TimeEnum.LAST_DAY, TimeUnit.HOURS.toMillis(1));
        splitIntervalMap.put(TimeEnum.LAST_HOUR, TimeUnit.MINUTES.toMillis(1));

        numberOfDataPoints = new HashMap<>();
        numberOfDataPoints.put(TimeEnum.ALL, 30);
        numberOfDataPoints.put(TimeEnum.LAST_YEAR, 10);
        numberOfDataPoints.put(TimeEnum.LAST_MONTH, 30);
        numberOfDataPoints.put(TimeEnum.LAST_WEEK, 7);
        numberOfDataPoints.put(TimeEnum.LAST_DAY, 24);
        numberOfDataPoints.put(TimeEnum.LAST_HOUR, 60);
    }

    public GraphResponse compute(TimeEnum interval) {
        List<GraphResponseElement> series = new ArrayList<>();
        List<Vote> votes = getVotes(interval);
        if (!votes.isEmpty()) {
            series.addAll(generateMoodElements(interval, votes));
        }
        return new GraphResponse(series);
    }

    private List<GraphResponseElement> generateMoodElements(TimeEnum interval, List<xpress.Vote> votes) {
        // we are not guaranteed that votes are sorted so to make sure we Sort (slow!!); also make defensive copy so
        // that we don't generate side effects to Repo
        List<xpress.Vote> sortedVotes = new ArrayList<>(votes);
        Collections.sort(votes);
        long splitInterval = getSplitInterval(interval);
        int totalNumberOfDatapoints = numberOfDataPoints.get(interval);
        // votes are sparse so we must find out max number of datapoints, and for moods that don't have votes for a
        // certain interval, put zero
        long maxTime = System.currentTimeMillis();
        long minTime = maxTime - totalNumberOfDatapoints*splitInterval;

        Integer[] happyData = new Integer[totalNumberOfDatapoints];
        Integer[] unhappyData = new Integer[totalNumberOfDatapoints];
        Integer[] neutralData = new Integer[totalNumberOfDatapoints];
        Arrays.fill(unhappyData,0);
        Arrays.fill(neutralData,0);
        Arrays.fill(happyData,0);

        Map<Mood, Integer[]> moodDataMap = new HashMap<>();
        moodDataMap.put(Mood.HAPPY, happyData);
        moodDataMap.put(Mood.UNHAPPY, unhappyData);
        moodDataMap.put(Mood.NEUTRAL, neutralData);

        long indexOffset =(int) ( minTime / splitInterval);
        for (Vote v : sortedVotes) {
            int index = (int) ((v.getTime() / splitInterval) - indexOffset) - 1;
            if(index == totalNumberOfDatapoints){
                index = index -  1;
            }
            moodDataMap.get(v.getMood())[index]++;
        }


        GraphResponseElement happyElement = new GraphResponseElement(Mood.HAPPY.toString(), Arrays.asList(happyData));
        GraphResponseElement unhappyElement = new GraphResponseElement(Mood.UNHAPPY.toString(), Arrays.asList(unhappyData));
        GraphResponseElement neutralElement = new GraphResponseElement(Mood.NEUTRAL.toString(), Arrays.asList(neutralData));
        return Arrays.asList(new GraphResponseElement[] { happyElement, unhappyElement, neutralElement });
    }

    private List<xpress.Vote> getVotes(TimeEnum interval) {
        Filter f = new Filter();
        f.setTime(interval);
        List<xpress.Vote> result = new ArrayList<>();
        final List<VoteEntity> voteEntities = repo.getVotes(f);
        for (VoteEntity voteEntity : voteEntities) {
            xpress.Vote v = new xpress.Vote(voteEntity.getMood(), voteEntity.getTag());
            v.setTime(voteEntity.getTime());
            result.add(v);
        }
        return result;
    }

    private long getSplitInterval(TimeEnum interval) {
        return splitIntervalMap.get(interval);
    }
}
