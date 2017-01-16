package de.lifelogr.notifier.trackingobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christin on 16.01.17.
 */
public class Food {

    private List<String> foodSuperfood;
    private List<String> foodCandy;
    private List<String> foodFastfood;

    public Food() {
        this.foodSuperfood = new ArrayList<>();
        this.foodCandy = new ArrayList<>();
        this.foodFastfood = new ArrayList<>();

        //superfood
        this.foodSuperfood.add("acai");
        this.foodSuperfood.add("goji");
        this.foodSuperfood.add("walnuss");
        this.foodSuperfood.add("walnüsse");
        this.foodSuperfood.add("himbeere");
        this.foodSuperfood.add("himbeeren");
        this.foodSuperfood.add("johannisbeere");
        this.foodSuperfood.add("johannisbeeren");
        this.foodSuperfood.add("matcha");
        this.foodSuperfood.add("matchatee");
        this.foodSuperfood.add("kokosnuss");
        this.foodSuperfood.add("ananas");
        this.foodSuperfood.add("granatapfel");
        this.foodSuperfood.add("kohl");
        this.foodSuperfood.add("mango");
        this.foodSuperfood.add("mandel");
        this.foodSuperfood.add("mandeln");
        this.foodSuperfood.add("chia");
        this.foodSuperfood.add("chiasamen");
        this.foodSuperfood.add("avocado");
        this.foodSuperfood.add("kakao");
        this.foodSuperfood.add("lachs");
        this.foodSuperfood.add("acerola");
        this.foodSuperfood.add("physalis");
        this.foodSuperfood.add("ingwer");

        //candy
        this.foodCandy.add("schokolade");
        this.foodCandy.add("pralinen");
        this.foodCandy.add("schokoriegel");
        this.foodCandy.add("fruchtgummi");
        this.foodCandy.add("weingummi");
        this.foodCandy.add("chips");
        this.foodCandy.add("kartoffelchips");
        this.foodCandy.add("bonbon");
        this.foodCandy.add("bonbons");
        this.foodCandy.add("eis");
        this.foodCandy.add("gummibärchen");
        this.foodCandy.add("keks");
        this.foodCandy.add("kekse");
        this.foodCandy.add("karamell");
        this.foodCandy.add("lebkuchen");
        this.foodCandy.add("lakritz");
        this.foodCandy.add("lutscher");
        this.foodCandy.add("nougat");

        //fastfood
        this.foodFastfood.add("burger");
        this.foodFastfood.add("cheeseburger");
        this.foodFastfood.add("hamburger");
        this.foodFastfood.add("pizza");
        this.foodFastfood.add("pommes");
        this.foodFastfood.add("fritten");
        this.foodFastfood.add("pommesfrittes");
        this.foodFastfood.add("currywurst");
        this.foodFastfood.add("döner");
        this.foodFastfood.add("chickennuggets");
        this.foodFastfood.add("nuggets");
        this.foodFastfood.add("kroketten");
    }

    public List<String> getFoodSuperfood() {
        return foodSuperfood;
    }

    public List<String> getFoodCandy() {
        return foodCandy;
    }

    public List<String> getFoodFastfood() {
        return foodFastfood;
    }
}
