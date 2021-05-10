package ru.geekbrains.lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        start("ru.geekbrains.lesson7.TestClass");
    }

    private static <T> void start(Class<T> clazz) {
        T test;

        try {
            test = clazz.getDeclaredConstructor().newInstance();
            Method[] testMethods = clazz.getDeclaredMethods();

            invokeBeforeSuit(testMethods, test);
            invokeTestByPriority(testMethods, test);
            invokeAfterSuit(testMethods, test);

        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static <T> void invokeAfterSuit(Method[] testMethods, T test) throws InvocationTargetException, IllegalAccessException {
        Method after = null;
        for (Method method : testMethods) {
            if (method.getAnnotation(AfterSuit.class) != null) {
                if (after != null) {
                    throw new RuntimeException("@AfterSuit used more than 1 time");
                }
                after = method;
            }
        }
        if (after != null) {
            after.invoke(test);
        }
    }

    private static <T> void invokeBeforeSuit(Method[] testMethods, T test) throws InvocationTargetException, IllegalAccessException {
        Method before = null;
        for (Method method : testMethods) {
            if (method.getAnnotation(BeforeSuit.class) != null) {
                if (before != null) {
                    throw new RuntimeException("@BeforeSuit used more than 1 time");
                }
                before = method;
            }
        }
        if (before != null) {
            before.invoke(test);
        }
    }

    private static <T> void invokeTestByPriority(Method[] methods, T test) throws InvocationTargetException, IllegalAccessException {
        List<Priority> priorities = new ArrayList<>();
        for (Method method : methods) {
            Test annotation = method.getAnnotation(Test.class);
            if (annotation != null) {
                priorities.add(new Priority(method, annotation.priority()));
            }
        }

        priorities.sort(Comparator.comparingInt(Priority::getPriority));
        for(Priority priority : priorities) {
            priority.getMethod().invoke(test);
        }
    }

    private static void start(String className) {
        try {
            Class clazz = Class.forName(className);
            start(clazz);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
