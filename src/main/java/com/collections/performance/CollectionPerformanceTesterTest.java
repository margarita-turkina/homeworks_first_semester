package com.collections.performance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CollectionPerformanceTester {
    
    private static final int ELEMENT_COUNT = 10000;
    
    public static void testPerformance() {
        System.out.println("Тестирование производительности для " + ELEMENT_COUNT + " элементов");
        System.out.println("=============================================================");
        System.out.printf("%-25s %-15s %-15s%n", "Операция", "ArrayList (ms)", "LinkedList (ms)");
        System.out.println("-------------------------------------------------------------");
      
        long arrayListAddEndTime = testArrayListAddToEnd();
        long linkedListAddEndTime = testLinkedListAddToEnd();
        System.out.printf("%-25s %-15d %-15d%n", "Добавление в конец", arrayListAddEndTime, linkedListAddEndTime);
  
        long arrayListAddStartTime = testArrayListAddToStart();
        long linkedListAddStartTime = testLinkedListAddToStart();
        System.out.printf("%-25s %-15d %-15d%n", "Добавление в начало", arrayListAddStartTime, linkedListAddStartTime);
      
        long arrayListInsertMiddleTime = testArrayListInsertMiddle();
        long linkedListInsertMiddleTime = testLinkedListInsertMiddle();
        System.out.printf("%-25s %-15d %-15d%n", "Вставка в середину", arrayListInsertMiddleTime, linkedListInsertMiddleTime);
      
        long arrayListAccessByIndexTime = testArrayListAccessByIndex();
        long linkedListAccessByIndexTime = testLinkedListAccessByIndex();
        System.out.printf("%-25s %-15d %-15d%n", "Доступ по индексу", arrayListAccessByIndexTime, linkedListAccessByIndexTime);
      
        long arrayListRemoveFromStartTime = testArrayListRemoveFromStart();
        long linkedListRemoveFromStartTime = testLinkedListRemoveFromStart();
        System.out.printf("%-25s %-15d %-15d%n", "Удаление из начала", arrayListRemoveFromStartTime, linkedListRemoveFromStartTime);
 
        long arrayListRemoveFromEndTime = testArrayListRemoveFromEnd();
        long linkedListRemoveFromEndTime = testLinkedListRemoveFromEnd();
        System.out.printf("%-25s %-15d %-15d%n", "Удаление из конца", arrayListRemoveFromEndTime, linkedListRemoveFromEndTime);
        
        System.out.println("=============================================================");
    }
    
    private static long testArrayListAddToEnd() {
        List<Integer> list = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testLinkedListAddToEnd() {
        List<Integer> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testArrayListAddToStart() {
        List<Integer> list = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(0, i);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testLinkedListAddToStart() {
        List<Integer> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(0, i);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testArrayListInsertMiddle() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < ELEMENT_COUNT / 2; i++) {
            list.add(i);
        }
        
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < ELEMENT_COUNT / 2; i++) {
            list.add(list.size() / 2, i);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testLinkedListInsertMiddle() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < ELEMENT_COUNT / 2; i++) {
            list.add(i);
        }
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < ELEMENT_COUNT / 2; i++) {
            list.add(list.size() / 2, i);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testArrayListAccessByIndex() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.get(i);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testLinkedListAccessByIndex() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.get(i);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testArrayListRemoveFromStart() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }
        
        long startTime = System.currentTimeMillis();
        
        while (!list.isEmpty()) {
            list.remove(0);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testLinkedListRemoveFromStart() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }
        
        long startTime = System.currentTimeMillis();
        
        while (!list.isEmpty()) {
            list.remove(0);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testArrayListRemoveFromEnd() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }
        
        long startTime = System.currentTimeMillis();
        
        while (!list.isEmpty()) {
            list.remove(list.size() - 1);
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static long testLinkedListRemoveFromEnd() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            list.add(i);
        }
        
        long startTime = System.currentTimeMillis();
        
        while (!list.isEmpty()) {
            list.remove(list.size() - 1);
        }
        
        return System.currentTimeMillis() - startTime;
    }
}
