package ru.geekbrains.lesson1.bigtask;

public abstract class Fruit {
    private final float weight;
    private final String type;

    public Fruit(float weight, String type) {
        this.weight = weight;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return getType();
    }
}
