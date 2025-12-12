package com.collections.customlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomArrayList<A> implements CustomList<A>, Iterable<A> {
    
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 1.5;
    
    private Object[] elements;
    private int size;
    
    public CustomArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }
    
    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }
    
    @Override
    public void add(A element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        
        ensureCapacity();
        elements[size++] = element;
    }
    
    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = (int)(elements.length * GROWTH_FACTOR);
            if (newCapacity <= elements.length) {
                newCapacity = elements.length + 1;
            }
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public A get(int index) {
        checkIndex(index);
        return (A) elements[index];
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public A remove(int index) {
        checkIndex(index);
        
        A removedElement = (A) elements[index];
        
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        
        elements[--size] = null;
        return removedElement;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                String.format("Index: %d, Size: %d", index, size)
            );
        }
    }
  
    public int capacity() {
        return elements.length;
    }
    
    @Override
    public Iterator<A> iterator() {
        return new CustomArrayListIterator();
    }
    
    /**
     * Итератор для CustomArrayList
     */
    private class CustomArrayListIterator implements Iterator<A> {
        private int currentIndex = 0;
        
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }
        
        @Override
        @SuppressWarnings("unchecked")
        public A next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (A) elements[currentIndex++];
        }
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
