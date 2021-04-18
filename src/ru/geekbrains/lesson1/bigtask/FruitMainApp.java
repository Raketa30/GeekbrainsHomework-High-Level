package ru.geekbrains.lesson1.bigtask;

import java.util.Random;

/*
a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
b. Класс Box в который можно складывать фрукты, коробки условно сортируются
   по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
c. Для хранения фруктов внутри коробки можете использовать ArrayList;
d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов
   и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку
   с той, которую подадут в compare в качестве параметра, true - если их веса равны, false
   в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
   (помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами),
   соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты,
   которые были в этой коробке;
g. Не забываем про метод добавления фрукта в коробку.
 */

public class FruitMainApp {
    public static void main(String[] args) {
        Box<Orange> orangeBox1 = fillBox(new Orange());
        Box<Orange> orangeBox2 = fillBox(new Orange());
        Box<Apple> appleBox1 = fillBox(new Apple());
        Box<Apple> appleBox2 = fillBox(new Apple());

        System.out.println(orangeBox1.getWeight());
        System.out.println(orangeBox2.getWeight());
        System.out.println(appleBox1.getWeight());
        System.out.println(appleBox2.getWeight());

        System.out.println(orangeBox1.compareTo(appleBox1));
        System.out.println(orangeBox2.compareTo(appleBox1));
        System.out.println(orangeBox2.compareTo(appleBox2));
        System.out.println(orangeBox1.compareTo(orangeBox2));

        orangeBox1.dischargeToAnotherBox(orangeBox2);
        appleBox2.dischargeToAnotherBox(appleBox1);

        System.out.println(orangeBox1.getWeight());
        System.out.println(orangeBox2.getWeight());
        System.out.println(appleBox1.getWeight());
        System.out.println(appleBox2.getWeight());

    }

    private static <T extends Fruit> Box<T> fillBox(T t) {
        Random random = new Random();
        int size = random.nextInt(5);
        Box<T> box = new Box<>(size);

        for (int i = 0; i < size; i++) {
            box.putInto(t);
        }
        return box;
    }
}
