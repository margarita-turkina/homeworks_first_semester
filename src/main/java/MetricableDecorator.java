import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class MetricableDecorator extends AbstractDataServiceDecorator {
    private final MetricService metricService;
    
    public MetricableDecorator(DataService dataService) {
        super(dataService);
        this.metricService = new MetricService();
    }
    
    @Override
    public Optional<String> findDataByKey(String key) {
        Instant start = Instant.now();
        Optional<String> result = wrappee.findDataByKey(key);
        Duration duration = Duration.between(start, Instant.now());
        metricService.sendMetric(duration);
        return result;
    }
    
    @Override
    public void saveData(String key, String data) {
        Instant start = Instant.now();
        wrappee.saveData(key, data);
        Duration duration = Duration.between(start, Instant.now());
        metricService.sendMetric(duration);
    }
    
    @Override
    public boolean deleteData(String key) {
        Instant start = Instant.now();
        boolean result = wrappee.deleteData(key);
        Duration duration = Duration.between(start, Instant.now());
        metricService.sendMetric(duration);
        return result;
    }
    
    public static class MetricService {
        public void sendMetric(Duration duration) {
            System.out.println("Метод выполнялся: " + duration.toString());
        }
    }
}
