class BankAccount {
    private final long id;
    private int balance;
    
    public BankAccount(long id, int initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }
    
    public long getId() {
        return id;
    }
    
    public int getBalance() {
        return balance;
    }
    
    public void deposit(int amount) {
        balance += amount;
    }
    
    public void withdraw(int amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
        balance -= amount;
    }
}
