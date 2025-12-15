import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoggingDecoratorTest {
    private DataService mockService;
    private LoggingDecorator loggingDecorator;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @BeforeEach
    void setUp() {
        mockService = mock(DataService.class);
        loggingDecorator = new LoggingDecorator(mockService);
        System.setOut(new PrintStream(outputStream));
    }
    
    @Test
    void testFindDataByKey_ShouldLogOperation() {
        String key = "testKey";
        String data = "testData";
        
        when(mockService.findDataByKey(key))
            .thenReturn(Optional.of(data));
        
        Optional<String> result = loggingDecorator.findDataByKey(key);
        
        assertTrue(result.isPresent());
        assertEquals(data, result.get());
        
        String output = outputStream.toString();
        assertTrue(output.contains("Выполняется поиск данных по ключу: " + key));
        assertTrue(output.contains("Результат поиска по ключу " + key + ": найдено"));
        
        verify(mockService, times(1)).findDataByKey(key);
    }
    
    @Test
    void testFindDataByKey_ShouldLogNotFound() {
        String key = "nonExistentKey";
        
        when(mockService.findDataByKey(key))
            .thenReturn(Optional.empty());
        
        Optional<String> result = loggingDecorator.findDataByKey(key);
        
        assertFalse(result.isPresent());
        
        String output = outputStream.toString();
        assertTrue(output.contains("Результат поиска по ключу " + key + ": не найдено"));
    }
    
    @Test
    void testSaveData_ShouldLogOperation() {
        String key = "testKey";
        String data = "testData";
        
        loggingDecorator.saveData(key, data);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Сохранение данных. Ключ: " + key + ", данные: " + data));
        assertTrue(output.contains("Данные сохранены для ключа: " + key));
        
        verify(mockService, times(1)).saveData(key, data);
    }
    
    @Test
    void testDeleteData_ShouldLogSuccess() {
        String key = "testKey";
        
        when(mockService.deleteData(key)).thenReturn(true);
        
        boolean result = loggingDecorator.deleteData(key);
        
        assertTrue(result);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Удаление данных по ключу: " + key));
        assertTrue(output.contains("Результат удаления ключа " + key + ": успешно"));
        
        verify(mockService, times(1)).deleteData(key);
    }
    
    @Test
    void testDeleteData_ShouldLogFailure() {
        String key = "nonExistentKey";
        
        when(mockService.deleteData(key)).thenReturn(false);
        
        boolean result = loggingDecorator.deleteData(key);
        
        assertFalse(result);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Результат удаления ключа " + key + ": ключ не найден"));
    }
}
