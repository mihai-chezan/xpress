package xpress.graphtags;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xpress.TimeEnum;
import xpress.storage.Filter;
import xpress.storage.TagRepository;
import xpress.storage.entity.TagByMood;

@Component
public class TagRetriever {

    @Autowired
    private TagRepository repository;

    public List<TagByMood> retrieve(TimeEnum period) {
        Filter filter = new Filter();
        filter.setTime(period);
        return repository.getTags(filter);
    }

    public void setRepository(TagRepository repository) {
        this.repository = repository;
    }

}
