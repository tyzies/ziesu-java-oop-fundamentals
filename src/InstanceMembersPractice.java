import java.util.Random;

public class InstanceMembersPractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Instance Variables & Methods
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk menguasai
         * instance variables, instance methods, dan perbedaannya dengan static.
         */

        // ===== INSTANCE VARIABLES BASICS =====
        System.out.println("=== INSTANCE VARIABLES BASICS ===");

        // Latihan 1: Memahami instance variables
        // Buat beberapa object dari class BankAccount
        // Tunjukkan bahwa setiap object memiliki data sendiri

        // Buat 3 object BankAccount dengan data berbeda
        BankAccount acc1 = new BankAccount("A1001", "Andi", "Andi Pratama", "Student");
        BankAccount acc2 = new BankAccount("A1002", "Budi", "Budi Santoso", "Savings");
        BankAccount acc3 = new BankAccount("A1003", "Cici", "Cici Lestari", "Checking");

        // Tampilkan saldo masing-masing account
        System.out.println("-- Saldo awal --");
        acc1.displayBalance();
        acc2.displayBalance();
        acc3.displayBalance();

        // Lakukan transaksi pada masing-masing account
        acc1.deposit(500).withdraw(120); // chaining
        acc2.deposit(1000);
        acc3.deposit(200).withdraw(50).deposit(30);

        // Tunjukkan bahwa perubahan pada satu object tidak mempengaruhi yang lain
        System.out.println("\n-- Saldo setelah transaksi (menunjukkan instance terpisah) --");
        acc1.displayBalance();
        acc2.displayBalance();
        acc3.displayBalance();

        // ===== INSTANCE METHODS ADVANCED =====
        System.out.println("\n=== INSTANCE METHODS ADVANCED ===");

        // Latihan 2: Instance methods yang bekerja dengan instance variables
        // Implementasikan berbagai jenis instance methods
        // Methods yang mengubah state object
        // Methods yang mengembalikan nilai berdasarkan state
        // Methods yang berinteraksi dengan object lain

        // Demonstrasikan berbagai jenis instance methods
        System.out.println("\n-- Demonstrasi method yang mengubah state dan mengembalikan nilai --");
        double interestAcc2 = acc2.calculateInterest(0.05); // 5% interest on balance (return value)
        System.out.println("Bunga (5%) untuk " + acc2.getAccountInfo() + ": " + interestAcc2);
        // Terapkan bunga ke akun (jika ingin)
        acc2.deposit(interestAcc2);

        System.out.println("Status akun acc1: " + acc1.getAccountStatus());
        System.out.println("Apakah acc3 bisa tarik 500? " + acc3.canWithdraw(500));

        // ===== METHOD INTERACTION =====
        System.out.println("\n=== METHOD INTERACTION ===");

        // Latihan 3: Methods yang memanggil methods lain
        // Buat methods yang memanggil methods lain dalam class yang sama
        // Demonstrasikan method chaining

        // Implementasikan method chaining dan interaction
        System.out.println("\n-- Demonstrasi method chaining (deposit().withdraw().deposit()) --");
        acc1.deposit(100).withdraw(50).deposit(200);
        acc1.displayBalance();
        acc1.printStatement();

        // ===== OBJECT COLLABORATION =====
        System.out.println("\n=== OBJECT COLLABORATION ===");

        // Latihan 4: Object yang berinteraksi dengan object lain
        // Implementasikan transfer uang antar account
        // Buat history transaksi

        // Implementasikan object collaboration
        System.out.println("\n-- Demonstrasi transfer antar akun --");
        System.out.println("Sebelum transfer:");
        acc1.displayBalance();
        acc2.displayBalance();

        boolean transferResult = acc2.transfer(acc1, 300);
        System.out.println("Transfer 300 dari acc2 ke acc1: " + (transferResult ? "Berhasil" : "Gagal"));

        System.out.println("Setelah transfer:");
        acc1.displayBalance();
        acc2.displayBalance();

        System.out.println("\n-- Statement acc1 --");
        acc1.printStatement();
        System.out.println("\n-- Statement acc2 --");
        acc2.printStatement();

        // Demonstrasi Customer dengan banyak akun
        System.out.println("\n=== CUSTOMER & MULTI-ACCOUNT ===");
        Customer customer = new Customer("CUST01", "Dewi", "08123456789", "dewi@example.com");
        customer.addAccount(acc1);
        customer.addAccount(acc3);

        customer.printCustomerInfo();
        System.out.println("Total saldo customer: " + customer.getTotalBalance());

        // Deactivate and check status
        acc3.deactivateAccount();
        System.out.println("Status acc3 setelah deactive: " + acc3.getAccountStatus());

        System.out.println("\n=== AKHIR PRAKTIK INSTANCE MEMBERS ===");
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class BankAccount
class BankAccount {
    // Instance variables
    // accountNumber, accountHolderName, balance, accountType, isActive
    public String accountNumber;
    public String accountHolderId; // e.g., pemilik id/simple identifier
    public String accountHolderName;
    public String accountType;
    public double balance;
    public boolean isActive;

