package de.lifelogr.dbconnector.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.List;

@Entity("users")
@Indexes(
        @Index(value = "username", fields = @Field("username"))
)
public class User
{
    @Id
    private ObjectId id;

    private String telegramUserId;
    private String telegramChatId;

    private String username;

    @Reference
    private List<TrackingObject> trackingObjects;
}
