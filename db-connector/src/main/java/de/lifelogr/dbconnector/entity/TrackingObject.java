package de.lifelogr.dbconnector.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single TrackingObject within the User.
 */
@Embedded
public class TrackingObject {
    private String name;
    private String unit;
    private Double currentCount;
    private Integer category;
    private List<Track> tracks;
    private boolean isCountable;

    public TrackingObject() {
        this.tracks = new ArrayList<>()
;    }

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

    public Double getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Double currentCount) {
        this.currentCount = currentCount;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public boolean isCountable()
    {
        return isCountable;
    }

    public void setCountable(boolean countable)
    {
        isCountable = countable;
    }
}