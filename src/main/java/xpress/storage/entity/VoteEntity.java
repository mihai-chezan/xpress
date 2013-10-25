package xpress.storage.entity;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import xpress.Mood;
import xpress.storage.Utils;
/**
 * @author sechelc
 */

@Entity
@Table(name = "VoteEntity")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Column(length = 200, nullable = true, unique = false)
    private String tag;

    @Column
    private long time;

    public VoteEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "VoteEntity [id=" + id + ", mood=" + mood + ", tag=" + tag + ", time=(" + Utils.prettyPrintDate(time) + ")]";
    }

}
