package xpress.storage;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.SessionImpl;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xpress.storage.entity.VoteEntity;

import java.util.List;

/**
 * @author sechelc
 */
@Component
@Transactional
public class DBRepository implements Repository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveVote(VoteEntity voteEntity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(voteEntity);
        currentSession.flush();
    }

    @Override
    public List<VoteEntity> getVotes(Filter queryVote) {
        Session currentSession = sessionFactory.getCurrentSession();
        Criteria criteria = currentSession.createCriteria(VoteEntity.class);
        if(isEmpty(queryVote)){

        if (queryVote.getTag() != null) {
            criteria.add(Expression.eq("tag", queryVote.getTag()));
        }
        if (queryVote.getMood() != null) {
            criteria.add(Expression.eq("mood", queryVote.getMood()));
        }
        if (queryVote.getTime() != null) {
            Interval interval = Utils.getIntervalFormTimeEnum(queryVote.getTime());
            criteria.add(Restrictions.between("time", interval.getStartMillis(), interval.getEndMillis()));
        }

        return criteria.list();
        }else{
            return ((SessionImpl)currentSession).find("from VoteEntity");
        }
    }

    private boolean isEmpty(Filter queryVote) {
        return queryVote == null? true : !hasTagSet(queryVote) && !hasMoodSet(queryVote) && !hasTimeSet(queryVote);
    }

    private boolean hasTagSet(Filter queryVote) {
        return (queryVote.getTag() != null);
    }

    private boolean hasMoodSet(Filter queryVote) {
        return (queryVote.getMood() != null);
    }

    private boolean hasTimeSet(Filter queryVote) {
        return (queryVote.getTime() != null);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
