import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    
    @Test
    void testValidUser() {
        User user = new User("Иван", "ivan@example.com", 25, "secure123");
        ValidationResult result = Validator.validate(user);
        
        assertTrue(result.isValid());
        assertEquals(0, result.getErrors().size());
    }
    
    @Test
    void testNullName() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setAge(30);
        user.setPassword("password123");
        
        ValidationResult result = Validator.validate(user);
        
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Имя не может быть null"));
        assertTrue(result.getErrors().contains("Имя должно быть от 2 до 50 символов"));
    }
    
    @Test
    void testShortName() {
        User user = new User("A", "test@example.com", 30, "password123");
        ValidationResult result = Validator.validate(user);
        
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Имя должно быть от 2 до 50 символов"));
    }
    
    @Test
    void testInvalidEmail() {
        User user = new User("Иван", "invalid-email", 25, "secure123");
        ValidationResult result = Validator.validate(user);
        
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Некорректный формат email"));
    }
    
    @Test
    void testNullEmail() {
        User user = new User("Иван", null, 25, "secure123");
        ValidationResult result = Validator.validate(user);
        
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Email не может быть null"));
        assertTrue(result.getErrors().contains("Некорректный формат email"));
    }
    
    @Test
    void testInvalidAge() {
        User user = new User("Иван", "ivan@example.com", -5, "secure123");
        ValidationResult result = Validator.validate(user);
        
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Возраст должен быть от 0 до 150"));
    }
    
    @Test
    void testInvalidPasswordLength() {
        User user = new User("Иван", "ivan@example.com", 25, "123");
        ValidationResult result = Validator.validate(user);
        
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Пароль должен быть от 6 до 20 символов"));
    }
    
    @Test
    void testLongPassword() {
        User user = new User("Иван", "ivan@example.com", 25, "thispasswordistoolongformyvalidationframework");
        ValidationResult result = Validator.validate(user);
        
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Пароль должен быть от 6 до 20 символов"));
    }
    
    @Test
    void testAgeTooHigh() {
        User user = new User("Иван", "ivan@example.com", 200, "secure123");
        ValidationResult result = Validator.validate(user);
        
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Возраст должен быть от 0 до 150"));
    }
    
    @Test
    void testValidEmailVariations() {
        User user1 = new User("Иван", "ivan.petrov@example.com", 25, "secure123");
        User user2 = new User("Петр", "petr_ivanov@example.co.uk", 30, "secure456");
        
        ValidationResult result1 = Validator.validate(user1);
        ValidationResult result2 = Validator.validate(user2);
        
        assertTrue(result1.isValid());
        assertTrue(result2.isValid());
    }
    
    @Test
    void testMultipleErrors() {
        User user = new User("A", "invalid", 200, "123");
        ValidationResult result = Validator.validate(user);
        
        assertFalse(result.isValid());
        assertEquals(4, result.getErrors().size());
    }
    
    @Test
    void testNullObject() {
        ValidationResult result = Validator.validate(null);
        
        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Объект для валидации не может быть null"));
    }
}
