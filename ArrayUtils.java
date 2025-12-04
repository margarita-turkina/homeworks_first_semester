public class ArrayUtils {
    public static <T> int findFirst(T[] array, T element) {
        if (array == null) {
            return -1;
        }
        
        for (int i = 0; i < array.length; i++) {
            if (element == null && array[i] == null) {
                return i;
            }
            if (element != null && element.equals(array[i])) {
                return i;
            }
        }
        
        return -1;
    }

    public static void main(String[] args) {
        final String[] names = {"Alice", "Bob", "Charlie"};
        final int index = ArrayUtils.findFirst(names, "Bob");
        System.out.println("Index: " + index); // Ожидаем: 1
        
        final Integer[] numbers = {1, null, 3};
        System.out.println("Null index: " + ArrayUtils.findFirst(numbers, null)); // 1
        System.out.println("Not found: " + ArrayUtils.findFirst(numbers, 5)); // -1
        System.out.println("Null array: " + ArrayUtils.findFirst(null, "test")); // -1
    }
}
