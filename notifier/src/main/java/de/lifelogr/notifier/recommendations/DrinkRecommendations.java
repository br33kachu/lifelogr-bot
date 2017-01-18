package de.lifelogr.notifier.recommendations;

import de.lifelogr.communicator.services.Emoji;
import de.lifelogr.dbconnector.services.TrackingObjectType;

import java.util.HashMap;

/**
 * Contains the hashmap for the drink recommendations.
 *
 * @author christin schlimbach
 */
public class DrinkRecommendations {

    private HashMap<TrackingObjectType, String[]> recommendations = new HashMap<>();

    /**
     * Constructor
     *
     * Puts the recommendations for the different drink types into the hashmap.
     */
    public DrinkRecommendations() {

        //recommendations for type alcohol
        this.recommendations.put(TrackingObjectType.ALCOHOL, new String[]
                {"Bist du nicht langsam betrunken? " + Emoji.CLINKING_BEER_MUGS + "\nPass lieber ein bisschen auf, sonst wirst du es morgen bereuen!",
                 "Das gibt einen ganz sch\u00f6nen Kater morgen " + Emoji.CLINKING_BEER_MUGS,
                 "Oh oh, das gibt bestimmt morgen Kopfschmerzen " + Emoji.CLINKING_BEER_MUGS});



        //recommendations for type beauty
        this.recommendations.put(TrackingObjectType.BEAUTY, new String[]
                {"Wow, das ist ein echter Sch\u00f6nmacher! " + Emoji.FACE_THROWING_A_KISS,
                "Das wirkt wirklich Wunder f\u00fcr Haut und Haare! " + Emoji.FACE_THROWING_A_KISS,
                "Super! Das macht Haut und Haare gesund und sch\u00f6n " + Emoji.FACE_THROWING_A_KISS});

        //recommendations for type caffein
        this.recommendations.put(TrackingObjectType.CAFFEIN, new String[]
                {"Du hast heute schon sehr viel Koffein zu dir genommen! " + Emoji.CONFOUNDED_FACE + "\nTrink doch lieber mal ein Glas Wasser, das schont deinen Magen." ,
                 "Trink doch lieber mal etwas Neutrales. Zu viel Koffein ist nicht gut f\u00fcr deinen Magen " + Emoji.CONFOUNDED_FACE});
    }

    //getter
    public HashMap<TrackingObjectType, String[]> getRecommendations() {
        return recommendations;
    }
}
