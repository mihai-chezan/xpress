package xpress.storage;

import xpress.storage.entity.VoteEntity;

import java.util.List;

/**
 * @author sechelc
 */
public interface Repository {

    void saveVote(VoteEntity voteEntity);

    List<VoteEntity> getVotes(Filter queryVote);
}
