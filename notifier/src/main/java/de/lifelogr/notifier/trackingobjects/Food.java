package de.lifelogr.notifier.trackingobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the lists of related tracking object names for the different food types.
 *
 * @author christin schlimbach
 */
public class Food {

    private List<String> superfoodList;
    private List<String> candyList;
    private List<String> fastfoodList;

    /**
     * Constructor
     *
     * Adds related tracking object names to the list of the specific food type.
     */
    public Food() {

        this.superfoodList = new ArrayList<>();
        this.candyList = new ArrayList<>();
        this.fastfoodList = new ArrayList<>();

        //list for type candy
        this.candyList.add("schokolade");
        this.candyList.add("pralinen");
        this.candyList.add("schokoriegel");
        this.candyList.add("fruchtgummi");
        this.candyList.add("weingummi");
        this.candyList.add("chips");
        this.candyList.add("kartoffelchips");
        this.candyList.add("bonbon");
        this.candyList.add("bonbons");
        this.candyList.add("eis");
        this.candyList.add("gummibärchen");
        this.candyList.add("keks");
        this.candyList.add("kekse");
        this.candyList.add("karamell");
        this.candyList.add("lebkuchen");
        this.candyList.add("lakritz");
        this.candyList.add("lutscher");
        this.candyList.add("nougat");

        //list for type fastfood
        this.fastfoodList.add("burger");
        this.fastfoodList.add("cheeseburger");
        this.fastfoodList.add("hamburger");
        this.fastfoodList.add("pizza");
        this.fastfoodList.add("pommes");
        this.fastfoodList.add("fritten");
        this.fastfoodList.add("pommesfrittes");
        this.fastfoodList.add("currywurst");
        this.fastfoodList.add("döner");
        this.fastfoodList.add("chickennuggets");
        this.fastfoodList.add("nuggets");
        this.fastfoodList.add("kroketten");

        //list for type superfood
        this.superfoodList.add("acai");
        this.superfoodList.add("goji");
        this.superfoodList.add("walnuss");
        this.superfoodList.add("walnüsse");
        this.superfoodList.add("himbeere");
        this.superfoodList.add("himbeeren");
        this.superfoodList.add("johannisbeere");
        this.superfoodList.add("johannisbeeren");
        this.superfoodList.add("matcha");
        this.superfoodList.add("matchatee");
        this.superfoodList.add("kokosnuss");
        this.superfoodList.add("ananas");
        this.superfoodList.add("granatapfel");
        this.superfoodList.add("kohl");
        this.superfoodList.add("mango");
        this.superfoodList.add("mandel");
        this.superfoodList.add("mandeln");
        this.superfoodList.add("chia");
        this.superfoodList.add("chiasamen");
        this.superfoodList.add("avocado");
        this.superfoodList.add("kakao");
        this.superfoodList.add("lachs");
        this.superfoodList.add("acerola");
        this.superfoodList.add("physalis");
        this.superfoodList.add("ingwer");
    }

    //getter
    public List<String> getSuperfoodList() {
        return superfoodList;
    }

    public List<String> getCandyList() {
        return candyList;
    }

    public List<String> getFastfoodList() { return fastfoodList; }
}
