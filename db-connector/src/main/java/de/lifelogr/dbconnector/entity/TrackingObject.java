package de.lifelogr.dbconnector.entity;

import org.mongodb.morphia.annotations.Reference;

import java.util.Date;
import java.util.List;

public class TrackingObject
{
    private String name;
    private String unit;
    private Date createdAt;

    @Reference
    private List<Track> tracks;
}
