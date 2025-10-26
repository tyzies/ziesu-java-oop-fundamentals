import java.util.Scanner;

public class IntroToOOPPractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Introduction to OOP
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk memahami
         * perbedaan antara pemrograman prosedural dan object-oriented.
         */

        // ===== SEBELUM OOP: CARA PROSEDURAL =====
        System.out.println("=== SEBELUM OOP: CARA PROSEDURAL ===");

        // Latihan 1: Data mahasiswa dengan variabel terpisah (cara lama)
        // Buat variabel untuk menyimpan data 3 mahasiswa
        // Setiap mahasiswa memiliki: nama, nim, jurusan, ipk

        String namaMhs1 = "Andi"; // Isi dengan data mahasiswa 1
        String nimMhs1 = "A001";
        String jurusanMhs1 = "Teknik Informatika";
        double ipkMhs1 = 3.20;

        String namaMhs2 = "Budi"; // Isi dengan data mahasiswa 2
        String nimMhs2 = "A002";
        String jurusanMhs2 = "Sistem Informasi";
        double ipkMhs2 = 2.90;

        String namaMhs3 = "Cici";
        String nimMhs3 = "A003";
        String jurusanMhs3 = "Teknik Elektro";
        double ipkMhs3 = 3.60;

        // Print semua data mahasiswa menggunakan cara prosedural
        // Implementasikan printing untuk semua mahasiswa
        System.out.println("\n-- Data Mahasiswa (prosedural) --");
        System.out.println("1. " + namaMhs1 + " | " + nimMhs1 + " | " + jurusanMhs1 + " | IPK: " + ipkMhs1);
        System.out.println("2. " + namaMhs2 + " | " + nimMhs2 + " | " + jurusanMhs2 + " | IPK: " + ipkMhs2);
        System.out.println("3. " + namaMhs3 + " | " + nimMhs3 + " | " + jurusanMhs3 + " | IPK: " + ipkMhs3);

        System.out.println("\n=== MASALAH DENGAN CARA PROSEDURAL ===");
        // 1) Lebih panjang dan rumit, resiko duplikasi kode.
        // 2) Data dan fungsi tidak terikat.
        // 3) Sulit melakukan validasi dan penambahan perilaku spesifik pada tiap objek.

        // ===== DENGAN OOP: CARA OBJECT-ORIENTED =====
        System.out.println("\n=== DENGAN OOP: CARA OBJECT-ORIENTED ===");

        // Latihan 2: Menggunakan class Mahasiswa (akan dibuat di bawah)
        // Buat 3 object Mahasiswa
        // Set data untuk setiap mahasiswa
        // Print menggunakan method dari class

        // Buat object mahasiswa dan isi datanya
        Mahasiswa m1 = new Mahasiswa(namaMhs1, nimMhs1, jurusanMhs1, ipkMhs1);
        Mahasiswa m2 = new Mahasiswa(namaMhs2, nimMhs2, jurusanMhs2, ipkMhs2);
        Mahasiswa m3 = new Mahasiswa(namaMhs3, nimMhs3, jurusanMhs3, ipkMhs3);

        System.out.println("\n-- Data Mahasiswa (OOP) --");
        m1.tampilkanInfo();
        System.out.println("Lulus: " + m1.isLulus() + " | Predikat: " + m1.getPredikat());
        m2.tampilkanInfo();
        System.out.println("Lulus: " + m2.isLulus() + " | Predikat: " + m2.getPredikat());
        m3.tampilkanInfo();
        System.out.println("Lulus: " + m3.isLulus() + " | Predikat: " + m3.getPredikat());

        // ===== ANALOGI DUNIA NYATA =====
        System.out.println("\n=== ANALOGI DUNIA NYATA ===");

        // Latihan 3: Implementasi analogi perpustakaan
        // Buat beberapa object Buku
        Buku buku1 = new Buku("Pemrograman Java", "John Doe", "ISBN001", 2020, true);
        Buku buku2 = new Buku("Struktur Data", "Jane Smith", "ISBN002", 2019, true);

        // Buat object Mahasiswa yang meminjam buku
        Mahasiswa peminjam = m1;

        // Simulasikan proses peminjaman
        Perpustakaan perpusSim = new Perpustakaan();
        perpusSim.tambahBuku(buku1);
        perpusSim.tambahBuku(buku2);

        System.out.println("\n-- Simulasi Peminjaman --");
        System.out.println("Sebelum peminjaman:");
        buku1.tampilkanInfo();

        boolean berhasilPinjam = perpusSim.pinjamBuku("ISBN001");
        System.out.println("Mahasiswa " + peminjam.nama + " meminjam '" + buku1.judul + "': " + (berhasilPinjam ? "Berhasil" : "Gagal"));
        System.out.println("Setelah peminjaman:");
        buku1.tampilkanInfo();

        // Simulasikan pengembalian
        boolean berhasilKembali = perpusSim.kembalikanBuku("ISBN001");
        System.out.println("Pengembalian oleh " + peminjam.nama + ": " + (berhasilKembali ? "Berhasil" : "Gagal"));
        System.out.println("Setelah pengembalian:");
        buku1.tampilkanInfo();

        // ===== KEUNTUNGAN OOP =====
        System.out.println("\n=== KEUNTUNGAN OOP ===");
        // Tuliskan dalam komentar 5 keuntungan OOP yang Anda rasakan
        // 1) Kode dibagi menjadi kelas dan objek, sehingga lebih mudah dikelola dan dikembangkan.
        // 2) Kelas dapat digunakan kembali di program lain tanpa menulis ulang kode.
        // 3) Data dan fungsi digabungkan dalam satu kesatuan (objek), menjaga keamanan dan konsistensi data.
        // 4) Perubahan pada satu bagian tidak langsung memengaruhi bagian lain.
        // 5) Memudahkan fokus pada fungsi utama tanpa perlu tahu detail internal, serta memungkinkan satu interface digunakan untuk berbagai bentuk objek.
    }
}

