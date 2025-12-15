public abstract class AbstractDataServiceDecorator implements DataService {
    protected final DataService wrappee;
    
    protected AbstractDataServiceDecorator(DataService dataService) {
        this.wrappee = dataService;
    }
    
    @Override
    public Optional<String> findDataByKey(String key) {
        return wrappee.findDataByKey(key);
    }
    
    @Override
    public void saveData(String key, String data) {
        wrappee.saveData(key, data);
    }
    
    @Override
    public boolean deleteData(String key) {
        return wrappee.deleteData(key);
    }
}
