package ru.geekbrains.lesson4;

public class ThreadsPool {
    private final Object monitor = new Object();
    private volatile char currentChar = 'A';

    public static void main(String[] args) {
        doTask(new ThreadsPool());
    }

    public static void doTask(ThreadsPool threadsPool) {
        new Thread(threadsPool::printA).start();
        new Thread(threadsPool::printB).start();
        new Thread(threadsPool::printC).start();
    }

    private void printA() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 15; i++) {
                    while (currentChar != 'A') {
                        monitor.wait();
                    }
                    System.out.print("A");
                    currentChar = 'B';
                    monitor.notifyAll();
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    private void printB() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 15; i++) {
                    while (currentChar != 'B') {
                        monitor.wait();
                    }
                    System.out.print("B");
                    currentChar = 'C';
                    monitor.notifyAll();
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    private void printC() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 15; i++) {
                    while (currentChar != 'C') {
                        monitor.wait();
                    }
                    System.out.print("C ");
                    currentChar = 'A';
                    monitor.notifyAll();
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}


