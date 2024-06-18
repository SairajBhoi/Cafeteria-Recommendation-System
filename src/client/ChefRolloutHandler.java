package client;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ChefRolloutHandler {

    public ChefRolloutHandler() {
      
    }

    public List<ChefMenuRollout> createChefMenuRollouts() {
        List<ChefMenuRollout> rollouts = new ArrayList<>();

        int breakfastCount=0;
		try {
			breakfastCount = InputHandler.getIntegerInput("Enter number of items for Breakfast:");
		} catch (IOException e) {
			e.printStackTrace();
		}

        for (int count = 0; count < breakfastCount; count++) {
            String itemName = null;
			try {
				itemName = InputHandler.getStringInput("Breakfast" +(count + 1));
			} catch (IOException e) {
				e.printStackTrace();
			}
            ChefMenuRollout rollout = createRolloutObject(itemName, "breakfast");
            rollouts.add(rollout);
        }

        int lunchCount = 0;
		try {
			lunchCount = InputHandler.getIntegerInput("Enter number of items for Lunch:");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (int count = 0; count < lunchCount; count++) {
            String itemName = null;
			try {
				itemName = InputHandler.getStringInput("Lunch" +(count + 1));
			} catch (IOException e) {
				e.printStackTrace();
			}
            ChefMenuRollout rollout = createRolloutObject(itemName, "lunch");
            rollouts.add(rollout);
        }

        int snacksCount = 0;
		try {
			snacksCount = InputHandler.getIntegerInput("Enter number of items for Snacks:");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (int count = 0; count < snacksCount; count++) {
            String itemName = null;
			try {
				itemName = InputHandler.getStringInput("Snacks"+(count + 1));
			} catch (IOException e) {
				e.printStackTrace();
			}
            ChefMenuRollout rollout = createRolloutObject(itemName, "snacks");
            rollouts.add(rollout);
        }

        int dinnerCount = 0;
		try {
			dinnerCount = InputHandler.getIntegerInput("Enter number of items for Dinner:");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (int count = 0; count < dinnerCount; count++) {
            String itemName = null;
			try {
				itemName = InputHandler.getStringInput("Dinner"+(count + 1));
			} catch (IOException e) {
				e.printStackTrace();
			}
            ChefMenuRollout rollout = createRolloutObject(itemName, "dinner");
            rollouts.add(rollout);
        }

        return rollouts;
    }

   
    private ChefMenuRollout createRolloutObject(String itemName, String categoryName) {
        ChefMenuRollout rollout = new ChefMenuRollout();
        rollout.setRolloutDate(new Date(System.currentTimeMillis())); 
        rollout.setItemName(itemName);
        rollout.setCategoryName(categoryName);
        rollout.setNumberOfVotes(0); 
        return rollout;
    }


    public static void main(String[] args) {
        ChefRolloutHandler handler = new ChefRolloutHandler();
        List<ChefMenuRollout> rollouts = handler.createChefMenuRollouts();

       
        for (ChefMenuRollout rollout : rollouts) {
            System.out.println("ChefMenuRollout: " + rollout.getItemName() + " - Category: " + rollout.getCategoryName());
        }
    }
}
