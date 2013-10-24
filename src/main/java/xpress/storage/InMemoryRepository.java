package xpress.storage;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;

import xpress.TimeEnum;
import xpress.Vote;

/**
 * @author sechelc
 */
public class InMemoryRepository implements Repository {

    private static InMemoryRepository inMemoryRepository = new InMemoryRepository();

    private InMemoryRepository() {

    }

    public static InMemoryRepository getInstance() {
        return inMemoryRepository;
    }

    private List<Vote> voteList = new ArrayList<>();
    private final static long ONE_DAY = 1000 * 60 * 60 * 24;
    private final static long ONE_MONTH = ONE_DAY * 30;
    private final static long ONE_YEAR = ONE_MONTH * 12;

    @Override
    public void saveVote(Vote vote) {
        voteList.add(vote);
    }

    @Override
    public List<Vote> getVotes(Filter queryVote) {
        List<Vote> result = new ArrayList<>();
        boolean applyMoodQuery = false;
        boolean applyTimeQuery = false;
        boolean applyTagQuery = false;
        for (Vote vote : voteList) {
            if (queryVote.getMood() != null) {
                applyMoodQuery = true;
            }

            if (queryVote.getTag() != null) {
                applyTagQuery = true;
            }

            if (queryVote.getTime() != null) {
                applyTimeQuery = true;
            }
            boolean shouldAdd = true;
            shouldAdd = applyMoodQuery ? vote.getMood().name().equals(queryVote.getMood().name()) : shouldAdd;
            shouldAdd = applyTagQuery ? vote.getTag().trim().equalsIgnoreCase(queryVote.getTag()) : shouldAdd;
            shouldAdd = applyTimeQuery ? isInTimeRange(vote.getTime(), queryVote.getTime()) : shouldAdd;
            if (shouldAdd) {
                result.add(vote);
            }
        }
        return result;
    }

    private boolean isInTimeRange(long time, TimeEnum timeRange) {
        long now = System.currentTimeMillis();
        Interval interval = getIntervalFormTimeEnum(timeRange, now);
        return interval.contains(time);
    }

    private Interval getIntervalFormTimeEnum(TimeEnum timeRange, long now) {
        switch (timeRange) {
        case LAST_DAY:
            return new Interval(now - ONE_DAY, now);
        case LAST_MONTH:
            return new Interval(now - ONE_MONTH, now);
        case LAST_YEAR:
            return new Interval(now - ONE_YEAR, now);
        default:
            return new Interval(0, now);

        }
    }
}
