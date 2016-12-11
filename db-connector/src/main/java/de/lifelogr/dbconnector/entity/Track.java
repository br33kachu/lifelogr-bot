package de.lifelogr.dbconnector.entity;

import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

@Entity
public class Track {
    private Integer count;
    private Date date;

    public Track() {
        this.date = new Date();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }
}
