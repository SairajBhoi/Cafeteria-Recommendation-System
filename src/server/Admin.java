public class Admin extends User {
    private Menu() menu=null;

 public   Admin(){
        menu= new Menu();
    }
   
    public void addMenuItem() {
        menu.addMenuItem();
    }

    public void updateMenuItem() {
        menu.updateMenuItem();
    }

    public void deleteMenuItem() {
        menu.deleteMenuItem();
    }


    public List<MenuItem> getAllMenuItems() {
        return menu.getAllMenuItems();
    }

    
}
