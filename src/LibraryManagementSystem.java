import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Comprehensive OOP Project
         *
         * Instruksi: Implementasikan sistem manajemen perpustakaan lengkap
         * yang menerapkan semua konsep OOP yang telah dipelajari.
         */

        // ===== SETUP LIBRARY SYSTEM =====
        System.out.println("=== LIBRARY MANAGEMENT SYSTEM ===");

        // Latihan 1: Setup library dengan berbagai tipe buku dan member
        // Buat library instance
        // Tambahkan berbagai buku
        // Daftarkan beberapa member
        Library lib = new Library("Kota Library", 5, 14); // maxBooksPerMember=5, loanPeriodDays=14

        Book b1 = new Book("9780140449136", "The Odyssey", "Homer", "Penguin Classics", 1996, BookCategory.HISTORY, 3);
        Book b2 = new Book("9780262033848", "Introduction to Algorithms", "Cormen", "MIT Press", 2009, BookCategory.SCIENCE, 2);
        Book b3 = new Book("9780307277671", "Thinking, Fast and Slow", "Kahneman", "Farrar", 2011, BookCategory.NON_FICTION, 1);
        Book b4 = new Book("9780451524935", "1984", "George Orwell", "Signet Classics", 1950, BookCategory.FICTION, 4);

        lib.addBook(b1);
        lib.addBook(b2);
        lib.addBook(b3);
        lib.addBook(b4);

        Member m1 = new Member("M001", "Alice", "alice@example.com", "08110011", "Jl. Melati", LocalDate.of(2022,1,10), MembershipType.PUBLIC);
        Member m2 = new Member("M002", "Budi", "budi@univ.edu", "08220022", "Jl. Mawar", LocalDate.of(2023,3,5), MembershipType.STUDENT);

        lib.registerMember(m1);
        lib.registerMember(m2);

        System.out.println("-- Initial Library Stats --");
        lib.displayLibraryStats();

        // ===== BOOK OPERATIONS =====
        System.out.println("\n=== BOOK OPERATIONS ===");

        // Latihan 2: Operasi buku
        // Tambah buku baru
        Book b5 = new Book("9780307743657", "Sapiens", "Yuval Noah Harari", "Harvill Secker", 2014, BookCategory.NON_FICTION, 2);
        lib.addBook(b5);
        System.out.println("Added book: " + b5.getTitle());

        // Cari buku berdasarkan kriteria
        List<Book> found = lib.searchBooks("Thinking", null, null);
        System.out.println("Search results for 'Thinking':");
        for (Book bx : found) System.out.println(" - " + bx.getTitle() + " | Available: " + bx.isAvailable());

        // Update informasi buku
        b2.setPublisher("MIT Press Updated");
        System.out.println("Updated publisher for " + b2.getTitle() + ": " + b2.getPublisher());

        // ===== MEMBER OPERATIONS =====
        System.out.println("\n=== MEMBER OPERATIONS ===");

        // Latihan 3: Operasi member
        // Registrasi member baru
        Member m3 = new Member("M003", "Citra", "citra@mail.com", "08330033", "Jl. Kenanga", LocalDate.now(), MembershipType.FACULTY);
        lib.registerMember(m3);

        // Update informasi member
        m1.setPhone("08119999");
        System.out.println("Updated phone for " + m1.getName() + ": " + m1.getPhone());

        // Cek status member
        System.out.println(m2.getName() + " can borrow more? " + m2.canBorrowMore(lib.getMaxBooksPerMember()));

        // ===== BORROWING SYSTEM =====
        System.out.println("\n=== BORROWING SYSTEM ===");

        // Latihan 4: Sistem peminjaman
        // Pinjam buku
        boolean borrowed = lib.borrowBook("M001", "9780140449136");
        System.out.println("Borrow attempt M001 -> 9780140449136: " + (borrowed ? "Success" : "Fail"));

        // Pinjam same book by another member
        lib.borrowBook("M002", "9780140449136");

        // Kembalikan buku
        // Simulasikan return after 16 days to cause fine (loan period 14)
        BorrowRecord rec = lib.getBorrowRecordForMemberAndISBN("M001", "9780140449136");
        if (rec != null) {
            // manually set borrow date to 16 days ago for testing fine
            rec.setBorrowDate(LocalDate.now().minusDays(16));
            System.out.println("Calculated fine if returned now: " + rec.calculateFine(Library.FINE_PER_DAY));
            lib.returnBook("M001", "9780140449136");
        }

        // Perpanjang peminjaman
        lib.borrowBook("M003", "9780307743657"); // citra borrows sapiens
        BorrowRecord rec2 = lib.getBorrowRecordForMemberAndISBN("M003", "9780307743657");
        if (rec2 != null) {
            boolean extended = lib.extendLoan("M003", "9780307743657", 7);
            System.out.println("Extend loan by 7 days: " + (extended ? "Extended" : "Cannot extend"));
        }

        // ===== REPORTING =====
        System.out.println("\n=== REPORTING ===");

        // Latihan 5: Sistem reporting
        // Laporan buku terpopuler
        lib.generatePopularBooksReport(3);

        // Laporan member aktif
        lib.generateActiveMembersReport();

        // Laporan denda (overdue)
        lib.generateOverdueReport();

        // Statistik perpustakaan
        lib.displayLibraryStats();

        // ===== ADMIN FUNCTIONS ===
        System.out.println("\n=== ADMIN FUNCTIONS ===");

        // Latihan 6: Fungsi admin
        lib.backup();
        lib.maintenance();

        System.out.println("\n=== END OF DEMO ===");
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class Book dengan encapsulation lengkap
class Book {
    // Private instance variables
    // isbn, title, author, publisher, yearPublished, category, isAvailable, borrowCount
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private BookCategory category;
    private int copiesAvailable;
    private int borrowCount;

