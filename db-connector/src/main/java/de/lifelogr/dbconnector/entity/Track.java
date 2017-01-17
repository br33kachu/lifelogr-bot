package de.lifelogr.dbconnector.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.util.Date;

/**
 * Represents a single Track within a TrackingObject.
 */
@Embedded
public class Track {
    private Double count;
    private Date date;

    public Track() {
        this.date = new Date();
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }
}
