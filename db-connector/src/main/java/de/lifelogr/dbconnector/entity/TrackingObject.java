package de.lifelogr.dbconnector.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.List;

public class TrackingObject {
    private String name;
    private String unit;
    private double currentCount;
    private int category;
    private List<Track> tracks;

    public TrackingObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(double currentCount) {
        this.currentCount = currentCount;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}