    // Static variables
    // static int totalBooks
    private static int totalBooks = 0;

    // Constructors dengan overloading
    public Book(String isbn, String title, String author, String publisher, int yearPublished, BookCategory category, int copies) {
        if (!LibraryUtils.isValidISBN(isbn)) throw new IllegalArgumentException("Invalid ISBN");
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.category = category;
        this.copiesAvailable = Math.max(0, copies);
        this.borrowCount = 0;
        totalBooks++;
    }

    public Book(String isbn, String title) {
        this(isbn, title, "Unknown", "Unknown", LocalDate.now().getYear(), BookCategory.FICTION, 1);
    }

    // Getter/Setter dengan validation
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getYearPublished() { return yearPublished; }
    public BookCategory getCategory() { return category; }
    public boolean isAvailable() { return copiesAvailable > 0; }
    public int getCopiesAvailable() { return copiesAvailable; }
    public int getBorrowCount() { return borrowCount; }

    public void setTitle(String title) { if (title != null && !title.isEmpty()) this.title = title; }
    public void setAuthor(String author) { if (author != null && !author.isEmpty()) this.author = author; }
    public void setPublisher(String publisher) { if (publisher != null && !publisher.isEmpty()) this.publisher = publisher; }
    public void setYearPublished(int year) { if (year > 0) this.yearPublished = year; }
    public void setCategory(BookCategory category) { if (category != null) this.category = category; }

    // Business methods
    public boolean borrowBook() {
        if (copiesAvailable <= 0) return false;
        copiesAvailable--;
        borrowCount++;
        return true;
    }

    public boolean returnBook() {
        copiesAvailable++;
        return true;
    }

    public double getPopularityScore() {
        // simple popularity: borrowCount adjusted by age
        long age = getAgeInYears();
        return borrowCount / (1.0 + Math.log(Math.max(1, age)));
    }

    // Utility methods
    public void displayBookInfo() {
        System.out.println(title + " | ISBN: " + isbn + " | Author: " + author + " | Year: " + yearPublished
                + " | Category: " + category + " | Available: " + copiesAvailable + " | Borrowed: " + borrowCount);
    }

    public boolean isClassic() {
        return LocalDate.now().getYear() - yearPublished > 50;
    }

    public long getAgeInYears() {
        return LocalDate.now().getYear() - yearPublished;
    }

    public static int getTotalBooks() { return totalBooks; }
}

// Implementasikan class Member
class Member {
    // Private instance variables
    // memberId, name, email, phone, address, joinDate, membershipType, isActive
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate joinDate;
    private MembershipType membershipType;
    private boolean isActive;
    private List<String> borrowedIsbns;

    // Static variables
    // static int totalMembers, static final int MAX_BOOKS_ALLOWED
    private static int totalMembers = 0;
    public static final int MAX_BOOKS_ALLOWED = 10;

