import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


//Make a classs for menu array
class MenuItems{
    String name;
    double price;
    String category;

    public MenuItems(String name, double price, String category){
        this.name = name;
        this.price = price;
        this.category = category;
    }
    
    public String getName() {
        return name;
    }

    public Double getPrice(){
        return price;
    }
}
    
    // Main class for restaurant
    public class Restaurant{
    public static void main (String [] args){
        ArrayList <MenuItems> menu = new ArrayList<>();
        menu.add(new MenuItems("Nasi goreng", 15000, "makanan"));
        menu.add(new MenuItems("Ketoprak", 20000, "makanan"));
        menu.add(new MenuItems("Mie goreng", 15000, "makanan"));
        menu.add(new MenuItems("Es teh", 5000, "minuman"));
        menu.add(new MenuItems("Es jeruk", 10000, "minuman"));
        menu.add(new MenuItems("Es doger", 15000, "minuman"));
    
        //Decimal format for Indonesia currency
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
    
        Scanner scanner = new Scanner(System.in);
    
        //Menampilkan pesanan
        System.out.println("Selamat datang di restoran Tane");
        System.out.println("Menu");
    
        for (MenuItems item : menu){
            System.out.println(item.name + " - " + kursIndonesia.format(item.price) + " - " + item.category);
        }
    
        //array untuk pesanan baru
        ArrayList <String> orderItems = new ArrayList<>();
        ArrayList <Integer> orderQuantity = new ArrayList<>();
        int itemCount = 0;
        double orderCost = 0;
    
        //membuat fungsi input untuk pesanan yang diinput pelanggan
        System.out.println("Masukkan pesanan anda, kosongkan bila sudah selesai");
        while (true) {
            System.out.println("Pesanan " + (itemCount + 1) + ":");
            String itemName = scanner.nextLine();
            if (itemName.isEmpty()){
                break;
            }
            
            //pengulangan untuk pesanan
            for (MenuItems item : menu){
                if(item.name.equalsIgnoreCase(itemName)){
                    orderItems.add(item.name);
                    System.out.println("Jumlah");
                    int quantity = scanner.nextInt();
                    orderQuantity.add(quantity);
                    scanner.nextLine();
                    itemCount++;
                    orderCost += item.price * quantity;
                    break;
                }
            }
        }
    
        //perhitungan total bayar pesanan
        double tax = 0.1 * orderCost;
        double fee = 20000;
        double orderPay = 0;
        double totalCost = orderCost + tax + fee;
        double discount = totalCost * 0.1;
    
        //Membuat struk pesanan
        System.out.println("Struk pesanan");
        for(int i = 0; i < orderItems.size(); i++){
            for (MenuItems item : menu){
                if(item.getName().equalsIgnoreCase(orderItems.get(i))){
                    Double itemPrice = item.getPrice() * orderQuantity.get(i);
                    System.out.println(orderItems.get(i) + " x " + orderQuantity.get(i) + " x " + kursIndonesia.format(item.getPrice()) +" = " + kursIndonesia.format(itemPrice));
                }
            }
        }

        //System.out.println(totalCost);
        System.out.println("Total pesanan: " + kursIndonesia.format(orderCost));
        System.out.println("Tax (10%): " + kursIndonesia.format(tax));
        System.out.println("Fee: " + kursIndonesia.format(fee));
        System.out.println("Total pesanan anda: " + kursIndonesia.format(totalCost));

        //lebih dari 100k diskon 10%, lebih dari 50k dapat 1 minuman
        if (totalCost >= 100000){
            System.out.println("Selamat anda mendapatkan diskon 10%: " + kursIndonesia.format(discount));
            orderPay = totalCost - discount;
            System.out.println("Total pesanan yang harus dibayar: " + kursIndonesia.format(orderPay));
        } else if (totalCost >= 50000){
            System.out.println("Selamat anda mendapatkan 1 minuman gratis");
            orderPay = totalCost;
        } 

    }
}

