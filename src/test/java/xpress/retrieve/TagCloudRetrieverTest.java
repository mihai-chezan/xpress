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
            returnedVotes.add(new Vote(Mood.HAPPY, tag1, now));
        }
        String tag2 = "other biscuits";
        Vote singleVote = new Vote(Mood.HAPPY, tag2, now);
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
        Vote olderVote = new Vote(Mood.HAPPY, tagForOlderVote, Calendar.getInstance().getTimeInMillis() - TimeUnit.MINUTES.toMillis(44));

        String tagForRecentVote = "muffins";
        Vote recentVote = new Vote(Mood.HAPPY, tagForRecentVote, Calendar.getInstance().getTimeInMillis());

        when(mockRepo.getVotes(Mockito.any(Filter.class))).thenReturn(Arrays.asList(recentVote, olderVote));
        TagCloud tags = tagCloudRetriever.retrieveTagCloudsFor(Mood.HAPPY);
        Integer weightOlderVote = tags.getContent().get(tagForOlderVote);
        Integer weightRecentVote = tags.getContent().get(tagForRecentVote);
        assertTrue(weightRecentVote > weightOlderVote);
    }


}
