package de.lifelogr.dbconnector.entity;

import java.util.Date;

public class Track {
    private int count;
    private Date date;

    public Track() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
