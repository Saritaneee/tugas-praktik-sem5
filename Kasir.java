import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class MenuItems{
    String name;
    Double price;
    String category;

    public MenuItems(String name, Double price, String category){
        this.name = name;
        this.price = price;
        this.category = category;
    }
}

class OrderItem {
    MenuItems menuItem;
    int quantity;

    public OrderItem(MenuItems menuItem, int quantity){
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public Double orderCost(){
        return menuItem.price * quantity;
    }
}

public class Kasir{
    static ArrayList<MenuItems> menu = new ArrayList<>();
    static ArrayList<OrderItem> orderItems = new ArrayList<>();
    
    public static void main(String[] args){
        
    }
}