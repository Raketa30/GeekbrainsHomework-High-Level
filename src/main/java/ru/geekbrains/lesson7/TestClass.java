package ru.geekbrains.lesson7;

public class TestClass {
    @BeforeSuit
    public void doBeforeSuit() {
        System.out.println("BeforeSuit");
    }

    @Test(priority = 1)
    public void doTestOne() {
        System.out.println("Test One");
    }

    @Test(priority = 2)
    public void doTestTwo() {
        System.out.println("Test Two");
    }

    @Test(priority = 3)
    public void doTestThree() {
        System.out.println("Test Three");
    }

    @Test(priority = 1)
    public void doTestOneRepeat() {
        System.out.println("Test One Repeat");
    }

    @AfterSuit
    public void doAfterSuit() {
        System.out.println("After suit");
    }
}
