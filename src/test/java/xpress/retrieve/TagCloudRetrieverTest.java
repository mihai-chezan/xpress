package xpress.retrieve;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import xpress.Mood;
import xpress.TagCloud;
import xpress.storage.Filter;
import xpress.storage.Repository;
import xpress.storage.entity.VoteEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

public class TagCloudRetrieverTest {

    private TagCloudRetriever tagCloudRetriever;
    private Repository mockRepo;

    @BeforeMethod
    public void setup() throws Exception {
        mockRepo = mock(Repository.class);
        tagCloudRetriever = new TagCloudRetriever(mockRepo);
    }

    @Test
    public void testMoreVotesMoreWeight() throws Exception {
        long now = Calendar.getInstance().getTimeInMillis();
        String tag1 = "biscuits";
        List<VoteEntity> returnedVotes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            VoteEntity v = new VoteEntity();
            v.setMood(Mood.HAPPY);
            v.setTag(tag1);
            v.setTime(now);
            returnedVotes.add(v);
        }
        String tag2 = "other biscuits";
        VoteEntity v2 = new VoteEntity();
        v2.setMood(Mood.HAPPY);
        v2.setTag(tag2);
        v2.setTime(now);
        VoteEntity singleVote = v2;
        returnedVotes.add(singleVote);

        when(mockRepo.getVotes(Mockito.any(Filter.class))).thenReturn(returnedVotes);

        TagCloud tags = tagCloudRetriever.retrieveTagCloudsFor(Mood.HAPPY);
        Integer weigthMoreVotes = tags.getContent().get(tag1);
        Integer weightLessWotes = tags.getContent().get(tag2);
        assertTrue(weigthMoreVotes > weightLessWotes);
    }

    @Test
    public void testMoreRecentMoreWeight() throws Exception {
        String tagForOlderVote = "biscuits";
        //one older vote that happened 44 minutes ago
        VoteEntity v1 = new VoteEntity();
        v1.setMood(Mood.HAPPY);
        v1.setTag(tagForOlderVote);
        v1.setTime(Calendar.getInstance().getTimeInMillis() - TimeUnit.MINUTES.toMillis(44));
        VoteEntity olderVote = v1;

        String tagForRecentVote = "muffins";
        VoteEntity v2 = new VoteEntity();
        v2.setMood(Mood.HAPPY);
        v2.setTag(tagForRecentVote);
        v2.setTime(Calendar.getInstance().getTimeInMillis());
        VoteEntity recentVote = v2;

        when(mockRepo.getVotes(Mockito.any(Filter.class))).thenReturn(Arrays.asList(recentVote, olderVote));
        TagCloud tags = tagCloudRetriever.retrieveTagCloudsFor(Mood.HAPPY);
        Integer weightOlderVote = tags.getContent().get(tagForOlderVote);
        Integer weightRecentVote = tags.getContent().get(tagForRecentVote);
        assertTrue(weightRecentVote > weightOlderVote);
    }


}
