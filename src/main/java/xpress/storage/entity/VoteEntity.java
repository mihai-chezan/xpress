package xpress.storage.entity;

import xpress.Mood;

import javax.persistence.*;
import static javax.persistence.GenerationType.AUTO;
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

    @Column(length = 200, nullable = true, unique = true)
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
}
