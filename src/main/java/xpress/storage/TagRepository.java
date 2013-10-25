package xpress.storage;

import java.util.List;

import xpress.storage.entity.TagByMood;

public interface TagRepository {

    List<TagByMood> getTags(Filter filter);

}
