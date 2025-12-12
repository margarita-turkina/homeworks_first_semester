package com.collections;

import com.collections.customlist.CustomArrayList;
import com.collections.performance.CollectionPerformanceTester;
import com.collections.student.Student;
import com.collections.student.StudentMapOperations;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Задание 1: Кастомный ArrayList ===\n");
        
        CustomArrayList<String> customList = new CustomArrayList<>();
        customList.add("First");
        customList.add("Second");
        customList.add("Third");
        
        System.out.println("Содержимое CustomArrayList: " + customList);
        System.out.println("Размер: " + customList.size());
        System.out.println("Элемент с индексом 1: " + customList.get(1));
        
        String removed = customList.remove(1);
        System.out.println("Удален элемент: " + removed);
        System.out.println("Содержимое после удаления: " + customList);
        
        System.out.println("\n=== Задание 2: Анализ производительности коллекций ===\n");
        CollectionPerformanceTester.testPerformance();
        
        System.out.println("\n=== Задание 3: Работа с HashMap и TreeMap ===\n");
        
        Map<Integer, Student> studentMap = new HashMap<>();
        studentMap.put(101, new Student(101, "Алексей", 4.2));
        studentMap.put(102, new Student(102, "Елена", 3.8));
        studentMap.put(103, new Student(103, "Дмитрий", 4.7));
        studentMap.put(104, new Student(104, "Ольга", 3.5));
        studentMap.put(105, new Student(105, "Игорь", 4.9));
        
        List<Student> studentsInRange = StudentMapOperations.findStudentsByGradeRange(
            studentMap, 4.0, 5.0);
        
        System.out.println("Студенты с оценкой от 4.0 до 5.0:");
        studentsInRange.forEach(System.out::println);
        
        TreeMap<Integer, Student> descendingTreeMap = StudentMapOperations.createDescendingIdTreeMap();
        descendingTreeMap.putAll(studentMap);
        
        List<Student> topStudents = StudentMapOperations.getTopNStudents(descendingTreeMap, 2);
        
        System.out.println("\nТоп 2 студентов с наибольшими id:");
        topStudents.forEach(System.out::println);
    }
}
