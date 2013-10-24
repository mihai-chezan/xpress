package xpress.storage;

import xpress.Vote;
import xpress.storage.Filter;

import java.util.List;

/**
 * @author sechelc
 */
public interface Repository {

    void saveVote(Vote vote);

    List<Vote> getVotes(Filter queryVote);
}
