package xpress.storage;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import xpress.Mood;
import xpress.storage.entity.TagByMood;

import com.google.common.collect.Lists;
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
        StringBuilder queryString = new StringBuilder("SELECT tag, count(*), mood FROM VoteEntity ");

        boolean firstCondition = true;

        if (!filter.isEmpty()) {
            queryString.append(" WHERE ");
        }

        if (!StringUtils.isEmpty(filter.getTag())) {
            if (filter.isSimilarTags()) {
                queryString.append(!firstCondition ? " AND " : "").append("LOWER(tag) like LOWER('%" + filter.getTag() + "%')");
            } else {
                queryString.append(!firstCondition ? " AND " : "").append("LOWER(tag) = LOWER('" + filter.getTag() + "')");
            }
            firstCondition = false;
        }

        if (filter.getMood() != null) {
            queryString.append(!firstCondition ? " AND " : "").append("mood is " + filter.getMood());
            firstCondition = false;
        }

        if (filter.getTime() != null) {
            long timestamp = Utils.getTimestampForTimeEnum(filter.getTime());
            queryString.append(!firstCondition ? " AND " : "").append("time > " + timestamp);
            firstCondition = false;
        }

        queryString.append(" GROUP BY tag, mood");

        List<Object[]> tuples = session.createQuery(queryString.toString()).list();

        Map<String, TagByMood> result = convertTuplesToTagByMood(tuples);

        return Lists.newArrayList(result.values());
    }

    private Map<String, TagByMood> convertTuplesToTagByMood(List<Object[]> tuples) {
        Map<String, TagByMood> result = Maps.newConcurrentMap();
        for (Object[] tuple : tuples) {

            String tagName = (String) tuple[0];
            Mood mood = (Mood) tuple[2];
            int frequency = (int) ((long) tuple[1]);

            TagByMood tagByMood = getOrCreateTagByMood(result, tagName);
            updateFrequencyForMood(tagByMood, mood, frequency);
        }

        setTotalFrequency(result);
        return result;
    }

    private void setTotalFrequency(Map<String, TagByMood> result) {
        for (TagByMood tagByMood : result.values()) {
            int totalFrequency = 0;
            for (Mood mood : Mood.values()) {
                Integer moodFrequency = tagByMood.getFrequency().get(mood);
                totalFrequency += moodFrequency != null ? moodFrequency : 0;
            }
            tagByMood.setTotalFrequency(totalFrequency);
        }
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

    private void updateFrequencyForMood(TagByMood tagByMood, Mood mood, int frequency) {
        tagByMood.getFrequency().put(mood, frequency);
    }

}
