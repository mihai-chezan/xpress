package xpress;

import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.Maps;

public class GraphTagsResourceTest {

    private static final Random RANDOM_GENERATOR = new Random();

    @Test(enabled = false)
    public void testGetTags() {
        Assert.fail("Not yet implemented");
    }

    @Test(enabled = false)
    public void testGetTagVotesDuringPeriod() {
        Assert.fail("Not yet implemented");
    }

    @Test(enabled = false)
    public void testGetTagVotesDuringPeriodForChart() {
        Assert.fail("Not yet implemented");
    }

    private Map<String, Integer> getDummyTags() {
        Map<String, Integer> result = Maps.newConcurrentMap();
        for (Integer i = 0; i < 10; i++) {
            result.put(RandomStringUtils.randomAlphabetic(8), RANDOM_GENERATOR.nextInt(100));
        }
        return result;
    }

    private Map<String, Integer> getDummyPeriodTags() {
        Map<String, Integer> result = Maps.newConcurrentMap();
        for (Mood mood : Mood.values()) {
            result.put(mood.name(), RANDOM_GENERATOR.nextInt(100));
        }
        return result;
    }

}
