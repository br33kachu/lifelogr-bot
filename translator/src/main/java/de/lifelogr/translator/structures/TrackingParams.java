package de.lifelogr.translator.structures;

/**
 * @author Marco Kretz
 */
public class TrackingParams {
    private String name;
    private double value;

    public TrackingParams(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String toString()
    {
        return "/track " + this.name + " " + this.value;
    }
}
