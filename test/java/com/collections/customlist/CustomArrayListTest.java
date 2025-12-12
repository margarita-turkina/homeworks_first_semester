package com.collections.customlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomArrayListTest {
    
    private CustomArrayList<Integer> list;
    
    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
    }
    
    @Test
    void testAddAndGet() {
        list.add(10);
        list.add(20);
        list.add(30);
        
        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }
    
    @Test
    void testAddNullShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.add(null);
        });
    }
    
    @Test
    void testGetWithInvalidIndex() {
        list.add(10);
        
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }
    
    @Test
    void testRemove() {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        
        int removed = list.remove(1);
        assertEquals(20, removed);
        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
        assertEquals(40, list.get(2));
        
        removed = list.remove(0);
        assertEquals(10, removed);
        assertEquals(2, list.size());
        assertEquals(30, list.get(0));
        assertEquals(40, list.get(1));
    }
    
    @Test
    void testRemoveWithInvalidIndex() {
        list.add(10);
        
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }
    
    @Test
    void testSizeAndIsEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        
        list.add(10);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        
        list.add(20);
        assertEquals(2, list.size());
    }
    
    @Test
    void testDynamicExpansion() {
        CustomArrayList<Integer> customList = new CustomArrayList<>(3);
        
        for (int i = 0; i < 10; i++) {
            customList.add(i);
        }
        
        assertEquals(10, customList.size());
        assertTrue(customList.capacity() >= 10);
    }
    
    @Test
    void testIterator() {
        list.add(10);
        list.add(20);
        list.add(30);
        
        Iterator<Integer> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next());
        assertEquals(20, iterator.next());
        assertEquals(30, iterator.next());
        assertFalse(iterator.hasNext());
        
        assertThrows(NoSuchElementException.class, iterator::next);
    }
    
    @Test
    void testIteratorWithEmptyList() {
        Iterator<Integer> iterator = list.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
    
    @Test
    void testMultipleRemovesAndAdds() {
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        
        assertEquals(100, list.size());
        
        for (int i = 0; i < 50; i++) {
            list.remove(0);
        }
        
        assertEquals(50, list.size());
        assertEquals(50, list.get(0));
        
        for (int i = 100; i < 150; i++) {
            list.add(i);
        }
        
        assertEquals(100, list.size());
        assertEquals(149, list.get(99));
    }
    
    @Test
    void testInitialCapacityConstructor() {
        CustomArrayList<Integer> listWithCapacity = new CustomArrayList<>(50);
        assertEquals(0, listWithCapacity.size());
        
        for (int i = 0; i < 100; i++) {
            listWithCapacity.add(i);
        }
        
        assertEquals(100, listWithCapacity.size());
    }
    
    @Test
    void testInitialCapacityConstructorWithInvalidArgument() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CustomArrayList<>(-1);
        });
    }
}
