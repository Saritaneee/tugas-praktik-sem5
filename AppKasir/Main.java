package AppKasir;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
// Kelas abstrak MenuItem yang mengimplementasikan antarmuka Serializable
abstract class MenuItem implements Serializable { protected String nama;
protected double harga;
protected String kategori;
    // Konstruktor untuk MenuItem
    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
}
// Metode abstrak untuk menampilkan menu
    public abstract void tampilMenu();
}
// Kelas Makanan yang merupakan turunan dari MenuItem
class Makanan extends MenuItem {
    private String jenisMakanan;
    // Konstruktor untuk Makanan
    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
}
// Implementasi metode tampilMenu untuk Makanan
@Override
public void tampilMenu() {
System.out.println("Makanan: " + nama + " (" + jenisMakanan + ")"); System.out.println("Harga: Rp " + harga);
} }
// Kelas Minuman yang merupakan turunan dari MenuItem
class Minuman extends MenuItem {
                                 
     private String jenisMinuman;
    // Konstruktor untuk Minuman
    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
}
// Implementasi metode tampilMenu untuk Minuman
@Override
public void tampilMenu() {
System.out.println("Minuman: " + nama + " (" + jenisMinuman + ")"); System.out.println("Harga: Rp " + harga);
} }
// Kelas Diskon yang merupakan turunan dari MenuItem
class Diskon extends MenuItem {
    private double diskon;
    // Konstruktor untuk Diskon
    public Diskon(String nama, double harga, double diskon) {
        super(nama, harga, "Diskon");
        this.diskon = diskon;
}
// Implementasi metode tampilMenu untuk Diskon
@Override
public void tampilMenu() { System.out.println("Diskon: " + nama); System.out.println("Harga: Rp " + harga); System.out.println("Diskon: " + diskon + "%");
} }
// Kelas Menu yang merupakan wadah untuk daftar MenuItem dan mengimplementasikan Serializable
class Menu implements Serializable {
    private ArrayList<MenuItem> daftarMenu = new ArrayList<>();
// Metode untuk menambahkan MenuItem ke Menu
    public void tambahMenu(MenuItem item) {
        daftarMenu.add(item);
}
// Metode untuk menampilkan seluruh menu
    public void tampilMenu() {
        for (int i = 0; i < daftarMenu.size(); i++) {
System.out.print((i + 1) + ". ");
            daftarMenu.get(i).tampilMenu();
        }
}
// Metode untuk mendapatkan MenuItem berdasarkan indeks
    public MenuItem getItemByIndex(int indeks) {
                                          
         if (indeks >= 0 && indeks < daftarMenu.size()) {
            return daftarMenu.get(indeks);
        } else {
            return null;
} }
}
// Kelas Pesanan yang merupakan wadah untuk daftar MenuItem yang dipesan dan mengimplementasikan Serializable
class Pesanan implements Serializable {
    private ArrayList<MenuItem> pesanan = new ArrayList<>();
// Metode untuk menambahkan MenuItem ke dalam pesanan
    public void tambahPesanan(MenuItem item) {
        pesanan.add(item);
}
// Metode untuk menampilkan struk pesanan dan total biaya
    public void tampilStruk() {
        double total = 0;
        for (int i = 0; i < pesanan.size(); i++) {
System.out.print((i + 1) + ". "); pesanan.get(i).tampilMenu(); total += pesanan.get(i).harga;
}
System.out.println("Total Biaya: Rp " + total); }
}
// Kelas utama "main" yang berfungsi sebagai pengguna aplikasi restoran
public class Main {
    public static void main(String[] args) {
        //daftarMenu.add(new MenuItem("Nasi Goreng", 25000, "makanan"));
Scanner scanner = new Scanner(System.in); 
Menu menuRestoran = new Menu();
Pesanan pesananPelanggan = new Pesanan();
int pilihan;
        // Mulai loop utama program
do {
System.out.println("\n1. Tambah Item Menu"); System.out.println("2. Tampilkan Menu"); 
System.out.println("3. Pesan Menu"); System.out.println("4. Tampilkan Struk Pesanan"); 
System.out.println("5. Keluar");
System.out.print("Pilih menu: "); 
pilihan = scanner.nextInt();
            switch (pilihan) {
                case 1:
                tambahItemKeMenu(menuRestoran, scanner);
                    break;
                case 2:
                menuRestoran.tampilMenu();
                break;
                case 3:
                pesanMenu(menuRestoran, pesananPelanggan, scanner);
                break;
                case 4:
                pesananPelanggan.tampilStruk();
                break;
                case 5:
                System.out.println("Keluar dari program. Terima kasih!");
                break;
            default:
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
} while (pilihan != 5); 
// Selesai loop jika pengguna memilih keluar (menu 5)
// Simpan menu dan pesanan ke dalam file
simpanKeFile("menu.txt", menuRestoran); simpanKeFile("pesanan.txt", pesananPelanggan);
    // Muat menu dan pesanan dari file
Menu menuDiload = muatDariFileMenu("menu.txt");
Pesanan pesananDiload = muatDariFilePesanan("pesanan.txt");
// Tampilkan menu dan pesanan yang di-load dari file
if (menuDiload != null) { 
    System.out.println("\nMenu yang Di-load:"); menuDiload.tampilMenu();
}
if (pesananDiload != null) { 
    System.out.println("\nPesanan yang Di-load:"); pesananDiload.tampilStruk();
}
    scanner.close();
}

// Metode untuk menambahkan item ke dalam menu restoran
private static void tambahItemKeMenu(Menu menu, Scanner scanner) { 
    System.out.println("\n1. Tambah Makanan"); 
    System.out.println("2. Tambah Minuman"); 
    System.out.println("3. Tambah Diskon"); 
    System.out.print("Pilih jenis item: ");
    int jenisItem = scanner.nextInt();
    scanner.nextLine(); // Konsumsi newline

System.out.print("Masukkan nama item: "); String namaItem = scanner.nextLine();
System.out.print("Masukkan harga item: Rp "); double hargaItem = scanner.nextDouble(); scanner.nextLine(); // Konsumsi newline

                      
     switch (jenisItem) {
        case 1:
System.out.print("Masukkan jenis makanan: ");
String jenisMakanan = scanner.nextLine();
menu.tambahMenu(new Makanan(namaItem, hargaItem, jenisMakanan)); break;
case 2:
System.out.print("Masukkan jenis minuman: ");
String jenisMinuman = scanner.nextLine();
menu.tambahMenu(new Minuman(namaItem, hargaItem, jenisMinuman)); break;
case 3:
System.out.print("Masukkan persentase diskon: ");
double diskon = scanner.nextDouble();
menu.tambahMenu(new Diskon(namaItem, hargaItem, diskon)); break;
default:
System.out.println("Pilihan tidak valid.");
} }
// Metode untuk mengambil pesanan dari menu
private static void pesanMenu(Menu menu, Pesanan pesanan, Scanner scanner) { 
    menu.tampilMenu();
    System.out.print("Masukkan nomor item yang ingin dipesan (0 untuk selesai):");
    int nomorItem;
        do {
        //int nomorItem;
        nomorItem = scanner.nextInt();
        if (nomorItem > 0) {
            MenuItem menuItem = menu.getItemByIndex(nomorItem - 1);
            if (menuItem != null) {
                pesanan.tambahPesanan(menuItem);
            } else {
        System.out.println("Nomor item tidak valid. Silakan coba lagi."); }
        }
        }   while (nomorItem > 0);
    }
// Metode untuk menyimpan objek serializable ke dalam file
private static void simpanKeFile(String namaFile, Serializable objek) { try (ObjectOutputStream oos = new ObjectOutputStream(new
FileOutputStream(namaFile))) {
            oos.writeObject(objek);
System.out.println("Data berhasil disimpan ke " + namaFile); } catch (IOException e) {
System.out.println("Error saat menyimpan data ke " + namaFile);
            e.printStackTrace();
        }
}
    // Metode untuk memuat menu dari file
    private static Menu muatDariFileMenu(String namaFile) {
          
try (ObjectInputStream ois = new ObjectInputStream(new
FileInputStream(namaFile))) {
return (Menu) ois.readObject();
} catch (IOException | ClassNotFoundException e) {
System.out.println("Error saat memuat menu dari " + namaFile);
            return null;
        }
}
// Metode untuk memuat pesanan dari file
    private static Pesanan muatDariFilePesanan(String namaFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new
FileInputStream(namaFile))) {
            return (Pesanan) ois.readObject();
} catch (IOException | ClassNotFoundException e) { System.out.println("Error saat memuat pesanan dari " + namaFile); return null;
} }
}