    // Transaction history
    private java.util.ArrayList<Transaction> history;

    // For simple ID/number generation when needed
    private static java.util.Random rnd = new java.util.Random();

    // Constructor(s)
    // Buat multiple constructors
    public BankAccount() {
        // generate a simple random account number if not provided
        this.accountNumber = "AC" + (1000 + rnd.nextInt(9000));
        this.accountHolderId = "unknown";
        this.accountHolderName = "Unknown";
        this.accountType = "Standard";
        this.balance = 0.0;
        this.isActive = true;
        this.history = new java.util.ArrayList<>();
    }

    public BankAccount(String accountNumber, String accountHolderId, String accountHolderName, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolderId = accountHolderId;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = 0.0;
        this.isActive = true;
        this.history = new java.util.ArrayList<>();
    }

    public BankAccount(String accountNumber, String accountHolderId, String accountHolderName, String accountType, double initialBalance) {
        this(accountNumber, accountHolderId, accountHolderName, accountType);
        if (initialBalance > 0) {
            this.balance = initialBalance;
            this.history.add(new Transaction("INIT", "INITIAL", initialBalance, java.time.LocalDateTime.now(), "Initial balance"));
        }
    }

    // Instance methods untuk account operations
    // deposit(double amount)
    public BankAccount deposit(double amount) {
        if (!isActive) {
            System.out.println("Akun " + accountNumber + " tidak aktif. Deposit gagal.");
            return this;
        }
        if (amount <= 0) {
            System.out.println("Jumlah deposit harus > 0.");
            return this;
        }
        this.balance += amount;
        this.history.add(new Transaction(generateTxnId(), "DEPOSIT", amount, java.time.LocalDateTime.now(), "Deposit ke akun"));
        return this; // mengembalikan this untuk memungkinkan chaining
    }

    // withdraw(double amount) - with validation
    public BankAccount withdraw(double amount) {
        if (!isActive) {
            System.out.println("Akun " + accountNumber + " tidak aktif. Withdraw gagal.");
            return this;
        }
        if (amount <= 0) {
            System.out.println("Jumlah withdraw harus > 0.");
            return this;
        }
        if (!canWithdraw(amount)) {
            System.out.println("Saldo tidak cukup atau aturan penarikan tidak terpenuhi untuk akun " + accountNumber);
            return this;
        }
        this.balance -= amount;
        this.history.add(new Transaction(generateTxnId(), "WITHDRAW", amount, java.time.LocalDateTime.now(), "Withdrawal dari akun"));
        return this;
    }

    // transfer(BankAccount target, double amount)
    public boolean transfer(BankAccount target, double amount) {
        if (!this.isActive) {
            System.out.println("Akun pengirim (" + this.accountNumber + ") tidak aktif. Transfer gagal.");
            return false;
        }
        if (!target.isActive) {
            System.out.println("Akun penerima (" + target.accountNumber + ") tidak aktif. Transfer gagal.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Jumlah transfer harus > 0.");
            return false;
        }
        if (!canWithdraw(amount)) {
            System.out.println("Saldo tidak cukup pada akun " + this.accountNumber + ". Transfer gagal.");
            return false;
        }
        // lakukan pemindahan
        this.balance -= amount;
        target.balance += amount;

        // catat transaksi di kedua akun
        String txnId = generateTxnId();
        this.history.add(new Transaction(txnId, "TRANSFER_OUT", amount, java.time.LocalDateTime.now(), "Transfer ke " + target.accountNumber));
        target.history.add(new Transaction(txnId, "TRANSFER_IN", amount, java.time.LocalDateTime.now(), "Transfer dari " + this.accountNumber));
        return true;
    }

