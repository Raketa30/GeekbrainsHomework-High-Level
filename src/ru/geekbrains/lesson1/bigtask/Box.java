package ru.geekbrains.lesson1.bigtask;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> implements Comparable<Box<? extends Fruit>> {
    private final List<T> fruits;

    public Box(int size) {
        fruits = new ArrayList<>(size);
    }

    public float getWeight() {
        if (fruits.isEmpty()) {
            return 0.0f;
        }
        return fruits.get(0).getWeight() * fruits.size();
    }

    public List<T> getFruits() {
        return fruits;
    }

    public void putInto(T fruit) {
        fruits.add(fruit);
    }

    public boolean compare(Box<? extends Fruit> box) {
        return compareTo(box) == 0;
    }

    public void dischargeToAnotherBox(Box<T> box) {
        box.getFruits().addAll(fruits);
        fruits.clear();
    }

    @Override
    public int compareTo(Box o) {
        return Float.compare(this.getWeight(), o.getWeight());
    }

    @Override
    public String toString() {
        return "Box{" +
                "fruits=" + fruits +
                '}';
    }

}
