package xpress.graphtags;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import xpress.TimeEnum;
import xpress.storage.Filter;
import xpress.storage.TagRepository;
import xpress.storage.entity.TagByMood;

@Component
public class TagRetriever {

    @Autowired
    private TagRepository repository;

    public List<TagByMood> retrieveTagsForPeriod(TimeEnum inPeriod) {
        TimeEnum period = inPeriod;
        if (inPeriod == null) {
            period = TimeEnum.LAST_MONTH;
        }
        Filter filter = new Filter.Builder().timeEnum(period).build();
        return repository.getTags(filter);
    }

    public TagByMood retrieveSpecificTagForPeriod(String tag, TimeEnum inPeriod) {
        TimeEnum period = inPeriod;
        if (inPeriod == null) {
            period = TimeEnum.LAST_MONTH;
        }
        Filter filter = new Filter.Builder().tag(tag).timeEnum(period).build();
        List<TagByMood> queryResult = repository.getTags(filter);
        if (!CollectionUtils.isEmpty(queryResult)) {
            return queryResult.get(0);
        }
        return null;
    }

    public List<TagByMood> retrieveSimilarTagsForPeriod(String tag, TimeEnum inPeriod) {
        TimeEnum period = inPeriod;
        if (inPeriod == null) {
            period = TimeEnum.LAST_MONTH;
        }
        Filter filter = new Filter.Builder().tag(tag).timeEnum(period).withSimilarTags(true).build();
        return repository.getTags(filter);
    }

    public void setRepository(TagRepository repository) {
        this.repository = repository;
    }

}