    // Constructors
    // Constructor dengan validation
    public Member(String memberId, String name, String email, String phone, String address, LocalDate joinDate, MembershipType type) {
        if (memberId == null || memberId.isEmpty()) throw new IllegalArgumentException("Member ID required");
        if (!LibraryUtils.isValidEmail(email)) throw new IllegalArgumentException("Invalid email");
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.joinDate = joinDate != null ? joinDate : LocalDate.now();
        this.membershipType = type != null ? type : MembershipType.PUBLIC;
        this.isActive = true;
        this.borrowedIsbns = new ArrayList<>();
        totalMembers++;
    }

    // Getter/Setter
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public LocalDate getJoinDate() { return joinDate; }
    public MembershipType getMembershipType() { return membershipType; }
    public boolean isActive() { return isActive; }
    public List<String> getBorrowedIsbns() { return borrowedIsbns; }

    public void setName(String name) { if (name != null && !name.isEmpty()) this.name = name; }
    public void setEmail(String email) { if (LibraryUtils.isValidEmail(email)) this.email = email; }
    public void setPhone(String phone) { if (phone != null && !phone.isEmpty()) this.phone = phone; }
    public void setAddress(String addr) { if (addr != null) this.address = addr; }
    public void deactivate() { this.isActive = false; }

    // Business methods
    public boolean canBorrowMore(int maxAllowed) {
        int limit = Math.min(maxAllowed, defaultLimitByMembership());
        return borrowedIsbns.size() < limit && isActive;
    }

    private int defaultLimitByMembership() {
        switch (membershipType) {
            case STUDENT: return 7;
            case FACULTY: return 12;
            default: return 5;
        }
    }

    public int calculateMembershipDuration() {
        return (int) ChronoUnit.DAYS.between(joinDate, LocalDate.now());
    }

    public void upgradeMembership(MembershipType newType) {
        if (newType != null) this.membershipType = newType;
    }

    public static int getTotalMembers() { return totalMembers; }
}

// Implementasikan class BorrowRecord
class BorrowRecord {
    // Private variables
    // recordId, memberId, isbn, borrowDate, dueDate, returnDate, fine
    private String recordId;
    private String memberId;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;

    // Static variables
    // static int totalRecords
    private static int totalRecords = 0;

    // Constructor
    // Constructor dengan auto-calculation due date
    public BorrowRecord(String memberId, String isbn, int loanPeriodDays) {
        this.recordId = "R" + (++totalRecords);
        this.memberId = memberId;
        this.isbn = isbn;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(loanPeriodDays);
        this.returnDate = null;
        this.fine = 0.0;
    }

    // Methods
    public double calculateFine(double finePerDay) {
        if (returnDate == null) {
            long overdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            if (overdue > 0) {
                return overdue * finePerDay;
            } else return 0.0;
        } else {
            long overdue = ChronoUnit.DAYS.between(dueDate, returnDate);
            if (overdue > 0) {
                return overdue * finePerDay;
            } else return 0.0;
        }
    }

    public boolean isOverdue() {
        LocalDate check = returnDate == null ? LocalDate.now() : returnDate;
        return check.isAfter(dueDate);
    }

    public void returnBook() {
        this.returnDate = LocalDate.now();
        // fine will be computed by library when needed
    }

    public boolean extendLoan(int extraDays) {
        if (returnDate != null) return false;
        this.dueDate = this.dueDate.plusDays(extraDays);
        return true;
    }

    // Getters and some setters for demo/testing
    public String getRecordId() { return recordId; }
    public String getMemberId() { return memberId; }
    public String getIsbn() { return isbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setBorrowDate(LocalDate d) { this.borrowDate = d; this.dueDate = d.plusDays(ChronoUnit.DAYS.between(borrowDate, dueDate)); } // simplistic
    public static int getTotalRecords() { return totalRecords; }
}

// Implementasikan class Library
class Library {
    // Private variables untuk collections
    // ArrayList<Book> books, ArrayList<Member> members, ArrayList<BorrowRecord> borrowRecords
    private List<Book> books;
    private List<Member> members;
    private List<BorrowRecord> borrowRecords;

    // Static variables
    // static String libraryName, static final double FINE_PER_DAY
    public static String libraryName;
    public static final double FINE_PER_DAY = 2.5; // per day

    // Private variables untuk business logic
    // maxBooksPerMember, loanPeriodDays
    private int maxBooksPerMember;
    private int loanPeriodDays;

    // Constructor
    // Initialize collections dan set library parameters
    public Library(String name, int maxBooksPerMember, int loanPeriodDays) {
        Library.libraryName = name;
        this.maxBooksPerMember = maxBooksPerMember;
        this.loanPeriodDays = loanPeriodDays;
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.borrowRecords = new ArrayList<>();
    }

