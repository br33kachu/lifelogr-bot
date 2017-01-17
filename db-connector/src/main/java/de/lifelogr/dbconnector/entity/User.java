package de.lifelogr.dbconnector.entity;

import de.lifelogr.dbconnector.Informant;
import de.lifelogr.dbconnector.services.TrackingObjectType;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * User class. Represents a single User in the system and gets persisted.
 */
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
    private String username;
    private Date createdAt;
    private String firstName;
    private String lastName;
    private String nickName;
    private String birthDate;
    private HashMap<TrackingObjectType, Date> lastRecommendations;
    @Embedded
    private ArrayList<TrackingObject> trackingObjects;
    private String question;
    private Date dndUntil;

    /**
     * Constructor
     */
    public User()
    {
        this.trackingObjects = new ArrayList<>();
        this.lastRecommendations = new HashMap<>();
    }

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public Integer getTelegramId()
    {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId)
    {
        this.telegramId = telegramId;
    }

    public Long getChatId()
    {
        return chatId;
    }

    public void setChatId(Long chatId)
    {
        this.chatId = chatId;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Date getTokenExpirationDate()
    {
        return tokenExpirationDate;
    }

    public void setTokenExpirationDate(Date tokenExpirationDate)
    {
        this.tokenExpirationDate = tokenExpirationDate;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }

    public HashMap<TrackingObjectType, Date> getLastRecommendations() {
        return lastRecommendations;
    }

    public void addLastRecommendations(TrackingObjectType type, Date date) {
        this.lastRecommendations.put(type, date);
    }

    public ArrayList<TrackingObject> getTrackingObjects()
    {
        return trackingObjects;
    }

    /**
     * Get a single TrackingObject by name
     * @param name Name of the TrackingObject
     * @return TrackingObject if one with the given name exists, else null
     */
    public TrackingObject getTrackingObjectByName(String name)
    {
        for (TrackingObject trackingObject : this.trackingObjects) {
            if (trackingObject.getName().toLowerCase().equals(name.toLowerCase())) {
                return trackingObject;
            }
        }

        return null;
    }

    public void setTrackingObjects(ArrayList<TrackingObject> trackingObjects)
    {
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

    /**
     * Check if the User if fully registered
     * @return
     */
    public boolean istFullyRegistered()
    {
        return (!this.username.isEmpty() && !this.birthDate.isEmpty());
    }

    /**
     * Get the last updated TrackingObject
     * @return
     */
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

    /**
     * Inform observers after document has been persisted to database.
     */
    @PostPersist
    public void postPersist()
    {
        TrackingObject lastTrackingObject = this.getLastAddedTrackingObject();

        if (lastTrackingObject != null) {
            Informant.notifyObserver(this, this.getLastAddedTrackingObject());
        }
    }

    public Date getDndUntil()
    {
        return dndUntil;
    }

    public void setDndUntil(Date dndUntil)
    {
        this.dndUntil = dndUntil;
    }
}
