package ru.geekbrains.lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String[] numbers = {"one", "two", "three", "four", "five"};

        System.out.println(Arrays.toString(numbers));
        System.out.println();

        swapElementsByIndex(numbers, 1, 3);
        System.out.println(Arrays.toString(numbers));
        System.out.println(transformToArrayList(numbers));
    }

    //1. Написать метод, который меняет два элемента массива местами.
    // (массив может быть любого ссылочного типа);

    public static <T> void swapElementsByIndex(T[] array, int i, int j) {
        //считаю что проверки на то что индексы входят в дипазон массива излишние
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    //2. Написать метод, который преобразует массив в ArrayList;

    public static <T> ArrayList<T> transformToArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }
}
