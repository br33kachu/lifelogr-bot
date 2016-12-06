package de.lifelogr.dbconnector.entity;

import com.mongodb.DBObject;
import de.lifelogr.dbconnector.DBConnector;
import de.lifelogr.dbconnector.Informant;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;
import java.util.List;

@Entity("users")
@Indexes(
        @Index(value = "username", fields = @Field("username"))
)
public class User
{
    @Id
    private ObjectId id;
    private int telegramId;
    private int chatId;
    private String token;
    private Date tokenExpirationDate;
    private Date doNotDisturbUntil;
    private String username;
    private Date createdAt;
    private String firstName;
    private String lastName;
    private String nickName;
    private String birthDate;
    private List<TrackingObject> trackingObjects;

    public User() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(int telegramId) {
        this.telegramId = telegramId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpirationDate() {
        return tokenExpirationDate;
    }

    public void setTokenExpirationDate(Date tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }

    public Date getDoNotDisturbUntil() {
        return doNotDisturbUntil;
    }

    public void setDoNotDisturbUntil(Date doNotDisturbUntil) {
        this.doNotDisturbUntil = doNotDisturbUntil;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<TrackingObject> getTrackingObjects() {
        return trackingObjects;
    }

    public void setTrackingObjects(List<TrackingObject> trackingObjects) {
        this.trackingObjects = trackingObjects;
    }


    @PostPersist
        public void postPersist() {
        Informant.notifyObserver(this);
    }

}