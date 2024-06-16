package client;

import java.util.ArrayList;
import java.util.List;

public class DailyMenuItem {
    private List<String> breakfastMenu;
    private List<String> lunchMenu;
    private List<String> snackMenu;
    private List<String> dinnerMenu;

    public DailyMenuItem() {
        this.breakfastMenu = new ArrayList<>();
        this.lunchMenu = new ArrayList<>();
        this.snackMenu = new ArrayList<>();
        this.dinnerMenu = new ArrayList<>();
    }

    public List<String> getBreakfastMenu() {
        return breakfastMenu;
    }

    public void setBreakfastMenu(List<String> breakfastMenu) {
        this.breakfastMenu = breakfastMenu;
    }

    public List<String> getLunchMenu() {
        return lunchMenu;
    }

    public void setLunchMenu(List<String> lunchMenu) {
        this.lunchMenu = lunchMenu;
    }

    public List<String> getSnackMenu() {
        return snackMenu;
    }

    public void setSnackMenu(List<String> snackMenu) {
        this.snackMenu = snackMenu;
    }

    public List<String> getDinnerMenu() {
        return dinnerMenu;
    }

    public void setDinnerMenu(List<String> dinnerMenu) {
        this.dinnerMenu = dinnerMenu;
    }

	
}
