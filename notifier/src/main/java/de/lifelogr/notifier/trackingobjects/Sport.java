package de.lifelogr.notifier.trackingobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christin on 16.01.17.
 */
public class Sport {

    private List<String> sportPower;
    private List<String> sportStamina;
    private List<String> sportRelax;

    public Sport() {
        this.sportPower = new ArrayList<>();
        this.sportStamina = new ArrayList<>();
        this.sportRelax = new ArrayList<>();

        //power
        this.sportPower.add("gewichttraining");
        this.sportPower.add("krafttraining");
        this.sportPower.add("hanteltraining");
        this.sportPower.add("bodybuilding");
        this.sportPower.add("gerätetraining");
        this.sportPower.add("muskeltraining");
        this.sportPower.add("muskelaufbau");
        this.sportPower.add("gewichtheben");
        this.sportPower.add("bankdrücken");
        this.sportPower.add("kreuzheben");
        this.sportPower.add("bodypump");
        this.sportPower.add("bbp");
        this.sportPower.add("bauchbeinepo");
        this.sportPower.add("rudern");

        //stamina
        this.sportStamina.add("joggen");
        this.sportStamina.add("laufen");
        this.sportStamina.add("radeln");
        this.sportStamina.add("marathon");
        this.sportStamina.add("radfahren");
        this.sportStamina.add("parcours");
        this.sportStamina.add("parcour");
        this.sportStamina.add("parkour");
        this.sportStamina.add("parkours");
        this.sportStamina.add("laufband");
        this.sportStamina.add("stepper");
        this.sportStamina.add("schwimmen");
        this.sportStamina.add("biken");

        //relax
        this.sportRelax.add("meditieren");
        this.sportRelax.add("meditation");
        this.sportRelax.add("yoga");
        this.sportRelax.add("qigong");
        this.sportRelax.add("pilates");
        this.sportRelax.add("fünftibeter");
        this.sportRelax.add("taichi");
    }

    public List<String> getSportPower() {
        return sportPower;
    }

    public List<String> getSportStamina() {
        return sportStamina;
    }

    public List<String> getSportRelax() {
        return sportRelax;
    }
}
