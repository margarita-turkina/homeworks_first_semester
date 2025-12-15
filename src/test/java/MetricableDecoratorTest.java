import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MetricableDecoratorTest {
    private DataService mockService;
    private MetricableDecorator metricableDecorator;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @BeforeEach
    void setUp() {
        mockService = mock(DataService.class);
        metricableDecorator = new MetricableDecorator(mockService);
        System.setOut(new PrintStream(outputStream));
    }
    
    @Test
    void testFindDataByKey_ShouldSendMetrics() {
        String key = "testKey";
        String data = "testData";
        
        when(mockService.findDataByKey(key))
            .thenReturn(Optional.of(data));
        
        Optional<String> result = metricableDecorator.findDataByKey(key);
        
        assertTrue(result.isPresent());
        assertEquals(data, result.get());
        
        String output = outputStream.toString();
        assertTrue(output.contains("Метод выполнялся: PT"));
        
        verify(mockService, times(1)).findDataByKey(key);
    }
    
    @Test
    void testSaveData_ShouldSendMetrics() {
        String key = "testKey";
        String data = "testData";
        
        metricableDecorator.saveData(key, data);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Метод выполнялся: PT"));
        
        verify(mockService, times(1)).saveData(key, data);
    }
    
    @Test
    void testDeleteData_ShouldSendMetrics() {
        String key = "testKey";
        
        when(mockService.deleteData(key)).thenReturn(true);
        
        boolean result = metricableDecorator.deleteData(key);
        
        assertTrue(result);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Метод выполнялся: PT"));
        
        verify(mockService, times(1)).deleteData(key);
    }
    
    @Test
    void testMetricService_ShouldFormatDuration() {
        MetricableDecorator.MetricService metricService = 
            new MetricableDecorator.MetricService();
        
        metricService.sendMetric(java.time.Duration.ofMillis(150));
        
        String output = outputStream.toString();
        assertTrue(output.contains("Метод выполнялся: PT0.15S") || 
                   output.contains("Метод выполнялся: PT0.150S"));
    }
}
