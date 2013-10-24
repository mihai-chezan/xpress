/**
 * 
 */
package xpress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import xpress.GraphResponse.GraphResponseElement;
import xpress.storage.Filter;
import xpress.storage.Repository;

/**
 * @author mcq
 * 
 */
public class MoodGraphGenerator {
    private final Repository repo;
    private final Map<TimeEnum, Long> splitIntervalMap;

    public MoodGraphGenerator(Repository repo) {
        super();
        this.repo = repo;
        splitIntervalMap = new HashMap<>();
        splitIntervalMap.put(TimeEnum.ALL, TimeUnit.DAYS.toMillis(30));
        splitIntervalMap.put(TimeEnum.LAST_YEAR, TimeUnit.DAYS.toMillis(30));
        splitIntervalMap.put(TimeEnum.LAST_MONTH, TimeUnit.DAYS.toMillis(1));
        splitIntervalMap.put(TimeEnum.LAST_DAY, TimeUnit.HOURS.toMillis(1));
    }

    public GraphResponse compute(TimeEnum interval) {
        return generateGraph(interval, getVotes(interval, Mood.HAPPY), getVotes(interval, Mood.UNHAPPY), getVotes(interval, Mood.NEUTRAL));
    }

    private GraphResponse generateGraph(TimeEnum interval, List<Vote> happyVotes, List<Vote> unhappyVotes, List<Vote> neutralVotes) {
        List<GraphResponseElement> series = new ArrayList<>();
        series.add(generateMoodElement(Mood.HAPPY, interval, happyVotes));
        series.add(generateMoodElement(Mood.UNHAPPY, interval, unhappyVotes));
        series.add(generateMoodElement(Mood.NEUTRAL, interval, neutralVotes));
        return new GraphResponse(series);
    }

    private GraphResponseElement generateMoodElement(Mood mood, TimeEnum interval, List<Vote> votes) {
        List<Integer> data = new ArrayList<>();
        GraphResponseElement result = new GraphResponseElement(mood.toString(), data);
        long splitInterval = getSplitInterval(interval);
        Map<Long, Integer> counters = new HashMap<>();
        for (Vote v:votes) {
            Long index = v.getTime() / splitInterval;
            Integer count = counters.get(index);
            if (count == null) {
                counters.put(index, 1);
            } else {
                counters.put(index, count + 1);
            }
        }
        ArrayList<Long> sortedKeys = new ArrayList<>(counters.keySet());
        Collections.sort(sortedKeys);
        for (Long key:sortedKeys) {
            data.add(counters.get(key));
        }
        return result;
    }

    private List<Vote> getVotes(TimeEnum interval, Mood mood) {
        Filter f = new Filter();
        f.setTime(interval);
        f.setMood(mood);
        return repo.getVotes(f);
    }
    
    private long getSplitInterval(TimeEnum interval) {
        return splitIntervalMap.get(interval);
    }
}
