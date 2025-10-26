import java.time.LocalDate;

public class ConstructorPractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Constructors
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk menguasai
         * constructors, constructor overloading, dan keyword this.
         */

        // ===== DEFAULT VS CUSTOM CONSTRUCTOR =====
        System.out.println("=== DEFAULT VS CUSTOM CONSTRUCTOR ===");

        // Latihan 1: Memahami default constructor
        // Buat class dengan dan tanpa custom constructor
        // Tunjukkan perbedaan penggunaan default constructor

        // Demonstrasikan penggunaan default constructor
        SimpleClass sDefault = new SimpleClass(); // menggunakan default constructor (compiler-provided)
        System.out.println("SimpleClass instance (default constructor):");
        sDefault.display();

        // ===== CONSTRUCTOR OVERLOADING =====
        System.out.println("\n=== CONSTRUCTOR OVERLOADING ===");

        // Latihan 2: Multiple constructors untuk berbagai skenario
        // Implementasikan class Product dengan multiple constructors
        // Setiap constructor untuk kasus penggunaan yang berbeda

        // Buat object Product menggunakan berbagai constructors
        Product pFull = new Product("P001", "Laptop X", "Laptop performa tinggi", 1500.0, "Electronics", 10, "SupplierA");
        Product pEssential = new Product("P002", "Mouse Y", 25.0);
        Product pCopy = new Product(pFull);
        Product pDefault = new Product();

        System.out.println("-- Produk (full) --");
        pFull.displayProductInfo();
        System.out.println("-- Produk (essential) --");
        pEssential.displayProductInfo();
        System.out.println("-- Produk (copy of full) --");
        pCopy.displayProductInfo();
        System.out.println("-- Produk (default) --");
        pDefault.displayProductInfo();

        // Mengubah stock dan apply discount
        pEssential.updateStock(50);
        pEssential.applyDiscount(10); // 10% discount
        System.out.println("-- Setelah update stock & discount untuk essential --");
        pEssential.displayProductInfo();

        // ===== KEYWORD THIS =====
        System.out.println("\n=== KEYWORD THIS ===");

        // Latihan 3: Penggunaan this dalam constructor dan methods
        // Demonstrasikan this untuk menghindari name collision
        // Gunakan this untuk memanggil constructor lain
        // Gunakan this untuk mereferensikan current object

        // Demonstrasikan berbagai penggunaan this
        Employee e1 = new Employee("EMP100", "Rina", "Sari");
        Employee e2 = new Employee("EMP101", "Agus", "Wijaya", "Finance", "Analyst", 4500.0, LocalDate.of(2018, 6, 1));
        System.out.println("Employee e1 info (constructed with minimal params):");
        System.out.println(e1.getEmployeeInfo());
        System.out.println("Employee e2 info (full constructor):");
        System.out.println(e2.getEmployeeInfo());

        // ===== CONSTRUCTOR CHAINING =====
        System.out.println("\n=== CONSTRUCTOR CHAINING ===");

        // Latihan 4: Constructor yang memanggil constructor lain
        // Implementasikan constructor chaining dengan this()
        // Tunjukkan keuntungan constructor chaining

        // Implementasikan constructor chaining
        Employee e3 = new Employee("EMP102", "Siti", "Nur", "HR");
        System.out.println("Employee e3 (chained constructor): " + e3.getEmployeeInfo());

        // beri kenaikan
        e2.giveRaise(5.0); // 5%
        System.out.println("Employee e2 after raise: " + e2.getEmployeeInfo());

        // ===== INITIALIZATION ORDER =====
        System.out.println("\n=== INITIALIZATION ORDER ===");

        // Latihan 5: Urutan inisialisasi object
        // Tunjukkan urutan eksekusi saat object dibuat
        // Instance variable initialization vs constructor

        // Demonstrasikan urutan inisialisasi
        InitializationDemo demo = new InitializationDemo();
        System.out.println("Created InitializationDemo with name = " + demo.getName());
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class SimpleClass (tanpa custom constructor)
class SimpleClass {
    // Hanya instance variables, tanpa constructor
    public int id = 100;
    public String name = "SimpleDefault";

    // No custom constructor provided -> default (no-arg) constructor used

    public void display() {
        System.out.println("id = " + id + " | name = " + name);
    }
}

// Implementasikan class Product dengan constructor overloading
class Product {
    // Instance variables
    // productId, name, description, price, category, inStock, supplier
    public String productId;
    public String name;
    public String description;
    public double price;
    public String category;
    public int inStock;
    public String supplier;

