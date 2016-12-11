package de.lifelogr.dbconnector.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.util.Date;

@Embedded
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

    public void setDate(Date date) {
        this.date = date;
    }
}
