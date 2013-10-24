package xpress.retrieve;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
            returnedVotes.add(new Vote(Mood.HAPPY, tag1, now));
        }
        String tag2 = "same biscuits";
        Vote singleVote = new Vote(Mood.HAPPY, tag2, now + 1000);
        returnedVotes.add(singleVote);

        when(mockRepo.getVotes(Mockito.any(Filter.class))).thenReturn(returnedVotes);

        TagCloud tags = tagCloudRetriever.retrieveTagCloudsFor(Mood.HAPPY);
        Integer weightRecentVote = tags.getContent().get(tag1);
        Integer weightOlderVote = tags.getContent().get(tag2);
        assertTrue(weightRecentVote > weightOlderVote);
    }

    @Test
    public void testMostRecentIsHigherThanLeastRecent() throws Exception {
        long now = Calendar.getInstance().getTimeInMillis();
        String tag1 = "biscuits";
        Vote recentVote = new Vote(Mood.HAPPY, tag1, now);
        String tag2 = "same biscuits";
        Vote olderVote = new Vote(Mood.HAPPY, tag2, now + 1000);
        when(mockRepo.getVotes(Mockito.any(Filter.class))).thenReturn(Arrays.asList(recentVote, olderVote));
        TagCloud tags = tagCloudRetriever.retrieveTagCloudsFor(Mood.HAPPY);
        Integer weightRecentVote = tags.getContent().get(tag1);
        Integer weightOlderVote = tags.getContent().get(tag2);
        assertTrue(weightRecentVote > weightOlderVote);
    }

    @Test
    public void testVotesWithNoTag() throws Exception {

    }

}
