package xpress.storage;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xpress.Mood;
import xpress.TimeEnum;
import xpress.storage.entity.VoteEntity;

import java.util.List;

/**
 * @author sechelc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class DBRepositoryTest {

    public static final Mood HAPPY = Mood.HAPPY;
    public static final String TTTTT = "ttttt";
    public static final int TIME = 1;

    @Autowired
    private Repository dbRepository;

    @Test
    public void testSaveAndGetVote() throws Exception {
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setTag(TTTTT);
        voteEntity.setMood(Mood.HAPPY);
        voteEntity.setTime(1);
        dbRepository.saveVote(voteEntity);
        final Filter queryVote = new Filter();
        queryVote.setMood(HAPPY);
        queryVote.setTag(TTTTT);
        queryVote.setTime(TimeEnum.ALL);
        List<VoteEntity> voteEntities = dbRepository.getVotes(queryVote);
        Assert.assertTrue(voteEntities.size() == 1);

    }

}
