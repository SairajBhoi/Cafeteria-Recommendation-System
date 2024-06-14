import java.util.Scanner;
public class Menu(){
    private MenuItem menuItem;
    Menu(){
        menuItem= new MenuItem();
        Scanner sc = new Scanner(System.in);
    }
   
    public void addMenuItem() {



       System.out.println("Enter the Food Item Name:"):
       String itemName=sc.nextLine();


       System.out.println("Enter the Food Item price :"):
       float itemPrice=sc.nextFloat();

       System.out.println("Enter Food Availablity : 0-for not availabe 1-for available");
       boolean available= (sc.nextInt()==1);
       

       System.out.println("Enter Food Category  : 1-breakfast 2-for lunch 3- snacks 4-dinner");
         int Category= sc.nextInt();


        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);
        menuItem.setItemAvailable(available);
        menuItem.setItemCategory();
    }

    public void updateMenuItem() {
      
    }

    public void deleteMenuItem() {
      
    }


    public List<MenuItem> getAllMenuItems() {
       
    }

}