package kasir;

import java.util.*;
import java.io.*;

//kelas abstrak MenuItem
abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public  abstract void tampilMenu();
}

//kelas turunan Makanan
class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Makanan: " + getNama() + ", Harga: " + getHarga() + ", Jenis: " + jenisMakanan);
    }
}

//kelas turunan Minuman
class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Minuman: " + getNama() + ", Harga: " + getHarga() + ", Jenis: " + jenisMinuman);
    }
}

//kelas turunan Diskon
class Diskon extends MenuItem {
    private double diskon;

    public Diskon(String nama, double harga, double diskon) {
        super(nama, harga, "Diskon");
        this.diskon = diskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Diskon: " + getNama() + ", Harga: " + getHarga() + ", Diskon: " + diskon + "%");
    }

    public double getDiskon() {
        return diskon;
    }
}

//kelas Menu untuk mengelola semua item menu dalam restoran
class Menu {
    private ArrayList<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        items.add(item);
    }

    public void tampilkanMenu() {
        for (MenuItem item : items) {
            item.tampilMenu();
        }
    }

    public MenuItem getItem(String nama) throws NoSuchElementException {
        for (MenuItem item : items) {
            if (item.getNama().equalsIgnoreCase(nama)) {
                return item;
            }
        }
        throw new NoSuchElementException("Item tidak ditemukan: " + nama);
    }

    public void simpanMenu(String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (MenuItem item : items) {
                writer.print(item.getKategori() + "," + item.getNama() + "," + item.getHarga());
                if (item instanceof Makanan) {
                    writer.println("," + ((Makanan) item).getJenisMakanan());
                } else if (item instanceof Minuman) {
                    writer.println("," + ((Minuman) item).getJenisMinuman());
                } else if (item instanceof Diskon) {
                    writer.println("," + ((Diskon) item).getDiskon());
                } else {
                    writer.println();
                }
            }
        }
    }

    public void muatMenu(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String kategori = data[0];
                String nama = data[1];
                double harga = Double.parseDouble(data[2]);
                if (kategori.equals("Makanan")) {
                    String jenisMakanan = data[3];
                    tambahItem(new Makanan(nama, harga, jenisMakanan));
                } else if (kategori.equals("Minuman")) {
                    String jenisMinuman = data[3];
                    tambahItem(new Minuman(nama, harga, jenisMinuman));
                } else if (kategori.equals("Diskon")) {
                    double diskon = Double.parseDouble(data[3]);
                    tambahItem(new Diskon(nama, harga, diskon));
                }
            }
        }
    }
}

//kelas Pesanan untuk mencatat pesanan pelanggan
class Pesanan {
    private class PesananItem {
        MenuItem item;
        int quantity;

        public PesananItem(MenuItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public double getTotal() {
            return item.getHarga() * quantity;
        }

        public double getTotalWithDiscount() {
            if (item instanceof Diskon) {
                Diskon diskonItem = (Diskon) item;
                return getTotal() * (1 - diskonItem.getDiskon() / 100);
            } else {
                return getTotal();
            }
        }
    }

    private ArrayList<PesananItem> items;

    public Pesanan() {
        items = new ArrayList<>();
    }

    public void tambahItem(MenuItem item, int quantity) {
        items.add(new PesananItem(item, quantity));
    }

    public double hitungTotal() {
        double total = 0;

        for (PesananItem pesananItem : items) {
            total += pesananItem.getTotalWithDiscount();
        }

        return total;
    }

    public void tampilkanStruk() {
        System.out.println("Struk Pesanan: ");

        for (PesananItem pesananItem : items) {
            double hargaTotal = pesananItem.getTotal();
            System.out.printf("%s | %.2f | %d | %.2f", pesananItem.item.getNama(), pesananItem.item.getHarga(), pesananItem.quantity, hargaTotal);
            if (pesananItem.item instanceof Diskon) {
                double hargaSetelahDiskon = pesananItem.getTotalWithDiscount();
                System.out.printf(" | Diskon %.2f%% | %.2f", ((Diskon) pesananItem.item).getDiskon(), hargaSetelahDiskon);
            }
            System.out.println();
        }

        System.out.printf("Total yang harus dibayar: %.2f\n", hitungTotal());
    }

    public void simpanStruk(String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (PesananItem pesananItem : items) {
                writer.printf("%s, %.2f, %d", pesananItem.item.getNama(), pesananItem.item.getHarga(), pesananItem.quantity);
                if (pesananItem.item instanceof Diskon) {
                    double hargaSetelahDiskon = pesananItem.getTotalWithDiscount();
                    writer.printf(",Diskon %.2f%%, %.2f", ((Diskon) pesananItem.item).getDiskon(), hargaSetelahDiskon);
                }
                writer.println();
            }
            writer.printf("Total, %.2f\n", hitungTotal());
        }
    }

