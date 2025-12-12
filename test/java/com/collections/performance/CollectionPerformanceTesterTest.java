package com.collections.performance;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CollectionPerformanceTesterTest {
    
    @Test
    void testPerformanceMethodsDoNotThrowExceptions() {
        assertDoesNotThrow(() -> {
            long time = CollectionPerformanceTester.testArrayListAddToEnd();
            assertTrue(time >= 0);
        });
        
        assertDoesNotThrow(() -> {
            long time = CollectionPerformanceTester.testLinkedListAddToEnd();
            assertTrue(time >= 0);
        });
        
        assertDoesNotThrow(() -> {
            CollectionPerformanceTester.testPerformance();
        });
    }
    
    @Test
    void testAllPerformanceMethods() {
        assertTrue(CollectionPerformanceTester.testArrayListAddToEnd() >= 0);
        assertTrue(CollectionPerformanceTester.testLinkedListAddToEnd() >= 0);
        assertTrue(CollectionPerformanceTester.testArrayListAddToStart() >= 0);
        assertTrue(CollectionPerformanceTester.testLinkedListAddToStart() >= 0);
        assertTrue(CollectionPerformanceTester.testArrayListInsertMiddle() >= 0);
        assertTrue(CollectionPerformanceTester.testLinkedListInsertMiddle() >= 0);
        assertTrue(CollectionPerformanceTester.testArrayListAccessByIndex() >= 0);
        assertTrue(CollectionPerformanceTester.testLinkedListAccessByIndex() >= 0);
        assertTrue(CollectionPerformanceTester.testArrayListRemoveFromStart() >= 0);
        assertTrue(CollectionPerformanceTester.testLinkedListRemoveFromStart() >= 0);
        assertTrue(CollectionPerformanceTester.testArrayListRemoveFromEnd() >= 0);
        assertTrue(CollectionPerformanceTester.testLinkedListRemoveFromEnd() >= 0);
    }
}
