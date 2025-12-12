package com.collections.student;

import java.util.*;

public class StudentMapOperations {
    
    public static List<Student> findStudentsByGradeRange(
            Map<Integer, Student> map, 
            double minGrade, 
            double maxGrade) {
        
        if (map == null) {
            throw new IllegalArgumentException("Map cannot be null");
        }
        
        if (minGrade > maxGrade) {
            throw new IllegalArgumentException("minGrade cannot be greater than maxGrade");
        }
        
        List<Student> result = new ArrayList<>();
        
        for (Student student : map.values()) {
            if (student.getGrade() >= minGrade && student.getGrade() <= maxGrade) {
                result.add(student);
            }
        }
        
        return result;
    }
 
    public static List<Student> getTopNStudents(
            TreeMap<Integer, Student> treeMap, 
            int n) {
        
        if (treeMap == null) {
            throw new IllegalArgumentException("TreeMap cannot be null");
        }
        
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        
        if (n > treeMap.size()) {
            n = treeMap.size();
        }
        
        List<Student> result = new ArrayList<>();
        int count = 0;
        
        for (Map.Entry<Integer, Student> entry : treeMap.entrySet()) {
            if (count >= n) {
                break;
            }
            result.add(entry.getValue());
            count++;
        }
        
        return result;
    }
    
    public static TreeMap<Integer, Student> createDescendingIdTreeMap() {
        return new TreeMap<>(Collections.reverseOrder());
    }
    
    public static void main(String[] args) {
        Map<Integer, Student> hashMap = new HashMap<>();
        hashMap.put(1, new Student(1, "Иван Иванов", 4.5));
        hashMap.put(2, new Student(2, "Петр Петров", 3.8));
        hashMap.put(3, new Student(3, "Сергей Сергеев", 4.2));
        hashMap.put(4, new Student(4, "Анна Аннова", 4.9));
        hashMap.put(5, new Student(5, "Мария Мариева", 3.5));
        
        List<Student> goodStudents = findStudentsByGradeRange(hashMap, 4.0, 5.0);
        System.out.println("Студенты с оценкой от 4.0 до 5.0:");
        goodStudents.forEach(System.out::println);
        
        TreeMap<Integer, Student> treeMap = createDescendingIdTreeMap();
        treeMap.putAll(hashMap);
        
        List<Student> top3Students = getTopNStudents(treeMap, 3);
        System.out.println("\nТоп 3 студентов с наибольшими id:");
        top3Students.forEach(System.out::println);
    }
}
