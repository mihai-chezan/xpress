package xpress.storage;

import java.util.List;

public interface TagRepository {

    List<String> getTags(Filter filter);

}
