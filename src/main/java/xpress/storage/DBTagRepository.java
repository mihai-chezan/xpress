package xpress.storage;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DBTagRepository implements TagRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTags(Filter filter) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("Select distinct(tag) from VoteEntity vote");

        return query.list();
    }

}
