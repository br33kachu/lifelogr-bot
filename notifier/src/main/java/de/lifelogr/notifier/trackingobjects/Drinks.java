package de.lifelogr.notifier.trackingobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the lists of related tracking object names for the different drink types.
 *
 * @author christin schlimbach
 */
public class Drinks {

    private List<String> caffeinList;
    private List<String> alcoholList;
    private List<String> beautyList;

    /**
     * Constructor
     *
     * Adds related tracking object names to the list of the specific drink type.
     */
    public Drinks() {

        this.caffeinList = new ArrayList<>();
        this.alcoholList = new ArrayList<>();
        this.beautyList = new ArrayList<>();

        //list for type alcohol
        this.alcoholList.add("absinth");
        this.alcoholList.add("alcopops");
        this.alcoholList.add("altbier");
        this.alcoholList.add("armagnac");
        this.alcoholList.add("bananenweizen");
        this.alcoholList.add("bier");
        this.alcoholList.add("bowle");
        this.alcoholList.add("caipirinha");
        this.alcoholList.add("calvados");
        this.alcoholList.add("campari");
        this.alcoholList.add("champagner");
        this.alcoholList.add("cocktail");
        this.alcoholList.add("colabier");
        this.alcoholList.add("cognac");
        this.alcoholList.add("cubalibre");
        this.alcoholList.add("dekanter");
        this.alcoholList.add("eiswein");
        this.alcoholList.add("faxe");
        this.alcoholList.add("gaffel");
        this.alcoholList.add("gintonic");
        this.alcoholList.add("glühwein");
        this.alcoholList.add("korn");
        this.alcoholList.add("kölsch");
        this.alcoholList.add("likör");
        this.alcoholList.add("longdrink");
        this.alcoholList.add("lumumba");
        this.alcoholList.add("mixery");
        this.alcoholList.add("obstbrand");
        this.alcoholList.add("ouzo");
        this.alcoholList.add("portwein");
        this.alcoholList.add("prosecco");
        this.alcoholList.add("punsch");
        this.alcoholList.add("rosewein");
        this.alcoholList.add("rotwein");
        this.alcoholList.add("rum");
        this.alcoholList.add("sake");
        this.alcoholList.add("schankbier");
        this.alcoholList.add("schaumwein");
        this.alcoholList.add("schnaps");
        this.alcoholList.add("schwarzbier");
        this.alcoholList.add("sekt");
        this.alcoholList.add("sexonthebeach");
        this.alcoholList.add("starkbier");
        this.alcoholList.add("tequila");
        this.alcoholList.add("tonicwater");
        this.alcoholList.add("vodka");
        this.alcoholList.add("wein");
        this.alcoholList.add("weisswein");
        this.alcoholList.add("weißwein");
        this.alcoholList.add("weizen");
        this.alcoholList.add("weizenbier");
        this.alcoholList.add("whisky");

        //list for type beauty
        this.beautyList.add("buttermilch");
        this.beautyList.add("kokoswasser");
        this.beautyList.add("kokosnusswasser");
        this.beautyList.add("kokosnußwasser");
        this.beautyList.add("kefir");
        this.beautyList.add("molke");

        //list for type caffein
        this.caffeinList.add("cappuccino");
        this.caffeinList.add("espresso");
        this.caffeinList.add("kaffee");
        this.caffeinList.add("lattemacchiato");
        this.caffeinList.add("grünertee");
        this.caffeinList.add("schwarzertee");
        this.caffeinList.add("weißertee");
        this.caffeinList.add("weissertee");
        this.caffeinList.add("cola");
        this.caffeinList.add("cocacola");
        this.caffeinList.add("mate");
        this.caffeinList.add("clubmate");
        this.caffeinList.add("mate");
        this.caffeinList.add("pepsi");
        this.caffeinList.add("energydrink");
        this.caffeinList.add("redbull");
    }

    //getter
    public List<String> getCaffeinList() {
        return caffeinList;
    }

    public List<String> getAlcoholList() {
        return alcoholList;
    }

    public List<String> getBeautyList() {
        return beautyList;
    }
}
