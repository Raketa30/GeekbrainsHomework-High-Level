package ru.geekbrains.lesson6;

public class JUnitHomeTask {
    public static void main(String[] args) {

    }

    public static int[] extractAfterFour(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array empty!!!");
        }
        int index = -1;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 4) {
                index = i + 1;
            }
        }

        if (index < 0) {
            throw new RuntimeException("No four digit found");
        }
        int[] extracted = new int[numbers.length - index];

        System.arraycopy(numbers, index, extracted, 0, extracted.length);

        return extracted;
    }

    public static boolean findOneAndFourInArray(int[] inputArray) {
        if (inputArray == null || inputArray.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }

        boolean containsOne = false;
        boolean containsFour = false;

        for (int value : inputArray) {
            if (value == 1 || value == 4) {
                if (value == 1) {
                    containsOne = true;
                }
                if (value == 4) {
                    containsFour = true;
                }
            } else {
                throw new IllegalArgumentException("Only 1 and 4 in array possible!");
            }
        }
        return containsOne && containsFour;
    }
}
