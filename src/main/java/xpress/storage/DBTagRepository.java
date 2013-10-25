package xpress.storage;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.testng.collections.Lists;

import xpress.Mood;
import xpress.storage.entity.TagByMood;

import com.google.common.collect.Maps;

@Component
@Transactional
public class DBTagRepository implements TagRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<TagByMood> getTags(Filter filter) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("SELECT tag, count(*), mood FROM VoteEntity GROUP BY tag, mood");
        List<Object[]> tuples = query.list();

        Map<String, TagByMood> result = Maps.newConcurrentMap();
        for (Object[] tuple : tuples) {

            String tagName = (String) tuple[0];
            Mood mood = (Mood) tuple[2];
            int frequency = (int) tuple[1];

            TagByMood tagByMood = getOrCreateTagByMood(result, tagName);
            Map<Mood, Integer> frequencyMap = getOrCreateFrquencyMap(tagByMood, mood);
            frequencyMap.put(mood, frequency);
        }

        for (TagByMood tagByMood : result.values()) {
            int totalFrequency = 0;
            for (Mood mood : Mood.values()) {
                Integer moodFrequency = tagByMood.getFrequency().get(mood);
                totalFrequency += moodFrequency != null ? moodFrequency : 0;
            }
            tagByMood.setTotalFrequency(totalFrequency);
        }

        return Lists.newArrayList(result.values());
    }

    private TagByMood getOrCreateTagByMood(Map<String, TagByMood> tagByMoodMap, String tagName) {
        TagByMood tagByMood;
        if (tagByMoodMap.get(tagName) == null) {
            tagByMood = new TagByMood();
            tagByMood.setTag(tagName);
            tagByMoodMap.put(tagName, tagByMood);
        } else {
            tagByMood = tagByMoodMap.get(tagName);
        }
        return tagByMood;
    }

    private Map<Mood, Integer> getOrCreateFrquencyMap(TagByMood tagByMood, Mood mood) {
        Map<Mood, Integer> frequencyMap;
        if (tagByMood.getFrequency() == null) {
            frequencyMap = Maps.newConcurrentMap();
            tagByMood.setFrequency(frequencyMap);
        } else {
            frequencyMap = tagByMood.getFrequency();
        }
        return frequencyMap;
    }

}
