package xpress.storage;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;

import xpress.TimeEnum;
import xpress.storage.entity.VoteEntity;

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

    private List<VoteEntity> voteEntityList = new ArrayList<>();

    @Override
    public void saveVote(VoteEntity voteEntity) {
        voteEntityList.add(voteEntity);
    }

    @Override
    public List<VoteEntity> getVotes(Filter queryVote) {
        List<VoteEntity> result = new ArrayList<>();
        boolean applyMoodQuery = false;
        boolean applyTimeQuery = false;
        boolean applyTagQuery = false;
        if (queryVote.getMood() != null) {
            applyMoodQuery = true;
        }
        if (queryVote.getTag() != null) {
            applyTagQuery = true;
        }
        if (queryVote.getTime() != null) {
            applyTimeQuery = true;
        }
        for (VoteEntity voteEntity : voteEntityList) {
            boolean shouldAdd = true;
            shouldAdd = applyMoodQuery ? voteEntity.getMood() == queryVote.getMood() : shouldAdd;
            if (!shouldAdd) {
                continue;
            }
            shouldAdd = applyTagQuery ? voteEntity.getTag().trim().equalsIgnoreCase(queryVote.getTag()) : shouldAdd;
            if (!shouldAdd) {
                continue;
            }
            shouldAdd = applyTimeQuery ? isInTimeRange(voteEntity.getTime(), queryVote.getTime()) : shouldAdd;
            if (shouldAdd) {
                result.add(voteEntity);
            }
        }
        return result;
    }

    private boolean isInTimeRange(long time, TimeEnum timeRange) {
        Interval interval = Utils.getIntervalFormTimeEnum(timeRange);
        return interval.contains(time);
    }

}
