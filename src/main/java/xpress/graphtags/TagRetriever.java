package xpress.graphtags;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xpress.TimeEnum;
import xpress.storage.Filter;
import xpress.storage.TagRepository;

@Component
public class TagRetriever {

    @Autowired
    private TagRepository repository;

    public List<String> retrieve(TimeEnum period) {
        Filter filter = new Filter();
        filter.setTime(period);
        List<String> tags = repository.getTags(filter);
        return tags;
    }

    public void setRepository(TagRepository repository) {
        this.repository = repository;
    }

}
