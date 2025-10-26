public class AccessModifiersPractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Access Modifiers & Encapsulation
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk menguasai
         * access modifiers, encapsulation, dan data protection.
         */

        // ===== MASALAH DENGAN PUBLIC VARIABLES =====
        System.out.println("=== MASALAH DENGAN PUBLIC VARIABLES ===");

        // Demonstrasikan masalah public variables
        BadExample bad = new BadExample("Zies", 20, 5000, "zies@mail.com");
        System.out.println("Sebelum perubahan: " + bad.name + ", " + bad.age + ", " + bad.salary + ", " + bad.email);

        // Data dapat dirusak langsung
        bad.age = -99;
        bad.salary = -2000;
        bad.email = "invalid_email";
        System.out.println("Setelah dirusak: " + bad.name + ", " + bad.age + ", " + bad.salary + ", " + bad.email);

        // ===== ENCAPSULATION SOLUTION =====
        System.out.println("\n=== ENCAPSULATION SOLUTION ===");

        GoodExample good = new GoodExample("Rina", 25, 7000, "rina@mail.com");
        System.out.println("Nama: " + good.getName());
        System.out.println("Usia: " + good.getAge());
        System.out.println("Gaji: " + good.getSalary());
        System.out.println("Email: " + good.getEmail());

        // Uji setter dengan validasi
        good.setAge(70);
        good.setEmail("bademail");
        good.setSalary(-2000);

        // ===== ACCESS MODIFIER COMBINATIONS =====
        System.out.println("\n=== ACCESS MODIFIER COMBINATIONS ===");

        AccessModifierDemo demo = new AccessModifierDemo();
        demo.testAccess();

        // ===== GETTER/SETTER BEST PRACTICES =====
        System.out.println("\n=== GETTER/SETTER BEST PRACTICES ===");

        BankAccountSecure acc = new BankAccountSecure("123456", 10000, "1234");
        acc.deposit(5000);
        acc.checkBalance("1234");
        acc.withdraw(3000, "1234");
        acc.changePin("1234", "5678");
        acc.checkBalance("5678");
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class BadExample (dengan public variables)
class BadExample {
    public String name;
    public int age;
    public double salary;
    public String email;

    public BadExample(String name, int age, double salary, String email) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.email = email;
    }

    public void showInfo() {
        System.out.println("Nama: " + name + ", Usia: " + age + ", Gaji: " + salary + ", Email: " + email);
    }
}

// Implementasikan class GoodExample (dengan encapsulation)
class GoodExample {
    private String name;
    private int age;
    private double salary;
    private String email;

    public GoodExample(String name, int age, double salary, String email) {
        setName(name);
        setAge(age);
        setSalary(salary);
        setEmail(email);
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    // Setter methods dengan validation
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
        } else {
            this.name = name;
        }
    }

    public void setAge(int age) {
        if (age < 17 || age > 65) {
            System.out.println("Usia tidak valid, harus antara 17-65.");
        } else {
            this.age = age;
        }
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            System.out.println("Gaji harus positif.");
        } else {
            this.salary = salary;
        }
    }

    public void setEmail(String email) {
        if (!validateEmail(email)) {
            System.out.println("Format email tidak valid.");
        } else {
            this.email = email;
        }
    }

    // Business methods
    private boolean validateEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public boolean isRetirementAge() {
        return age >= 60;
    }

    public double calculateTax() {
        return salary * 0.1;
    }
}

// Implementasikan class BankAccountSecure
class BankAccountSecure {
    private String accountNumber;
    private double balance;
    private String pin;
    private boolean isLocked = false;

    public BankAccountSecure(String accountNumber, double balance, String pin) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Nomor akun tidak boleh kosong.");
        }
        if (!isValidPinFormat(pin)) {
            throw new IllegalArgumentException("PIN tidak valid, harus 4 digit.");
        }
        this.accountNumber = accountNumber;
        this.balance = Math.max(balance, 0);
        this.pin = pin;
    }

    public void deposit(double amount) {
        if (isValidAmount(amount)) {
            balance += amount;
            System.out.println("Berhasil deposit: " + amount);
        }
    }

    public void withdraw(double amount, String pin) {
        if (isLocked) {
            System.out.println("Akun terkunci, transaksi ditolak.");
            return;
        }
        if (!validatePin(pin)) {
            System.out.println("PIN salah, akun dikunci!");
            lockAccount();
            return;
        }
        if (amount > balance) {
            System.out.println("Saldo tidak cukup.");
        } else {
            balance -= amount;
            System.out.println("Berhasil tarik: " + amount);
        }
    }

    public void checkBalance(String pin) {
        if (validatePin(pin)) {
            System.out.println("Saldo saat ini: " + balance);
        } else {
            System.out.println("PIN salah.");
        }
    }

    public void changePin(String oldPin, String newPin) {
        if (!validatePin(oldPin)) {
            System.out.println("PIN lama salah.");
            return;
        }
        if (!validatePin(newPin)) {
            System.out.println("PIN baru tidak valid.");
            return;
        }
        this.pin = newPin;
        System.out.println("PIN berhasil diganti.");
    }

    private boolean validatePin(String pin) {
        return pin != null && pin.equals(this.pin);
    }

    private boolean isValidPinFormat(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    private void lockAccount() {
        this.isLocked = true;
    }

    private boolean isValidAmount(double amount) {
        return amount > 0;
    }

    // Read-only
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountStatus() {
        return isLocked ? "Terkunci" : "Aktif";
    }

    // Write-only
    public void setSecurityLevel(int level) {
        System.out.println("Security level diatur ke " + level);
    }
}

// Implementasikan class AccessModifierDemo
class AccessModifierDemo {
    private String privateField = "Private";
    protected String protectedField = "Protected";
    String defaultField = "Default";
    public String publicField = "Public";

    private void privateMethod() {
        System.out.println("Private method called");
    }

    protected void protectedMethod() {
        System.out.println("Protected method called");
    }

    void defaultMethod() {
        System.out.println("Default method called");
    }

    public void publicMethod() {
        System.out.println("Public method called");
    }

    // Method untuk test accessibility dari dalam class
    public void testAccess() {
        System.out.println("Testing access within the same class...");
        System.out.println(privateField);
        System.out.println(protectedField);
        System.out.println(defaultField);
        System.out.println(publicField);

        privateMethod();
        protectedMethod();
        defaultMethod();
        publicMethod();
    }
}
