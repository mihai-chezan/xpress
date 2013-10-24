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
        List<GraphResponseElement> series = new ArrayList<>();
        series.addAll(generateMoodElements(interval, getVotes(interval)));
        return new GraphResponse(series);
    }

    private List<GraphResponseElement> generateMoodElements(TimeEnum interval, List<Vote> votes) {
        // we are not guaranteed that votes are sorted so to make sure we Sort (slow!!); also make defensive copy so
        // that we don't generate side effects to Repo
        List<Vote> sortedVotes = new ArrayList<>(votes);
        Collections.sort(votes);
        long splitInterval = getSplitInterval(interval);
        // votes are sparse so we must find out max number of datapoints, and for moods that don't have votes for a
        // certain interval, put zero
        long maxTime = sortedVotes.get(sortedVotes.size() - 1).getTime();
        long minTime = sortedVotes.get(0).getTime();
        long totalTimeSpan = maxTime - minTime;
        long totalNumberOfDatapoints = (totalTimeSpan / splitInterval);
        if ((totalNumberOfDatapoints * splitInterval) < totalTimeSpan) {
            totalNumberOfDatapoints++;
        }

        List<Integer> happyData = new ArrayList<>();
        GraphResponseElement happyElement = new GraphResponseElement(Mood.HAPPY.toString(), happyData);

        List<Integer> unhappyData = new ArrayList<>();
        GraphResponseElement unhappyElement = new GraphResponseElement(Mood.UNHAPPY.toString(), unhappyData);

        List<Integer> neutralData = new ArrayList<>();
        GraphResponseElement neutralElement = new GraphResponseElement(Mood.NEUTRAL.toString(), neutralData);

        int index = 0;
        int countHappy = 0;
        int countUnhappy = 0;
        int countNeutral = 0;
        for (Vote v : sortedVotes) {
            int newIndex = (int) ((v.getTime() - minTime) % totalNumberOfDatapoints);
            if (newIndex != index) {
                happyData.add(index, countHappy);
                unhappyData.add(index, countUnhappy);
                neutralData.add(index, countNeutral);
                index = newIndex;
                countHappy = 0;
                countUnhappy = 0;
                countNeutral = 0;
            }
            switch (v.getMood()) {
            case HAPPY:
                countHappy++;
                break;
            case UNHAPPY:
                countUnhappy++;
                break;
            case NEUTRAL:
                countNeutral++;
                break;
            default:
                break;
            }
        }
        happyData.add(index, countHappy);
        unhappyData.add(index, countUnhappy);
        neutralData.add(index, countNeutral);

        return Arrays.asList(new GraphResponseElement[] { happyElement, unhappyElement, neutralElement });
    }

    private List<Vote> getVotes(TimeEnum interval) {
        Filter f = new Filter();
        f.setTime(interval);
        return repo.getVotes(f);
    }

    private long getSplitInterval(TimeEnum interval) {
        return splitIntervalMap.get(interval);
    }
}
