class Bank {
    
    public void sendToAccountDeadlock(BankAccount from, BankAccount to, int amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Счета не могут быть null");
        }
        
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }
        
        synchronized (from) {
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            synchronized (to) {
                
                if (from.getBalance() < amount) {
                    throw new IllegalArgumentException("Недостаточно средств на счете отправителя");
                }
                
                from.withdraw(amount);
                to.deposit(amount);
                
            }
        }
    }
    
    public void sendToAccount(BankAccount from, BankAccount to, int amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Счета не могут быть null");
        }
        
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }
        
        BankAccount first = from.getId() < to.getId() ? from : to;
        BankAccount second = from.getId() < to.getId() ? to : from;
        
        synchronized (first) {
            synchronized (second) {
                if (from.getBalance() < amount) {
                    throw new IllegalArgumentException("Недостаточно средств на счете отправителя");
                }
                
                from.withdraw(amount);
                to.deposit(amount);
                
            }
        }
    }
}
