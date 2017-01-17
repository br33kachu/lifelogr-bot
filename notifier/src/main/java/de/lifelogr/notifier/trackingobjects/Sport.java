package de.lifelogr.notifier.trackingobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the lists of related tracking object names for the different sport types.
 *
 * @author christin schlimbach
 */
public class Sport {

    private List<String> powerList;
    private List<String> staminaList;
    private List<String> relaxList;

    /**
     * Constructor
     *
     * Adds related tracking object names to the list of the specific sport type.
     */
    public Sport() {
        
        this.powerList = new ArrayList<>();
        this.staminaList = new ArrayList<>();
        this.relaxList = new ArrayList<>();

        //list for type power
        this.powerList.add("gewichttraining");
        this.powerList.add("krafttraining");
        this.powerList.add("hanteltraining");
        this.powerList.add("bodybuilding");
        this.powerList.add("gerätetraining");
        this.powerList.add("muskeltraining");
        this.powerList.add("muskelaufbau");
        this.powerList.add("gewichtheben");
        this.powerList.add("bankdrücken");
        this.powerList.add("kreuzheben");
        this.powerList.add("bodypump");
        this.powerList.add("bbp");
        this.powerList.add("bauchbeinepo");
        this.powerList.add("rudern");

        //list for type relax
        this.relaxList.add("meditieren");
        this.relaxList.add("meditation");
        this.relaxList.add("yoga");
        this.relaxList.add("qigong");
        this.relaxList.add("pilates");
        this.relaxList.add("fünftibeter");
        this.relaxList.add("taichi");

        //list for type stamina
        this.staminaList.add("joggen");
        this.staminaList.add("laufen");
        this.staminaList.add("radeln");
        this.staminaList.add("marathon");
        this.staminaList.add("radfahren");
        this.staminaList.add("parcours");
        this.staminaList.add("parcour");
        this.staminaList.add("parkour");
        this.staminaList.add("parkours");
        this.staminaList.add("laufband");
        this.staminaList.add("stepper");
        this.staminaList.add("schwimmen");
        this.staminaList.add("biken");
    }

    //getter
    public List<String> getPowerList() { return powerList; }

    public List<String> getStaminaList() {
        return staminaList;
    }

    public List<String> getRelaxList() {
        return relaxList;
    }
}