    public int getMaxBooksPerMember() { return this.maxBooksPerMember; }
    public int getLoanPeriodDays() { return this.loanPeriodDays; }

    // Book management methods
    // addBook(), removeBook(), searchBooks(), getAvailableBooks()
    public void addBook(Book b) {
        if (b != null) books.add(b);
    }

    public boolean removeBook(String isbn) {
        Book found = findBookByISBN(isbn);
        if (found != null) { books.remove(found); return true; }
        return false;
    }

    public List<Book> searchBooks(String titlePart, String author, BookCategory category) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            boolean match = (titlePart == null || b.getTitle().toLowerCase().contains(titlePart.toLowerCase()))
                    && (author == null || b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    && (category == null || b.getCategory() == category);
            if (match) result.add(b);
        }
        return result;
    }

    public List<Book> getAvailableBooks() {
        List<Book> res = new ArrayList<>();
        for (Book b : books) if (b.isAvailable()) res.add(b);
        return res;
    }

    private Book findBookByISBN(String isbn) {
        for (Book b : books) if (b.getIsbn().equals(isbn)) return b;
        return null;
    }

    // Member management methods
    // registerMember(), updateMember(), getMemberById(), getActiveMembers()
    public boolean registerMember(Member m) {
        if (m == null) return false;
        if (getMemberById(m.getMemberId()) != null) return false;
        members.add(m);
        return true;
    }

    public boolean updateMember(String memberId, String phone, String address) {
        Member m = getMemberById(memberId);
        if (m == null) return false;
        m.setPhone(phone);
        m.setAddress(address);
        return true;
    }

    public Member getMemberById(String id) {
        for (Member m : members) if (m.getMemberId().equals(id)) return m;
        return null;
    }

    public List<Member> getActiveMembers() {
        List<Member> res = new ArrayList<>();
        for (Member m : members) if (m.isActive()) res.add(m);
        return res;
    }

    // Borrowing methods
    // borrowBook(), returnBook(), extendLoan(), calculateFine()
    public boolean borrowBook(String memberId, String isbn) {
        Member m = getMemberById(memberId);
        Book b = findBookByISBN(isbn);
        if (m == null || b == null) return false;
        if (!m.canBorrowMore(maxBooksPerMember)) {
            System.out.println("Member " + memberId + " cannot borrow more books.");
            return false;
        }
        if (!b.isAvailable()) {
            System.out.println("Book " + isbn + " not available.");
            return false;
        }
        boolean ok = b.borrowBook();
        if (!ok) return false;
        BorrowRecord rec = new BorrowRecord(memberId, isbn, loanPeriodDays);
        borrowRecords.add(rec);
        m.getBorrowedIsbns().add(isbn);
        System.out.println("Borrow record created: " + rec.getRecordId() + " | Due: " + rec.getDueDate());
        return true;
    }

    public boolean returnBook(String memberId, String isbn) {
        BorrowRecord record = null;
        for (BorrowRecord r : borrowRecords) {
            if (r.getMemberId().equals(memberId) && r.getIsbn().equals(isbn) && r.getReturnDate() == null) {
                record = r;
                break;
            }
        }
        if (record == null) {
            System.out.println("No active borrow record found for member/book.");
            return false;
        }
        Book b = findBookByISBN(isbn);
        Member m = getMemberById(memberId);
        if (b == null || m == null) return false;

        record.returnBook();
        b.returnBook();
        m.getBorrowedIsbns().remove(isbn);
        double fine = record.calculateFine(FINE_PER_DAY);
        if (fine > 0) {
            System.out.println("Return processed. Fine due: " + fine);
        } else {
            System.out.println("Return processed. No fine.");
        }
        return true;
    }

    public boolean extendLoan(String memberId, String isbn, int extraDays) {
        for (BorrowRecord r : borrowRecords) {
            if (r.getMemberId().equals(memberId) && r.getIsbn().equals(isbn) && r.getReturnDate() == null) {
                boolean ok = r.extendLoan(extraDays);
                if (ok) System.out.println("Loan extended. New due date: " + r.getDueDate());
                return ok;
            }
        }
        return false;
    }

    public double calculateFine(String memberId, String isbn) {
        BorrowRecord r = getBorrowRecordForMemberAndISBN(memberId, isbn);
        if (r == null) return 0.0;
        return r.calculateFine(FINE_PER_DAY);
    }

