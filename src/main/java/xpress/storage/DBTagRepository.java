package xpress.storage;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

@Component
@Transactional
public class DBTagRepository implements TagRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Integer> getTags(Filter filter) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("SELECT distinct(tag), count(*) FROM VoteEntity GROUP BY tag");
        List<Object[]> tuples = query.list();

        Map<String, Integer> result = Maps.newConcurrentMap();
        for (Object[] tuple : tuples) {
            result.put((String) tuple[0], (int) tuple[1]);
        }

        return result;
    }

}
