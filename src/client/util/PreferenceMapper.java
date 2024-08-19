package client.util;



public class PreferenceMapper {

    public static String getDietaryPreference() {
        int dietaryInput;
        String dietaryPreference;
        do {
            dietaryInput = InputHandler.getIntegerInput("Enter dietary preference (1 - Vegetarian, 2 - Non-Vegetarian, 3 - Eggetarian): ");
            dietaryPreference = mapDietaryPreference(dietaryInput);
            if (dietaryPreference == null) {
                System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
            }
        } while (dietaryPreference == null);
        return dietaryPreference;
    }

    public static String getSpiceLevel() {
        int spiceInput;
        String spiceLevel;
        do {
            spiceInput = InputHandler.getIntegerInput("Enter preferred spice level (1 - Low, 2 - Medium, 3 - High): ");
            spiceLevel = mapSpiceLevel(spiceInput);
            if (spiceLevel == null) {
                System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
            }
        } while (spiceLevel == null);
        return spiceLevel;
    }

    public static String getCuisine() {
        int cuisineInput;
        String cuisine;
        do {
            cuisineInput = InputHandler.getIntegerInput("Enter preferred cuisine (1 - North Indian, 2 - South Indian, 3 - Other): ");
            cuisine = mapCuisine(cuisineInput);
            if (cuisine == null) {
                System.out.println("Invalid input. Please enter a valid integer (1, 2, 3).");
            }
        } while (cuisine == null);
        return cuisine;
    }

    private static String mapDietaryPreference(int dietaryInput) {
        switch (dietaryInput) {
            case 1:
                return "Vegetarian";
            case 2:
                return "Non-Vegetarian";
            case 3:
                return "Eggetarian";
            default:
                return null;
        }
    }

    private static String mapSpiceLevel(int spiceInput) {
        switch (spiceInput) {
            case 1:
                return "Low";
            case 2:
                return "Medium";
            case 3:
                return "High";
            default:
                return null;
        }
    }

    private static String mapCuisine(int cuisineInput) {
        switch (cuisineInput) {
            case 1:
                return "North Indian";
            case 2:
                return "South Indian";
            case 3:
                return "Other";
            default:
                return null;
        }
    }
}