    // Constructor 1: Full parameters
    // public Product(String productId, String name, String description, double price, String category, int inStock, String supplier)
    public Product(String productId, String name, String description, double price, String category, int inStock, String supplier) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.inStock = inStock;
        this.supplier = supplier;
    }

    // Constructor 2: Essential parameters only
    // public Product(String productId, String name, double price)
    public Product(String productId, String name, double price) {
        // delegasi ke constructor penuh dengan nilai default untuk field lain
        this(productId, name, "No description", price, "General", 0, "Unknown");
    }

    // Constructor 3: Copy constructor
    // public Product(Product other)
    public Product(Product other) {
        if (other == null) {
            // default values if null
            this.productId = "UNKNOWN";
            this.name = "Unknown";
            this.description = "";
            this.price = 0.0;
            this.category = "General";
            this.inStock = 0;
            this.supplier = "Unknown";
        } else {
            this.productId = other.productId + "_COPY";
            this.name = other.name;
            this.description = other.description;
            this.price = other.price;
            this.category = other.category;
            this.inStock = other.inStock;
            this.supplier = other.supplier;
        }
    }

    // Constructor 4: Default constructor with default values
    // public Product()
    public Product() {
        this("P000", "Default Product", "Auto-generated default product", 0.0, "General", 0, "DefaultSupplier");
    }

    // Methods
    // displayProductInfo()
    public void displayProductInfo() {
        System.out.println("ProductId: " + productId + " | Name: " + name + " | Price: " + String.format("%.2f", price)
                + " | Category: " + category + " | InStock: " + inStock + " | Supplier: " + supplier);
        System.out.println("  Description: " + description);
    }

    // updateStock(int quantity)
    public void updateStock(int quantity) {
        this.inStock += quantity;
        System.out.println("Stock updated for " + productId + ". New inStock = " + inStock);
    }

    // applyDiscount(double percentage)
    public void applyDiscount(double percentage) {
        if (percentage <= 0) {
            System.out.println("Discount must be positive.");
            return;
        }
        double discount = this.price * (percentage / 100.0);
        this.price -= discount;
        System.out.println("Applied discount " + percentage + "% to " + productId + ". New price = " + String.format("%.2f", price));
    }

    // isAvailable()
    public boolean isAvailable() {
        return this.inStock > 0;
    }
}

// Implementasikan class Employee dengan constructor chaining
class Employee {
    // Instance variables
    // employeeId, firstName, lastName, department, position, salary, hireDate
    public String employeeId;
    public String firstName;
    public String lastName;
    public String department;
    public String position;
    public double salary;
    public LocalDate hireDate;

    // Constructor chaining examples
    // Buat multiple constructors yang saling memanggil dengan this()

    // Full constructor
    public Employee(String employeeId, String firstName, String lastName, String department, String position, double salary, LocalDate hireDate) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Constructor with fewer params -> chain to full constructor with defaults
    public Employee(String employeeId, String firstName, String lastName, String department) {
        this(employeeId, firstName, lastName, department, "Staff", 3000.0, LocalDate.now());
        // this(...) called: constructor chaining
    }

    // Minimal constructor -> chain to department constructor
    public Employee(String employeeId, String firstName, String lastName) {
        this(employeeId, firstName, lastName, "General");
    }

    // Methods
    // getFullName()
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // calculateYearsOfService()
    public int calculateYearsOfService() {
        if (hireDate == null) return 0;
        return java.time.Period.between(hireDate, LocalDate.now()).getYears();
    }

    // getEmployeeInfo()
    public String getEmployeeInfo() {
        return employeeId + " | " + getFullName() + " | Dept: " + department + " | Position: " + position + " | Salary: " + String.format("%.2f", salary)
                + " | HireDate: " + (hireDate != null ? hireDate.toString() : "N/A")
                + " | YearsService: " + calculateYearsOfService();
    }

    // giveRaise(double percentage)
    public void giveRaise(double percentage) {
        if (percentage <= 0) {
            System.out.println("Raise percentage must be positive.");
            return;
        }
        double increase = salary * (percentage / 100.0);
        salary += increase;
        System.out.println("Given raise of " + percentage + "% to " + employeeId + ". New salary = " + String.format("%.2f", salary));
    }
}

// Implementasikan class InitializationDemo
class InitializationDemo {
    // Tunjukkan instance variable initialization
    // Tunjukkan urutan eksekusi constructor
    // Tambahkan System.out.println di berbagai tempat untuk tracking

    private String name = initName(); // instance variable initialization

    // instance initializer block
    {
        System.out.println("Instance initializer block executed for InitializationDemo");
    }

    // default constructor
    public InitializationDemo() {
        System.out.println("Constructor of InitializationDemo executed");
    }

    private String initName() {
        System.out.println("Field initializer initName() executed");
        return "InitializedName";
    }

    public String getName() {
        return name;
    }
}