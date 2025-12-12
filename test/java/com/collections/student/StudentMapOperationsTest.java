package com.collections.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class StudentMapOperationsTest {
    
    private Map<Integer, Student> hashMap;
    private TreeMap<Integer, Student> treeMap;
    
    @BeforeEach
    void setUp() {
        hashMap = new HashMap<>();
        hashMap.put(1, new Student(1, "Student1", 3.0));
        hashMap.put(2, new Student(2, "Student2", 4.0));
        hashMap.put(3, new Student(3, "Student3", 4.5));
        hashMap.put(4, new Student(4, "Student4", 3.5));
        hashMap.put(5, new Student(5, "Student5", 5.0));
        
        treeMap = StudentMapOperations.createDescendingIdTreeMap();
        treeMap.putAll(hashMap);
    }
    
    @Test
    void testFindStudentsByGradeRange() {
        List<Student> result = StudentMapOperations.findStudentsByGradeRange(hashMap, 4.0, 5.0);
        
        assertEquals(3, result.size());
        
        for (Student student : result) {
            assertTrue(student.getGrade() >= 4.0 && student.getGrade() <= 5.0);
        }
    }
    
    @Test
    void testFindStudentsByGradeRangeBoundaryValues() {
        List<Student> result = StudentMapOperations.findStudentsByGradeRange(hashMap, 3.5, 4.5);
        
        assertEquals(4, result.size());
        
        for (Student student : result) {
            assertTrue(student.getGrade() >= 3.5 && student.getGrade() <= 4.5);
        }
    }
    
    @Test
    void testFindStudentsByGradeRangeEmptyResult() {
        List<Student> result = StudentMapOperations.findStudentsByGradeRange(hashMap, 1.0, 2.0);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testFindStudentsByGradeRangeAllStudents() {
        List<Student> result = StudentMapOperations.findStudentsByGradeRange(hashMap, 0.0, 10.0);
        assertEquals(5, result.size());
    }
    
    @Test
    void testFindStudentsByGradeRangeInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            StudentMapOperations.findStudentsByGradeRange(null, 1.0, 2.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            StudentMapOperations.findStudentsByGradeRange(hashMap, 5.0, 1.0);
        });
    }
    
    @Test
    void testGetTopNStudents() {
        List<Student> top3 = StudentMapOperations.getTopNStudents(treeMap, 3);
        
        assertEquals(3, top3.size());
        
        assertEquals(5, top3.get(0).getId());
        assertEquals(4, top3.get(1).getId());
        assertEquals(3, top3.get(2).getId());
    }
    
    @Test
    void testGetTopNStudentsSingleElement() {
        List<Student> top1 = StudentMapOperations.getTopNStudents(treeMap, 1);
        
        assertEquals(1, top1.size());
        assertEquals(5, top1.get(0).getId());
    }
    
    @Test
    void testGetTopNStudentsWithNGreaterThanSize() {
        List<Student> top10 = StudentMapOperations.getTopNStudents(treeMap, 10);
        
        assertEquals(5, top10.size());
        
        assertEquals(5, top10.get(0).getId());
        assertEquals(4, top10.get(1).getId());
        assertEquals(3, top10.get(2).getId());
        assertEquals(2, top10.get(3).getId());
        assertEquals(1, top10.get(4).getId());
    }
    
    @Test
    void testGetTopNStudentsInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            StudentMapOperations.getTopNStudents(null, 3);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            StudentMapOperations.getTopNStudents(treeMap, 0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            StudentMapOperations.getTopNStudents(treeMap, -1);
        });
    }
    
    @Test
    void testCreateDescendingIdTreeMap() {
        TreeMap<Integer, Student> descendingMap = StudentMapOperations.createDescendingIdTreeMap();
        
        descendingMap.put(1, new Student(1, "A", 1.0));
        descendingMap.put(3, new Student(3, "C", 3.0));
        descendingMap.put(2, new Student(2, "B", 2.0));
        
        Iterator<Integer> iterator = descendingMap.keySet().iterator();
        assertEquals(3, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(1, iterator.next());
    }
    
    @Test
    void testStudentEqualsAndHashCode() {
        Student student1 = new Student(1, "John", 4.5);
        Student student2 = new Student(1, "John", 4.5);
        Student student3 = new Student(2, "Jane", 4.0);
        
        assertEquals(student1, student2);
        assertNotEquals(student1, student3);
        assertEquals(student1.hashCode(), student2.hashCode());
        assertNotEquals(student1.hashCode(), student3.hashCode());
    }
    
    @Test
    void testStudentToString() {
        Student student = new Student(1, "John", 4.5);
        String toString = student.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name='John'"));
        assertTrue(toString.contains("grade=4.5"));
    }
}
