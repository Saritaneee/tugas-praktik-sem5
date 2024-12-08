import java.util.ArrayList;
import java.util.Scanner;

class MenuItem {
    String name;
    double price;
    String category;

    public MenuItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
    
class Order {
    ArrayList<MenuItem> orderedItems = new ArrayList<>();
    ArrayList<Integer> orderedQuantities = new ArrayList<>();

    public void addOrder(MenuItem item, int quantity) {
        orderedItems.add(item);
        orderedQuantities.add(quantity);
    }

    public double calculateTotal() {
        double subtotal = 0;
        double discount = 0;

        // Menghitung subtotal
        for (int i = 0; i < orderedItems.size(); i++) {
            MenuItem item = orderedItems.get(i);
            int quantity = orderedQuantities.get(i);
            subtotal += item.price * quantity;
        }

        // Menentukan diskon 10% jika subtotal lebih dari Rp 100.000
        if (subtotal > 100000) {
             discount = 0.10; // Diskon 10%
        }

        return subtotal * (1 - discount);
    }

    public void printReceipt(double taxRate, double serviceCharge) {
        System.out.println("\n======== STRUK PEMBAYARAN ========");
        double subtotal = 0;

        // Menampilkan item yang dipesan dan menghitung subtotal
        for (int i = 0; i < orderedItems.size(); i++) {
            MenuItem item = orderedItems.get(i);
            int quantity = orderedQuantities.get(i);
            double itemTotal = item.price * quantity;
            subtotal += itemTotal;

            System.out.printf("%-20s x%d - Rp %.2f\n", item.name, quantity, itemTotal);
        }

        // Menampilkan pesan promo jika subtotal lebih dari Rp 50.000
        if (subtotal > 50000) {
            System.out.println("Selamat Anda Mendapatkan Promo Beli Satu Gratis Satu Untuk Salah Satu Kategori Minuman");
        }

        // Menampilkan subtotal dan biaya tambahan
        System.out.printf("Subtotal: Rp %.2f\n", subtotal);
        double tax = subtotal * taxRate;
        System.out.printf("Pajak (%.0f%%): Rp %.2f\n", taxRate * 100, tax);
        System.out.printf("Biaya Pelayanan: Rp %.2f\n", serviceCharge);

        double total = subtotal + tax + serviceCharge;

        // Menambahkan diskon jika ada
        double discount = 0;
        if (subtotal > 100000) {
            discount = 0.10;
            System.out.printf("Diskon (10%%): -Rp %.2f\n", subtotal *discount);
            total -= subtotal * discount;
        }

        // Menampilkan total biaya
        System.out.printf("Total Biaya: Rp %.2f\n", total);
        System.out.println("==================================="); 
    }
}

public class restoranapp2 {
    static ArrayList<MenuItem> menu = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        menu.add(new MenuItem("Nasi Goreng", 25000, "makanan"));
        menu.add(new MenuItem("Mie Kwetiau", 25000, "makanan"));
        menu.add(new MenuItem("Ayam Bakar", 30000, "makanan"));
        menu.add(new MenuItem("Bakso Iga", 35000, "makanan"));
        menu.add(new MenuItem("Es Teh Manis", 5000, "minuman"));
        menu.add(new MenuItem("Jus Jeruk", 10000, "minuman"));
        menu.add(new MenuItem("Jus Alpukat", 15000, "minuman"));
        menu.add(new MenuItem("Es Buah", 20000, "minuman"));
        
        while (true) {
            System.out.println("\n==== APLIKASI RESTORAN ====");
            System.out.println("1. Menu Pemesanan");
            System.out.println("2. Pengelolaan Menu");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                orderMenu();
            } else if (choice == 2) {
                manageMenu();
            } else if (choice == 3) {
                System.out.println("Terima kasih telah menggunakan aplikasi restoran!");
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void orderMenu() {
        Order order = new Order();
        System.out.println("\n==== MENU RESTORAN ====");
        displayMenu();

        while (true) {
            System.out.print("Masukkan nomor menu yang ingin dipesan (atau ketik 'selesai' untuk selesai): ");
            String input = scanner.next();

            if (input.equalsIgnoreCase("selesai")) break;

            try {
                int menuIndex = Integer.parseInt(input) - 1;
                if (menuIndex >= 0 && menuIndex < menu.size()) {
                    System.out.print("Masukkan jumlah: ");
                    int quantity = scanner.nextInt();
                    order.addOrder(menu.get(menuIndex), quantity);
                } else {
                    System.out.println("Menu tidak valid. Silahkan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Silahkan coba lagi.");
            }
        }

        double taxRate = 0.10;
        double serviceCharge = 20000;
        order.printReceipt(taxRate, serviceCharge);
    }

    public static void manageMenu() {
        while (true) {
            System.out.println("\n==== PENGELOLAAN MENU ====");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            
            if (choice == 1) {
                addMenuItem();
            } else if (choice == 2) {
                updateMenuItem();
            } else if (choice == 3) {
                deleteMenuItem();
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void addMenuItem() {
        System.out.print("Nama Menu: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Harga: ");
        double price = scanner.nextDouble();
        System.out.print("Kategori (makanan/minuman): ");
        String category = scanner.next();

        menu.add(new MenuItem(name, price, category));
        System.out.println("Menu berhasil ditambahkan.");
    }

    public static void updateMenuItem() {
        displayMenu();
        System.out.print("Pilih nomor menu untuk diubah: ");
        int menuIndex = scanner.nextInt() - 1;

        if (menuIndex >= 0&& menuIndex < menu.size()) {
            System.out.print("Harga baru: ");
            double newPrice = scanner.nextDouble();
            menu.get(menuIndex).price = newPrice;
            System.out.println("Harga berhasil diubah.");
        } else {
            System.out.println("Menu tidak valid.");
        }
    }

    public static void deleteMenuItem() {
        displayMenu();
        System.out.print("Pilih nomor menu untuk dihapus: ");
        int menuIndex = scanner.nextInt() - 1;

        if (menuIndex >= 0 && menuIndex < menu.size()) {
            System.out.print("Anda yakin ingin menghapus? (Ya/Tidak): ");
            String confirmation = scanner.next();

            if (confirmation.equalsIgnoreCase("Ya")) {
                menu.remove(menuIndex);
                System.out.println("Menu berhasil dihapus.");
            } else {
                System.out.println("Penghapusan dibatalkan");
            }
        } else {
            System.out.println("Menu tidak valid.");
        }
    }

    public static void displayMenu() {
        for (int i = 0; i < menu.size(); i ++) {
            MenuItem item = menu.get(i);
            System.out.printf("%d. %s - Rp %.2f (%s)\n", i + 1, item.name, item.price, item.category);
        }
        
    }
}