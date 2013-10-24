package xpress.graphtags;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xpress.TimeEnum;
import xpress.storage.Filter;
import xpress.storage.TagRepository;

@Component
public class TagRetriever {

    @Autowired
    private TagRepository repository;

    public Map<String, Integer> retrieve(TimeEnum period) {
        Filter filter = new Filter();
        filter.setTime(period);
        return repository.getTags(filter);
    }

    public void setRepository(TagRepository repository) {
        this.repository = repository;
    }

}
