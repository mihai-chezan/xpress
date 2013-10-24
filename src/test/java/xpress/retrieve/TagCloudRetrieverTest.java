package xpress.retrieve;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import xpress.Mood;
import xpress.TagCloud;
import xpress.Vote;
import xpress.storage.Filter;
import xpress.storage.Repository;

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
        List<Vote> returnedVotes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Vote e = new Vote(Mood.HAPPY, tag1);
            e.setTime(now);
            returnedVotes.add(e);
        }
        String tag2 = "other biscuits";
        Vote singleVote = new Vote(Mood.HAPPY, tag2);
        singleVote.setTime(now + 1000);
        returnedVotes.add(singleVote);

        when(mockRepo.getVotes(Mockito.any(Filter.class))).thenReturn(returnedVotes);

        TagCloud tags = tagCloudRetriever.retrieveTagCloudsFor(Mood.HAPPY);
        Integer weigthMoreVotes = tags.getContent().get(tag1);
        Integer weightLessWotes = tags.getContent().get(tag2);
        assertTrue(weigthMoreVotes > weightLessWotes);
    }

    @Test(enabled = false)
    public void testMostRecentIsHigherThanLeastRecent() throws Exception {
        String tag1 = "biscuits";
        Vote olderVote = new Vote(Mood.HAPPY, tag1);
        olderVote.setTime(Calendar.getInstance().getTimeInMillis()-1);

        TimeUnit.MILLISECONDS.sleep(100);
        String tag2 = "same biscuits";
        Vote recentVote = new Vote(Mood.HAPPY, tag2);
        recentVote.setTime(Calendar.getInstance().getTimeInMillis());

        when(mockRepo.getVotes(Mockito.any(Filter.class))).thenReturn(Arrays.asList(recentVote, olderVote));
        TagCloud tags = tagCloudRetriever.retrieveTagCloudsFor(Mood.HAPPY);
        Integer weightRecentVote = tags.getContent().get(tag1);
        Integer weightOlderVote = tags.getContent().get(tag2);
        assertTrue(weightRecentVote > weightOlderVote);
    }


}
