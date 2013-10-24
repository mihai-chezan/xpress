package xpress;

import java.util.List;

/**
 * @author sechelc
 */
public interface Repository {

    void saveVote(Vote vote);

    List<Vote> getVotes(Filter queryVote);
}
