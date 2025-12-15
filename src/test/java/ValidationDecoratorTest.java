import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidationDecoratorTest {
    private DataService mockService;
    private ValidationDecorator validationDecorator;
    
    @BeforeEach
    void setUp() {
        mockService = mock(DataService.class);
        validationDecorator = new ValidationDecorator(mockService);
    }
    
    @Test
    void testFindDataByKey_WithValidKey_ShouldCallService() {
        String key = "validKey";
        String data = "testData";
        
        when(mockService.findDataByKey(key))
            .thenReturn(Optional.of(data));
        
        Optional<String> result = validationDecorator.findDataByKey(key);
        
        assertTrue(result.isPresent());
        assertEquals(data, result.get());
        verify(mockService, times(1)).findDataByKey(key);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    void testFindDataByKey_WithEmptyKey_ShouldThrowException(String invalidKey) {
        assertThrows(IllegalArgumentException.class, 
            () -> validationDecorator.findDataByKey(invalidKey));
        verify(mockService, never()).findDataByKey(any());
    }
    
    @Test
    void testFindDataByKey_WithNullKey_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, 
            () -> validationDecorator.findDataByKey(null));
        verify(mockService, never()).findDataByKey(any());
    }
    
    @Test
    void testFindDataByKey_WithTooLongKey_ShouldThrowException() {
        String longKey = "a".repeat(101);
        
        assertThrows(IllegalArgumentException.class, 
            () -> validationDecorator.findDataByKey(longKey));
        verify(mockService, never()).findDataByKey(any());
    }
    
    @Test
    void testSaveData_WithValidParameters_ShouldCallService() {
        String key = "validKey";
        String data = "validData";
        
        validationDecorator.saveData(key, data);
        
        verify(mockService, times(1)).saveData(key, data);
    }
    
    @Test
    void testSaveData_WithNullData_ShouldThrowException() {
        String key = "validKey";
        
        assertThrows(IllegalArgumentException.class, 
            () -> validationDecorator.saveData(key, null));
        verify(mockService, never()).saveData(any(), any());
    }
    
    @Test
    void testSaveData_WithTooLongData_ShouldThrowException() {
        String key = "validKey";
        String longData = "a".repeat(10001);
        
        assertThrows(IllegalArgumentException.class, 
            () -> validationDecorator.saveData(key, longData));
        verify(mockService, never()).saveData(any(), any());
    }
    
    @Test
    void testDeleteData_WithValidKey_ShouldCallService() {
        String key = "validKey";
        
        when(mockService.deleteData(key)).thenReturn(true);
        
        boolean result = validationDecorator.deleteData(key);
        
        assertTrue(result);
        verify(mockService, times(1)).deleteData(key);
    }
    
    @Test
    void testDeleteData_WithInvalidKey_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, 
            () -> validationDecorator.deleteData(null));
        verify(mockService, never()).deleteData(any());
    }
}
