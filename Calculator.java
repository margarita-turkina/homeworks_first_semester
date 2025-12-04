public class Calculator<T extends Number> {
    
    public double sum(T a, T b) {
        if (a == null || b == null) {
            return 0.0;
        }
        return a.doubleValue() + b.doubleValue();
    }
    
    public double subtract(T a, T b) {
        if (a == null || b == null) {
            return 0.0;
        }
        return a.doubleValue() - b.doubleValue();
    }
    
    public double multiply(T a, T b) {
        if (a == null || b == null) {
            return 0.0;
        }
        return a.doubleValue() * b.doubleValue();
    }
    
    public double divide(T a, T b) {
        if (a == null || b == null) {
            return Double.NaN;
        }
        if (b.doubleValue() == 0.0) {
            return Double.NaN;
        }
        return a.doubleValue() / b.doubleValue();
    }

    public static void main(String[] args) {
        final Calculator<Integer> intCalc = new Calculator<>();
        final double result = intCalc.sum(5, 3);
        System.out.println("Sum: " + result); // 8.0
        
        final Calculator<Double> doubleCalc = new Calculator<>();
        final double div = doubleCalc.divide(10.0, 4.0);
        System.out.println("Divide: " + div); // 2.5
        
        final double zeroDiv = doubleCalc.divide(10.0, 0.0);
        System.out.println("Divide by zero: " + zeroDiv); // NaN
        
        final double nullResult = doubleCalc.sum(null, 5.0);
        System.out.println("Null sum: " + nullResult); // 0.0
    }
}
