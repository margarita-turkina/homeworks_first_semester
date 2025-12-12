import java.lang.reflect.Field;
import java.util.regex.Pattern;
import annotations.*;
import models.ValidationResult;

public class Validator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    public static ValidationResult validate(Object object) {
        ValidationResult result = new ValidationResult();
        
        if (object == null) {
            result.addError("Объект для валидации не может быть null");
            return result;
        }
        
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            field.setAccessible(true);
            
            try {
                Object fieldValue = field.get(object);
                
                if (field.isAnnotationPresent(NotNull.class)) {
                    NotNull annotation = field.getAnnotation(NotNull.class);
                    if (fieldValue == null) {
                        result.addError(annotation.message());
                    }
                }
               
                if (field.isAnnotationPresent(Size.class) && fieldValue != null) {
                    if (field.getType().equals(String.class)) {
                        Size annotation = field.getAnnotation(Size.class);
                        String value = (String) fieldValue;
                        int length = value.length();
                        
                        if (length < annotation.min() || length > annotation.max()) {
                            result.addError(annotation.message());
                        }
                    }
                }
              
                if (field.isAnnotationPresent(Range.class) && fieldValue != null) {
                    Range annotation = field.getAnnotation(Range.class);
                    
                    if (fieldValue instanceof Number) {
                        long value = ((Number) fieldValue).longValue();
                        
                        if (value < annotation.min() || value > annotation.max()) {
                            result.addError(annotation.message());
                        }
                    } else if (fieldValue instanceof Integer || fieldValue instanceof Long || 
                               fieldValue instanceof Short || fieldValue instanceof Byte) {
                        long value = ((Number) fieldValue).longValue();
                        
                        if (value < annotation.min() || value > annotation.max()) {
                            result.addError(annotation.message());
                        }
                    }
                }
                
                if (field.isAnnotationPresent(Email.class) && fieldValue != null) {
                    if (field.getType().equals(String.class)) {
                        Email annotation = field.getAnnotation(Email.class);
                        String email = (String) fieldValue;
                        
                        if (!EMAIL_PATTERN.matcher(email).matches()) {
                            result.addError(annotation.message());
                        }
                    }
                }
                
            } catch (IllegalAccessException e) {
                result.addError("Ошибка доступа к полю: " + field.getName());
            } catch (Exception e) {
                result.addError("Ошибка при валидации поля " + field.getName() + ": " + e.getMessage());
            }
        }
        
        return result;
    }
}
