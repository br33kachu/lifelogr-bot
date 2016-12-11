package de.lifelogr.trackingobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christin on 06.12.16.
 */
public class Drinks {

    private List<String> drinksCaffein = new ArrayList<>();
    private List<String> drinksAlcohol = new ArrayList<>();

    public Drinks() {
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
    }

    public List<String> getDrinksCaffein() {
        return drinksCaffein;
    }

    public List<String> getDrinksAlcohol() {
        return drinksAlcohol;
    }
}
