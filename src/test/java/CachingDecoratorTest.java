import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CachingDecoratorTest {
    private DataService mockService;
    private CachingDecorator cachingDecorator;
    
    @BeforeEach
    void setUp() {
        mockService = mock(DataService.class);
        cachingDecorator = new CachingDecorator(mockService);
    }
    
    @Test
    void testFindDataByKey_ShouldCacheResult() {
        String key = "testKey";
        String data = "testData";
        
        when(mockService.findDataByKey(key))
            .thenReturn(Optional.of(data));
      
        Optional<String> result1 = cachingDecorator.findDataByKey(key);
        assertTrue(result1.isPresent());
        assertEquals(data, result1.get());
        verify(mockService, times(1)).findDataByKey(key);
        
        Optional<String> result2 = cachingDecorator.findDataByKey(key);
        assertTrue(result2.isPresent());
        assertEquals(data, result2.get());
        verify(mockService, times(1)).findDataByKey(key);
    }
    
    @Test
    void testSaveData_ShouldUpdateCache() {
        String key = "testKey";
        String data = "testData";
        
        cachingDecorator.saveData(key, data);
    
        Optional<String> result = cachingDecorator.findDataByKey(key);
        assertTrue(result.isPresent());
        assertEquals(data, result.get());
        verify(mockService, times(1)).saveData(key, data);
    }
    
    @Test
    void testDeleteData_ShouldInvalidateCache() {
        String key = "testKey";
        
        when(mockService.deleteData(key)).thenReturn(true);
        
        cachingDecorator.saveData(key, "data");
        
        boolean result = cachingDecorator.deleteData(key);
        assertTrue(result);
        
        Optional<String> cachedResult = cachingDecorator.findDataByKey(key);
        assertTrue(cachedResult.isEmpty());
        verify(mockService, times(1)).deleteData(key);
    }
    
    @Test
    void testCacheMiss_ShouldReturnEmptyOptional() {
        String key = "nonExistentKey";
        
        when(mockService.findDataByKey(key))
            .thenReturn(Optional.empty());
        
        Optional<String> result = cachingDecorator.findDataByKey(key);
        assertFalse(result.isPresent());
        verify(mockService, times(1)).findDataByKey(key);
    }
}
