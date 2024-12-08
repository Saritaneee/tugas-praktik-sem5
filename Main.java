import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//Kelas abstrak MenuItem
abstract class MenuItem implements Serializable {protected String name; protected double price; protected String category;
    public MenuItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    //mode abstract untuk cetak menu
    public abstract void displayMenu();
}

//kelas makanan yang merupakan turunan dari menuitem
class Makanan extends MenuItem{
    private String jenisMakanan;
    public Makanan(String name, double price, String jenisMakanan){
        super(name, price, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    //implementasi metode display menu
    @Override
    public void displayMenu(){
    System.out.println("Makanan: " + name + "Harga" + price + "Kategori" + " (" + jenisMakanan + ")");
}
}

//kelas minuman yang merupakan turunan dari menuitem
class Minuman extends MenuItem {      
    private String jenisMinuman;
   // Konstruktor untuk Minuman
    public Minuman(String nama, double harga, String jenisMinuman) {
       super(nama, harga, "Minuman");
       this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void displayMenu(){
        System.out.println("Minuman: " + name + "Harga" + price + "Kategori" + " (" + jenisMinuman + ")");
    }
}

//kelas diskon turunan dari menuitem
class Diskon extends MenuItem {
    private double diskon;
    public Diskon(String nama, double harga, double diskon) {
        super(nama, harga, "Diskon");
        this.diskon = diskon;
    }
// Implementasi metode tampilMenu untuk Diskon
    @Override
    public void displayMenu() { 
    System.out.println("Diskon: " + name); 
    System.out.println("Harga: Rp " + price); 
    System.out.println("Diskon: " + diskon + "%");
    } 
}

class Menu implements Serializable{
    private ArrayList<MenuItem> daftarMenu = new ArrayList<>();
    public void addMenuItem (MenuItem item){
        daftarMenu.add(item);
    }

    //Metode untuk menampilkan semua menu
    public void displayMenu(){
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.print((i + 1) + " .");
            daftarMenu.get(i).displayMenu();
        }
    }
    //metode untuk mendapatkan menu berdasar indeks
    public MenuItem getItemByIndex(int indeks){
        if (indeks >= 0 && indeks < daftarMenu.size()) {
            return daftarMenu.get(indeks);
        } else {
            return null;
        }
    }
}

//kelas pesanan untuk pesan menu
class Order implements Serializable{
    private ArrayList<MenuItem> orderItem = new ArrayList<>();

    //Metode untuk menambahkan pesanan
    public void addNewOrder(MenuItem item){
        orderItem.add(item);
    }

    public void printReceipt(){
        double total = 0;
        for (int i = 0; i < orderItem.size(); i++) {
        System.out.print((i + 1) + ". "); orderItem.get(i).displayMenu(); total += orderItem.get(i).price;
        }
        System.out.println("Total Biaya: Rp " + total);
    }
}

//Kelas utama main
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menuRestoran = new Menu();
        Order orderedItems = new Order();

        int choice;

        do {
            System.out.println("\n1. Tambah Item Menu"); System.out.println("2. Tampilkan Menu"); 
            System.out.println("3. Pesan Menu"); System.out.println("4. Tampilkan Struk Pesanan"); 
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: "); 
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                addMenuItem(menuRestoran, scanner);
                break;
                case 2:
                menuRestoran.displayMenu();
                break;
                case 3:
                orderItem(menuRestoran, orderedItems, scanner);
                break;
                case 4:
                orderedItems.printReceipt();
                break;
                case 5:
                System.out.println("Keluar dari program. Terima kasih!");
                break;
            default:
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (choice != 5);
    
        //Simpan menu pesanan dalam file
        saveToFile("menu.txt", menuRestoran);
        saveToFile("order.txt", orderedItems);
    
        // Muat menu dan pesanan dari file
        Menu menuDiload = muatDariFileMenu("menu.txt");
        Order pesananDiload = muatDariFilePesanan("pesanan.txt");
        
        // Tampilkan menu dan pesanan yang di-load dari file
        if (menuDiload != null) { 
            System.out.println("\nMenu yang Di-load:"); menuDiload.displayMenu();
        }
        if (pesananDiload != null) { 
            System.out.println("\nPesanan yang Di-load:"); pesananDiload.printReceipt();
        }
        scanner.close();
    }
        
    private static void addMenuItem(Menu menu, Scanner scanner) { 
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
            menu.addMenuItem(new Makanan(namaItem, hargaItem, jenisMakanan)); break;
        case 2:
            System.out.print("Masukkan jenis minuman: ");
            String jenisMinuman = scanner.nextLine();
            menu.addMenuItem(new Minuman(namaItem, hargaItem, jenisMinuman)); break;
        case 3:
            System.out.print("Masukkan persentase diskon: ");
            double diskon = scanner.nextDouble();
            menu.addMenuItem(new Diskon(namaItem, hargaItem, diskon)); break;
        default:
        System.out.println("Pilihan tidak valid.");
        } 
    }

    private static void orderItem(Menu menu, Order order, Scanner scanner) { 
        menu.displayMenu();
        System.out.print("Masukkan nomor item yang ingin dipesan (0 untuk selesai):");
        int nomorItem;
            do {
                //int nomorItem;
                nomorItem = scanner.nextInt();
                if (nomorItem > 0) {
                    MenuItem menuItem = menu.getItemByIndex(nomorItem - 1);
                    if (menuItem != null) {
                        order.addNewOrder(menuItem);
                    } else {
                        System.out.println("Nomor item tidak valid. Silakan coba lagi."); 
                    }
                }
            } while (nomorItem > 0);
    }

    // Metode untuk menyimpan objek serializable ke dalam file
    private static void saveToFile(String namaFile, Serializable objek) { 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(namaFile))) {
            oos.writeObject(objek);
                System.out.println("Data berhasil disimpan ke " + namaFile); 
        } catch (IOException e) {
            System.out.println("Error saat menyimpan data ke " + namaFile);
            e.printStackTrace();
        }
    }

    // Metode untuk memuat menu dari file
    private static Menu muatDariFileMenu(String namaFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(namaFile))) {
            return (Menu) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error saat memuat menu dari " + namaFile);
            return null;
        }
    }

    // Metode untuk memuat pesanan dari file
    private static Order muatDariFilePesanan(String namaFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(namaFile))) {
            return (Order) ois.readObject();
        } catch (IOException | ClassNotFoundException e) { 
            System.out.println("Error saat memuat pesanan dari " + namaFile); return null;
        } 
    }


}