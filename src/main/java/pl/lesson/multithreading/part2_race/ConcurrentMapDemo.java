package pl.lesson.multithreading.part2_race;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap vs ConcurrentHashMap przy zapisie z wielu wątków.
 */
public class ConcurrentMapDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("HashMap:           " + count(new HashMap<>()));
        System.out.println("ConcurrentHashMap: " + count(new ConcurrentHashMap<>()));
    }

    static int count(Map<String, Integer> map) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10_000; i++) {
                map.merge("klucz", 1, Integer::sum); // w ConcurrentHashMap merge jest atomowe
            }
        };
        Thread a = new Thread(task);
        Thread b = new Thread(task);
        a.start();
        b.start();
        a.join();
        b.join();
        return map.get("klucz"); // oczekiwane 20000
    }
}