    // helper to find borrow record
    public BorrowRecord getBorrowRecordForMemberAndISBN(String memberId, String isbn) {
        for (BorrowRecord r : borrowRecords) {
            if (r.getMemberId().equals(memberId) && r.getIsbn().equals(isbn) && r.getReturnDate() == null) return r;
        }
        return null;
    }

    // Reporting methods
    // generatePopularBooksReport(), generateActiveMembersReport(), generateOverdueReport()
    public void generatePopularBooksReport(int topN) {
        System.out.println("-- Popular Books Report (top " + topN + ") --");
        List<Book> sorted = new ArrayList<>(books);
        sorted.sort(Comparator.comparingDouble(Book::getPopularityScore).reversed());
        int count = Math.min(topN, sorted.size());
        for (int i = 0; i < count; i++) {
            Book b = sorted.get(i);
            System.out.println((i+1) + ". " + b.getTitle() + " | Borrowed: " + b.getBorrowCount() + " | Score: " + String.format("%.2f", b.getPopularityScore()));
        }
    }

    public void generateActiveMembersReport() {
        System.out.println("-- Active Members Report --");
        for (Member m : getActiveMembers()) {
            System.out.println(m.getMemberId() + " | " + m.getName() + " | Borrowed: " + m.getBorrowedIsbns().size());
        }
    }

    public void generateOverdueReport() {
        System.out.println("-- Overdue Report --");
        for (BorrowRecord r : borrowRecords) {
            if (r.getReturnDate() == null && r.isOverdue()) {
                double fine = r.calculateFine(FINE_PER_DAY);
                System.out.println("Record " + r.getRecordId() + " | Member: " + r.getMemberId() + " | ISBN: " + r.getIsbn() + " | Due: " + r.getDueDate() + " | Fine: " + fine);
            }
        }
    }

    // Utility methods
    // displayLibraryStats(), backup(), maintenance()
    public void displayLibraryStats() {
        System.out.println("Library: " + libraryName + " | Books: " + Book.getTotalBooks() + " | Members: " + Member.getTotalMembers() + " | Active Borrows: " + activeBorrowCount());
    }

    public void backup() {
        System.out.println("[Admin] Backup started... (simulated)");
        // In real system we'd serialize data to disk or DB
        System.out.println("[Admin] Backup completed.");
    }

    public void maintenance() {
        System.out.println("[Admin] Running maintenance tasks...");
        // e.g., remove old records, optimize indexes
        System.out.println("[Admin] Maintenance done.");
    }

    private int activeBorrowCount() {
        int c = 0;
        for (BorrowRecord r : borrowRecords) if (r.getReturnDate() == null) c++;
        return c;
    }

    // Private helper methods
    // private boolean isValidMember(), private boolean isBookAvailable()
    private boolean isValidMember(String memberId) {
        Member m = getMemberById(memberId);
        return m != null && m.isActive();
    }

    private boolean isBookAvailableInternal(String isbn) {
        Book b = findBookByISBN(isbn);
        return b != null && b.isAvailable();
    }
}

// Implementasikan class LibraryUtils (static utility class)
class LibraryUtils {
    // Constants
    // static final variables untuk berbagai konstanta
    public static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Static utility methods
    // static boolean isValidISBN(), static boolean isValidEmail()
    // static String formatDate(), static double calculateLateFee()
    public static boolean isValidISBN(String isbn) {
        if (isbn == null) return false;
        String digits = isbn.replaceAll("-", "");
        return digits.matches("\\d{10}|\\d{13}");
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.contains("@") && email.contains(".") && email.indexOf('@') < email.lastIndexOf('.');
    }

    public static String formatDate(LocalDate d) {
        if (d == null) return "";
        return d.format(DATE_FMT);
    }

    public static double calculateLateFee(long daysLate) {
        if (daysLate <= 0) return 0.0;
        return daysLate * Library.FINE_PER_DAY;
    }

    // Private constructor
    private LibraryUtils() { }
}

// Implementasikan enum untuk BookCategory
enum BookCategory {
    FICTION,
    NON_FICTION,
    SCIENCE,
    HISTORY,
    BIOGRAPHY,
    TECHNOLOGY,
    PHILOSOPHY
}

// Implementasikan enum untuk MembershipType
enum MembershipType {
    STUDENT,
    FACULTY,
    PUBLIC
}
