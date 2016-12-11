package de.lifelogr.dbconnector.entity;

import de.lifelogr.dbconnector.Informant;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.Date;

@Entity("users")
@Indexes(
        {
                @Index(value = "username", fields = @Field("username")),
                @Index(value = "token", fields = @Field("token")),
                @Index(value = "telegramId", fields = @Field("telegramId"))
        }
)
public class User
{
    @Id
    private ObjectId id;
    private Integer telegramId;
    private Long chatId;
    private String token;
    private Date tokenExpirationDate;
    private Date doNotDisturbUntil;
    private String username;
    private Date createdAt;
    private String firstName;
    private String lastName;
    private String nickName;
    private String birthDate;
    @Embedded
    private ArrayList<TrackingObject> trackingObjects;
    @Transient
    private ArrayList<TrackingObject> trackingObjectsTemp = null;
    private String question;

    public User()
    {
        this.trackingObjects = new ArrayList<>();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
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

    public ArrayList<TrackingObject> getTrackingObjects() {
        return trackingObjects;
    }

    public TrackingObject getTrackingObjectByName(String name)
    {
        for (TrackingObject trackingObject : this.trackingObjects) {
            if (trackingObject.getName().toLowerCase().equals(name.toLowerCase())) {
                return trackingObject;
            }
        }

        return null;
    }

    public void setTrackingObjects(ArrayList<TrackingObject> trackingObjects) {
        this.trackingObjects = trackingObjects;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public boolean istFullyRegistered()
    {
        return (!this.username.isEmpty() && !this.birthDate.isEmpty());
    }

    public TrackingObject getLastAddedTrackingObject()
    {
        TrackingObject result = null;

        for (TrackingObject current : this.trackingObjects) {
            if (result == null) {
                result = current;
            } else {
                if (current.getTracks().get(current.getTracks().size() - 1).getDate().getTime() > result.getTracks().get(result.getTracks().size() - 1).getDate().getTime()) {
                    result = current;
                }
            }
        }

        return result;
    }

    @PostLoad
    public void cacheTrackingObjects()
    {
        this.trackingObjectsTemp = new ArrayList<>(this.trackingObjects);
    }

    @PostPersist
    public void postPersist()
    {
        Informant.notifyObserver(this, this.getLastAddedTrackingObject());
    }
}
