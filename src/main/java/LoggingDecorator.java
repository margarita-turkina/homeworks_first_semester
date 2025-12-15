import java.util.Optional;

public class LoggingDecorator extends AbstractDataServiceDecorator {
    
    public LoggingDecorator(DataService dataService) {
        super(dataService);
    }
    
    @Override
    public Optional<String> findDataByKey(String key) {
        System.out.println("Выполняется поиск данных по ключу: " + key);
        Optional<String> result = wrappee.findDataByKey(key);
        System.out.println("Результат поиска по ключу " + key + ": " + 
            result.map(data -> "найдено").orElse("не найдено"));
        return result;
    }
    
    @Override
    public void saveData(String key, String data) {
        System.out.println("Сохранение данных. Ключ: " + key + ", данные: " + data);
        wrappee.saveData(key, data);
        System.out.println("Данные сохранены для ключа: " + key);
    }
    
    @Override
    public boolean deleteData(String key) {
        System.out.println("Удаление данных по ключу: " + key);
        boolean result = wrappee.deleteData(key);
        System.out.println("Результат удаления ключа " + key + ": " + 
            (result ? "успешно" : "ключ не найден"));
        return result;
    }
}
