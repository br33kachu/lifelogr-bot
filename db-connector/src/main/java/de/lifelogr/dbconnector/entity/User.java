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

    private String username;

    public ObjectId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
