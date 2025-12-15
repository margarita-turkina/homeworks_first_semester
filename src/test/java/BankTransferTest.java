public class BankTransferTest {
    
    public static void main(String[] args) throws InterruptedException {
        testCorrectMethod();
        testDeadlockMethod();
        testValidation();
        testConcurrentTransfers();
    }
    
    private static void testCorrectMethod() {
        System.out.println("Тест корректного метода");
        
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount(1, 1000);
        BankAccount account2 = new BankAccount(2, 500);
        
        try {
            bank.sendToAccount(account1, account2, 300);
            System.out.println("Баланс account1: " + account1.getBalance());
            System.out.println("Баланс account2: " + account2.getBalance());
            
            try {
                bank.sendToAccount(account1, account2, 800);
            } catch (IllegalArgumentException e) {
                System.out.println("Ожидаемая ошибка: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
    
    private static void testDeadlockMethod() {
        System.out.println("Тест метода с deadlock");
        
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount(1, 1000);
        BankAccount account2 = new BankAccount(2, 1000);
        
        Thread thread1 = new Thread(() -> {
            try {
                bank.sendToAccountDeadlock(account1, account2, 100);
            } catch (Exception e) {
                System.out.println("Поток 1: " + e.getMessage());
            }
        }, "Thread-1");
        
        Thread thread2 = new Thread(() -> {
            try {
                bank.sendToAccountDeadlock(account2, account1, 100);
            } catch (Exception e) {
                System.out.println("Поток 2: " + e.getMessage());
            }
        }, "Thread-2");
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join(2000);
            thread2.join(2000);
            
            if (thread1.isAlive() || thread2.isAlive()) {
                System.out.println("Обнаружен deadlock!");
                thread1.interrupt();
                thread2.interrupt();
            } else {
                System.out.println("Переводы завершены успешно");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private static void testValidation() {
        System.out.println("Тест валидации");
        
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount(1, 1000);
        
        try {
            bank.sendToAccount(null, account1, 100);
        } catch (IllegalArgumentException e) {
            System.out.println("Тест на null пройден: " + e.getMessage());
        }
        
        try {
            bank.sendToAccount(account1, account1, -100);
        } catch (IllegalArgumentException e) {
            System.out.println("Тест на отрицательную сумму пройден: " + e.getMessage());
        }
        
        try {
            bank.sendToAccount(account1, account1, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Тест на нулевую сумму пройден: " + e.getMessage());
        }
    }
    
    private static void testConcurrentTransfers() {
        System.out.println("Тест конкурентных переводов");
        
        Bank bank = new Bank();
        BankAccount[] accounts = new BankAccount[5];
        
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new BankAccount(i + 1, 1000);
        }
        
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < 5; j++) {
                        int fromIndex = (threadId + j) % accounts.length;
                        int toIndex = (threadId + j + 1) % accounts.length;
                        int amount = 10 + (threadId * 5) + j;
                        
                        bank.sendToAccount(accounts[fromIndex], accounts[toIndex], amount);
                        
                        Thread.sleep(1);
                    }
                } catch (Exception e) {
                    System.out.println("Поток " + threadId + " завершился с ошибкой: " + e.getMessage());
                }
            }, "Transfer-Thread-" + i);
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        boolean allCompleted = true;
        for (Thread thread : threads) {
            try {
                thread.join(3000);
                if (thread.isAlive()) {
                    allCompleted = false;
                    thread.interrupt();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        int totalBalance = 0;
        for (BankAccount account : accounts) {
            totalBalance += account.getBalance();
        }
        
        System.out.println("Общий баланс всех счетов: " + totalBalance);
        System.out.println("Ожидаемый баланс: " + (accounts.length * 1000));
        
        if (totalBalance == accounts.length * 1000) {
            System.out.println("Тест пройден: общий баланс сохранен");
        } else {
            System.out.println("Тест не пройден: общий баланс не совпадает");
        }
        
        if (allCompleted) {
            System.out.println("Все потоки завершились успешно");
        } else {
            System.out.println("Некоторые потоки не завершились");
        }
    }
}
