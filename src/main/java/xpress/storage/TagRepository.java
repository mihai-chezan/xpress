package xpress.storage;

import java.util.Map;

public interface TagRepository {

    Map<String, Integer> getTags(Filter filter);

}
