import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CachingDecorator extends AbstractDataServiceDecorator {
    private final Map<String, String> cache = new HashMap<>();
    
    public CachingDecorator(DataService dataService) {
        super(dataService);
    }
    
    @Override
    public Optional<String> findDataByKey(String key) {
        if (cache.containsKey(key)) {
            return Optional.of(cache.get(key));
        }
        
        Optional<String> result = wrappee.findDataByKey(key);
        result.ifPresent(data -> cache.put(key, data));
        return result;
    }
    
    @Override
    public void saveData(String key, String data) {
        wrappee.saveData(key, data);
        cache.put(key, data);
    }
    
    @Override
    public boolean deleteData(String key) {
        boolean result = wrappee.deleteData(key);
        if (result) {
            cache.remove(key);
        }
        return result;
    }
}
