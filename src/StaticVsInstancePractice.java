public class StaticVsInstancePractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Static vs Instance
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk menguasai
         * perbedaan static dan instance members, kapan menggunakan masing-masing.
         */

        // ===== STATIC VARIABLES DEMO =====
        System.out.println("=== STATIC VARIABLES DEMO ===");

        // Latihan 1: Static variables sebagai shared data
        // Buat beberapa object dari class yang memiliki static counter
        // Tunjukkan bahwa static variable di-share oleh semua object

        // Demonstrasikan static variable sharing
        Counter c1 = new Counter("Counter A");
        Counter c2 = new Counter("Counter B");
        Counter c3 = new Counter("Counter C");

        c1.displayCounterInfo();
        c2.displayCounterInfo();
        c3.displayCounterInfo();

        System.out.println("Total globalCount (shared): " + Counter.getGlobalCount());

        // ===== STATIC METHODS DEMO =====
        System.out.println("\n=== STATIC METHODS DEMO ===");

        // Latihan 2: Static methods sebagai utility functions
        // Implementasikan utility methods yang tidak butuh object
        // Tunjukkan cara memanggil static methods

        // Demonstrasikan static methods usage
        double area = MathUtils.calculateCircleArea(5);
        System.out.println("Area of circle (r=5): " + area);

        double distance = MathUtils.calculateDistance(0, 0, 3, 4);
        System.out.println("Distance (0,0) to (3,4): " + distance);

        System.out.println("5! = " + MathUtils.factorial(5));
        System.out.println("2^8 = " + MathUtils.power(2, 8));
        System.out.println("Is 17 prime? " + MathUtils.isPrime(17));

        // ===== STATIC VS INSTANCE COMPARISON =====
        System.out.println("\n=== STATIC VS INSTANCE COMPARISON ===");

        // Latihan 3: Perbandingan langsung static vs instance
        // Tunjukkan memory usage difference
        // Performance comparison

        // Implementasikan perbandingan
        Counter test1 = new Counter("TestCounter1");
        Counter test2 = new Counter("TestCounter2");

        test1.incrementInstance();
        test2.incrementInstance();
        System.out.println("Static globalCount shared: " + Counter.getGlobalCount());
        System.out.println("Instance count berbeda untuk tiap object: " + test1.getInstanceCount() + ", " + test2.getInstanceCount());

        // ===== STATIC INITIALIZATION =====
        System.out.println("\n=== STATIC INITIALIZATION ===");

        // Latihan 4: Static initialization blocks
        // Tunjukkan kapan static variables diinisialisasi
        // Static blocks vs static variable initialization

        // Demonstrasikan static initialization
        DatabaseConnection conn1 = DatabaseConnection.getConnection();
        conn1.connect();
        conn1.executeQuery("SELECT * FROM users");
        conn1.disconnect();

        System.out.println("Active connections: " + DatabaseConnection.getActiveConnectionCount());
        DatabaseConnection.closeAllConnections();

        // ===== BEST PRACTICES =====
        System.out.println("\n=== BEST PRACTICES ===");

        // Latihan 5: Kapan menggunakan static vs instance
        // Constants (static final)
        // Utility methods (static)
        // Counters/global state (static)
        // Object-specific data (instance)

        // Implementasikan best practices examples
        Student.setUniversityName("Tech University");

        Student s1 = new Student("S001", "Alice", "Computer Science", 3.8);
        Student s2 = new Student("S002", "Bob", "Information Systems", 3.2);

        s1.displayStudentInfo();
        s2.displayStudentInfo();

        System.out.println("Total Students: " + Student.getTotalStudents());
        System.out.println("University Name: " + Student.getUniversityName());
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class Counter dengan static dan instance counters
class Counter {
    // Static variables
    static int globalCount = 0;
    static final String APP_NAME = "CounterApp";

    // Instance variables
    int instanceCount = 0;
    String counterName;

    // Static initialization block
    static {
        System.out.println("[Static Block] Counter class initialized. APP_NAME = " + APP_NAME);
    }

    // Constructor
    Counter(String name) {
        this.counterName = name;
        globalCount++;
        instanceCount++;
    }

    // Static methods
    static int getGlobalCount() {
        return globalCount;
    }

    static void resetGlobalCount() {
        globalCount = 0;
    }

    static void displayAppInfo() {
        System.out.println("Application: " + APP_NAME + ", Global Count: " + globalCount);
    }

    // Instance methods
    int getInstanceCount() {
        return instanceCount;
    }

    void incrementInstance() {
        instanceCount++;
    }

    void displayCounterInfo() {
        System.out.println(counterName + " - instanceCount: " + instanceCount + " | globalCount: " + globalCount);
    }
}

// Implementasikan class MathUtils dengan static utility methods
class MathUtils {
    // Constants
    static final double PI = 3.14159;
    static final double E = 2.71828;

    // Static utility methods
    static double calculateCircleArea(double radius) {
        return PI * radius * radius;
    }

    static double calculateDistance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    static boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) result *= i;
        return result;
    }

    static double power(double base, int exponent) {
        double result = 1;
        for (int i = 0; i < exponent; i++) result *= base;
        return result;
    }

    // Private constructor (utility class)
    private MathUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }
}

// Implementasikan class DatabaseConnection dengan static connection pool
class DatabaseConnection {
    // Static variables untuk connection pool
    static int maxConnections = 3;
    static int activeConnections = 0;
    static boolean isInitialized;

    // Instance variables
    String connectionId;
    boolean isConnected;
    String database;

    // Static initialization
    static {
        isInitialized = true;
        System.out.println("[Static Block] DatabaseConnection initialized with maxConnections = " + maxConnections);
    }

    // Constructor
    private DatabaseConnection(String id) {
        this.connectionId = id;
        this.isConnected = false;
        this.database = "DefaultDB";
    }

    // Static methods untuk connection management
    static DatabaseConnection getConnection() {
        if (activeConnections < maxConnections) {
            activeConnections++;
            return new DatabaseConnection("Conn-" + activeConnections);
        } else {
            System.out.println("No available connections!");
            return null;
        }
    }

    static void closeAllConnections() {
        activeConnections = 0;
        System.out.println("All connections closed.");
    }

    static int getActiveConnectionCount() {
        return activeConnections;
    }

    // Instance methods
    void connect() {
        if (!isConnected) {
            isConnected = true;
            System.out.println(connectionId + " connected to " + database);
        }
    }

    void disconnect() {
        if (isConnected) {
            isConnected = false;
            activeConnections--;
            System.out.println(connectionId + " disconnected.");
        }
    }

    void executeQuery(String query) {
        if (isConnected) {
            System.out.println(connectionId + " executing query: " + query);
        } else {
            System.out.println(connectionId + " is not connected!");
        }
    }
}

// Implementasikan class Student dengan mixed static/instance
class Student {
    // Static variables
    static String universityName;
    static int totalStudents = 0;

    // Instance variables
    String studentId, name, major;
    double gpa;

    // Constructor
    Student(String id, String name, String major, double gpa) {
        this.studentId = id;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
        totalStudents++;
    }

    // Static methods
    static int getTotalStudents() {
        return totalStudents;
    }

    static void setUniversityName(String name) {
        universityName = name;
    }

    static String getUniversityName() {
        return universityName;
    }

    // Instance methods
    void updateGPA(double newGPA) {
        this.gpa = newGPA;
    }

    void displayStudentInfo() {
        System.out.println("[" + universityName + "] " + name + " (" + studentId + ") - " + major + " | GPA: " + gpa);
    }

    boolean isHonorStudent() {
        return gpa >= 3.5;
    }
}
