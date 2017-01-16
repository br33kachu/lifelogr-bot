package de.lifelogr.notifier.trackingobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christin on 06.12.16.
 */
public class Drinks {

    private List<String> drinksCaffein;
    private List<String> drinksAlcohol;
    private List<String> drinksBeauty;

    public Drinks() {
        this.drinksCaffein = new ArrayList<>();
        this.drinksAlcohol = new ArrayList<>();
        this.drinksBeauty = new ArrayList<>();

        //caffein
        this.drinksCaffein.add("cappuccino");
        this.drinksCaffein.add("espresso");
        this.drinksCaffein.add("kaffee");
        this.drinksCaffein.add("lattemacchiato");
        this.drinksCaffein.add("grünertee");
        this.drinksCaffein.add("schwarzertee");
        this.drinksCaffein.add("weißertee");
        this.drinksCaffein.add("weissertee");
        this.drinksCaffein.add("cola");
        this.drinksCaffein.add("cocacola");
        this.drinksCaffein.add("mate");
        this.drinksCaffein.add("clubmate");
        this.drinksCaffein.add("mate");
        this.drinksCaffein.add("pepsi");
        this.drinksCaffein.add("energydrink");
        this.drinksCaffein.add("redbull");

        //alcohol
        this.drinksAlcohol.add("absinth");
        this.drinksAlcohol.add("alcopops");
        this.drinksAlcohol.add("altbier");
        this.drinksAlcohol.add("armagnac");
        this.drinksAlcohol.add("bananenweizen");
        this.drinksAlcohol.add("bier");
        this.drinksAlcohol.add("bowle");
        this.drinksAlcohol.add("caipirinha");
        this.drinksAlcohol.add("calvados");
        this.drinksAlcohol.add("campari");
        this.drinksAlcohol.add("champagner");
        this.drinksAlcohol.add("cocktail");
        this.drinksAlcohol.add("colabier");
        this.drinksAlcohol.add("cognac");
        this.drinksAlcohol.add("cubalibre");
        this.drinksAlcohol.add("dekanter");
        this.drinksAlcohol.add("eiswein");
        this.drinksAlcohol.add("faxe");
        this.drinksAlcohol.add("gaffel");
        this.drinksAlcohol.add("gintonic");
        this.drinksAlcohol.add("glühwein");
        this.drinksAlcohol.add("korn");
        this.drinksAlcohol.add("kölsch");
        this.drinksAlcohol.add("likör");
        this.drinksAlcohol.add("longdrink");
        this.drinksAlcohol.add("lumumba");
        this.drinksAlcohol.add("mixery");
        this.drinksAlcohol.add("obstbrand");
        this.drinksAlcohol.add("ouzo");
        this.drinksAlcohol.add("portwein");
        this.drinksAlcohol.add("prosecco");
        this.drinksAlcohol.add("punsch");
        this.drinksAlcohol.add("rosewein");
        this.drinksAlcohol.add("rotwein");
        this.drinksAlcohol.add("rum");
        this.drinksAlcohol.add("sake");
        this.drinksAlcohol.add("schankbier");
        this.drinksAlcohol.add("schaumwein");
        this.drinksAlcohol.add("schnaps");
        this.drinksAlcohol.add("schwarzbier");
        this.drinksAlcohol.add("sekt");
        this.drinksAlcohol.add("sexonthebeach");
        this.drinksAlcohol.add("starkbier");
        this.drinksAlcohol.add("tequila");
        this.drinksAlcohol.add("tonicwater");
        this.drinksAlcohol.add("vodka");
        this.drinksAlcohol.add("wein");
        this.drinksAlcohol.add("weisswein");
        this.drinksAlcohol.add("weißwein");
        this.drinksAlcohol.add("weizen");
        this.drinksAlcohol.add("weizenbier");
        this.drinksAlcohol.add("whisky");

        //beauty
        this.drinksBeauty.add("buttermilch");
        this.drinksBeauty.add("kokoswasser");
        this.drinksBeauty.add("kokosnusswasser");
        this.drinksBeauty.add("kokosnußwasser");
        this.drinksBeauty.add("kefir");
        this.drinksBeauty.add("molke");
    }

    public List<String> getDrinksCaffein() {
        return drinksCaffein;
    }

    public List<String> getDrinksAlcohol() {
        return drinksAlcohol;
    }

    public List<String> getDrinksBeauty() {
        return drinksBeauty;
    }
}