    // getBalance()
    public double getBalance() {
        return this.balance;
    }

    // getAccountInfo()
    public String getAccountInfo() {
        return this.accountNumber + " (" + this.accountHolderName + ")";
    }

    // activateAccount() / deactivateAccount()
    public void activateAccount() {
        this.isActive = true;
        this.history.add(new Transaction(generateTxnId(), "ACTIVATE", 0.0, java.time.LocalDateTime.now(), "Activate account"));
    }

    public void deactivateAccount() {
        this.isActive = false;
        this.history.add(new Transaction(generateTxnId(), "DEACTIVATE", 0.0, java.time.LocalDateTime.now(), "Deactivate account"));
    }

    // Instance methods untuk business logic
    // calculateInterest(double rate)
    public double calculateInterest(double rate) {
        if (rate <= 0) return 0.0;
        return this.balance * rate;
    }

    // canWithdraw(double amount)
    public boolean canWithdraw(double amount) {
        // contoh aturan: harus aktif, amount positif, dan balance >= amount
        return this.isActive && amount > 0 && this.balance >= amount;
    }

    // getAccountStatus()
    public String getAccountStatus() {
        if (!isActive) return "Inactive";
        if (balance <= 0) return "Zero/Negative Balance";
        if (balance < 100) return "Low Balance";
        return "Active";
    }

    // Instance methods untuk formatting/display
    // displayBalance()
    public void displayBalance() {
        System.out.println("Account: " + accountNumber + " | Holder: " + accountHolderName + " | Balance: " + String.format("%.2f", balance));
    }

    // printStatement()
    public void printStatement() {
        System.out.println("=== Statement untuk " + accountNumber + " - " + accountHolderName + " ===");
        if (history.isEmpty()) {
            System.out.println("(Tidak ada transaksi)");
            return;
        }
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Transaction t : history) {
            System.out.println(t.getTimestamp().format(fmt) + " | " + t.type + " | " + String.format("%.2f", t.amount) + " | " + t.description + " | ID:" + t.transactionId);
        }
    }

    // Helper - generate transaction id (simple)
    private String generateTxnId() {
        return "TX" + Math.abs(rnd.nextInt(1_000_000));
    }
}

// Implementasikan class Transaction (untuk history)
class Transaction {
    // transactionId, type, amount, timestamp, description
    public String transactionId;
    public String type;
    public double amount;
    private java.time.LocalDateTime timestamp;
    public String description;

    // Constructor dan methods
    public Transaction(String transactionId, String type, double amount, java.time.LocalDateTime timestamp, String description) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
    }

    public java.time.LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return "[" + transactionId + "] " + type + " " + amount + " at " + timestamp + " (" + description + ")";
    }
}

// Implementasikan class Customer
class Customer {
    // customerId, name, phone, email, accounts[]

    public String customerId;
    public String name;
    public String phone;
    public String email;
    private java.util.ArrayList<BankAccount> accounts;

    // Constructor
    public Customer(String customerId, String name, String phone, String email) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.accounts = new java.util.ArrayList<>();
    }

    // Methods untuk mengelola multiple accounts
    public void addAccount(BankAccount acc) {
        if (acc != null && !accounts.contains(acc)) {
            accounts.add(acc);
        }
    }

    public boolean removeAccount(String accountNumber) {
        BankAccount found = findAccountByNumber(accountNumber);
        if (found != null) {
            accounts.remove(found);
            return true;
        }
        return false;
    }

    public BankAccount findAccountByNumber(String accountNumber) {
        for (BankAccount a : accounts) {
            if (a.accountNumber.equals(accountNumber)) return a;
        }
        return null;
    }

    public double getTotalBalance() {
        double total = 0;
        for (BankAccount a : accounts) {
            total += a.getBalance();
        }
        return total;
    }

    public void printCustomerInfo() {
        System.out.println("Customer: " + customerId + " | " + name + " | Phone: " + phone + " | Email: " + email);
        System.out.println("Accounts:");
        if (accounts.isEmpty()) {
            System.out.println("  (Tidak ada akun)");
        } else {
            for (BankAccount a : accounts) {
                System.out.println("  - " + a.getAccountInfo() + " | Balance: " + String.format("%.2f", a.getBalance()) + " | Status: " + a.getAccountStatus());
            }
        }
    }
}