// ===== DEFINISI CLASS =====

// Buat class Mahasiswa dengan struktur berikut:
class Mahasiswa {
    // Instance variables
    // Definisikan instance variables untuk nama, nim, jurusan, ipk
    public String nama;
    public String nim;
    public String jurusan;
    public double ipk;

    // Constructor
    // Buat constructor untuk inisialisasi data mahasiswa
    public Mahasiswa(String nama, String nim, String jurusan, double ipk) {
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
        this.ipk = ipk;
    }

    // Methods
    // Buat method untuk menampilkan informasi mahasiswa
    public void tampilkanInfo() {
        System.out.println("Nama: " + nama + " | NIM: " + nim + " | Jurusan: " + jurusan + " | IPK: " + ipk);
    }

    // Buat method untuk mengecek apakah mahasiswa lulus (IPK >= 2.75)
    public boolean isLulus() {
        return this.ipk >= 2.75;
    }

    // Buat method untuk menghitung predikat berdasarkan IPK
    public String getPredikat() {
        if (this.ipk >= 3.5) {
            return "Cumlaude";
        } else if (this.ipk >= 3.0) {
            return "Sangat Memuaskan";
        } else if (this.ipk >= 2.75) {
            return "Memuaskan";
        } else {
            return "Tidak Lulus";
        }
    }
}

// Buat class Buku dengan struktur berikut:
class Buku {
    // Instance variables
    // Definisikan variables untuk judul, penulis, isbn, tahunTerbit, tersedia
    public String judul;
    public String penulis;
    public String isbn;
    public int tahunTerbit;
    public boolean tersedia;

    // Constructor
    // Buat constructor
    public Buku(String judul, String penulis, String isbn, int tahunTerbit, boolean tersedia) {
        this.judul = judul;
        this.penulis = penulis;
        this.isbn = isbn;
        this.tahunTerbit = tahunTerbit;
        this.tersedia = tersedia;
    }

    // Methods
    // Buat method untuk menampilkan info buku
    public void tampilkanInfo() {
        System.out.println("Judul: " + judul + " | Penulis: " + penulis + " | ISBN: " + isbn + " | Tahun: " + tahunTerbit + " | Tersedia: " + (tersedia ? "Ya" : "Tidak"));
    }

    // Buat method untuk meminjam buku
    public boolean pinjamBuku() {
        if (tersedia) {
            tersedia = false;
            return true;
        }
        return false;
    }

    // Buat method untuk mengembalikan buku
    public boolean kembalikanBuku() {
        if (!tersedia) {
            tersedia = true;
            return true;
        }
        return false;
    }
}

// Buat class Perpustakaan yang mengelola buku dan peminjaman
class Perpustakaan {
    private java.util.ArrayList<Buku> koleksi;

    public Perpustakaan() {
        this.koleksi = new java.util.ArrayList<>();
    }

    public void tambahBuku(Buku b) {
        this.koleksi.add(b);
    }

    // Pinjam buku berdasarkan ISBN, mengembalikan true jika berhasil
    public boolean pinjamBuku(String isbn) {
        for (Buku b : koleksi) {
            if (b.isbn.equals(isbn)) {
                return b.pinjamBuku();
            }
        }
        return false;
    }

    // Kembalikan buku berdasarkan ISBN, mengembalikan true jika berhasil
    public boolean kembalikanBuku(String isbn) {
        for (Buku b : koleksi) {
            if (b.isbn.equals(isbn)) {
                return b.kembalikanBuku();
            }
        }
        return false;
    }

    public void tampilkanDaftarBuku() {
        if (koleksi.isEmpty()) {
            System.out.println("(Tidak ada buku di koleksi)");
            return;
        }
        for (Buku b : koleksi) {
            b.tampilkanInfo();
        }
    }
}