    public void muatStruk(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String nama = data[0].trim();
                    double harga = Double.parseDouble(data[1].trim());
                    int quantity = Integer.parseInt(data[2].trim());
                    MenuItem item = null;

                    try {
                        item = ManajemenRestoran.menu.getItem(nama);
                    } catch (NoSuchElementException e) {
                        if (data.length == 5 && data[3].trim().startsWith("Diskon")) {
                            double diskon = Double.parseDouble(data[3].trim().replace("Diskon ", "").replace("%", ""));
                            item = new Diskon(nama, harga, diskon);
                        } else {
                            item = new MenuItem(nama, harga, "Loaded") {
                                @Override
                                public void tampilMenu() {
                                    System.out.println(nama + " - " + harga);
                                }
                            };
                        }
                    }

                    items.add(new PesananItem(item, quantity));
                }
            }
        }
    }
}

//kelas utama
public class ManajemenRestoran {
    public static Menu menu = new Menu();
    public static Pesanan pesanan = new Pesanan();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("1. Tambah item ke menu");
            System.out.println("2. Tampilkan menu");
            System.out.println("3. Buat pesanan");
            System.out.println("4. Tampilkan struk pesanan");
            System.out.println("5. Simpan menu");
            System.out.println("6. Muat menu");
            System.out.println("7. Simpan struk pesanan");
            System.out.println("8. Muat struk pesanan");
            System.out.println("9. Keluar");
            System.out.print("Pilihan: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahItemKeMenu();
                    break;
                case 2:
                    menu.tampilkanMenu();
                    break;
                case 3:
                    buatPesanan();
                    break;
                case 4:
                    pesanan.tampilkanStruk();
                    break;
                case 5:
                    simpanMenu();
                    break;
                case 6:
                    muatMenu();
                    break;
                case 7:
                    simpanStrukPesanan();
                    break;
                case 8:
                    muatStrukPesanan();
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void tambahItemKeMenu() {
        System.out.print("Masukkan nama item: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga item: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Pilih kategori:");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        int kategori = scanner.nextInt();
        scanner.nextLine();

        switch (kategori) {
            case 1:
                System.out.print("Masukkan jenis makanan: ");
                String jenisMakanan = scanner.nextLine();
                menu.tambahItem(new Makanan(nama, harga, jenisMakanan));
                break;
            case 2:
                System.out.print("MMasukkan jenis minuman: ");
                String jenisMinuman = scanner.nextLine();
                menu.tambahItem(new Minuman(nama, harga, jenisMinuman));
                break;
            case 3:
                System.out.print("Masukkan persentase diskon: ");
                double diskon = scanner.nextDouble();
                menu.tambahItem(new Diskon(nama, harga, diskon));
                break;
            default:
                System.out.println("Kategori tidak valid.");
        }
    }

    public static void buatPesanan() {
        pesanan = new Pesanan();
        boolean ordering = true;

        while (ordering) {
            System.out.print("Masukkan nama item dan jumlah (format: Nama Item = Qty) atau ketik 'selesai' untuk menyelesaikan: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("selesai")) {
                ordering = false;
            } else {
                String[] parts = input.split("=");
                if (parts.length == 2) {
                    String nama = parts[0].trim();
                    int quantity;
                    try {
                        quantity = Integer.parseInt(parts[1].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Jumlah tidak valid. Silahkan coba lagi.");
                        continue;
                    }
                    try {
                        MenuItem item = menu.getItem(nama);
                        pesanan.tambahItem(item, quantity);
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Format tidak valid. Silahkan coba lagi.");
                }
            }
        }
        pesanan.tampilkanStruk();
    }

    public static void simpanMenu() {
        System.out.print("Masukkan nama file untuk menyimpan menu: ");
        String fileName = scanner.nextLine();
        try {
            menu.simpanMenu(fileName);
            System.out.println("Menu berhasil disimpan.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan menu: " + e.getMessage());
        }
    }

    public static void muatMenu() {
        System.out.print("Masukkan nama file untuk memuat menu: ");
        String fileName = scanner.nextLine();
        try {
            menu.muatMenu(fileName);
            System.out.println("Menu berhasil dimuat.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat memuat menu: " + e.getMessage());
        }
    }

    public static void simpanStrukPesanan() {
        System.out.print("Masukkan nama file untuk menyimpan struk pesanan: ");
        String fileName = scanner.nextLine();
        try {
            pesanan.simpanStruk(fileName);
            System.out.println("Struk pesanan berhasil disimpan.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan struk pesanan: " + e.getMessage());
        }
    }

    public static void muatStrukPesanan() {
        System.out.print("Masukkan nama file untuk memuat struk pesanan: ");
        String fileName = scanner.nextLine();
        try {
            pesanan.muatStruk(fileName);
            System.out.println("Struk pesanan berhasil dimuat.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat memuat struk pesanan: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Format data di file tidak valid: " + e.getMessage());
        }
    }
}
