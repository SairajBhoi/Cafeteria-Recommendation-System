package client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {
    private static final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    private static final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    public static String getStringInput(String messageToUser) {
        System.out.print(messageToUser);
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input. Please try again.");
            return getStringInput(messageToUser);
        }
    }

    public static int getIntegerInput(String messageToUser) {
        while (true) {
            try {
                return Integer.parseInt(getStringInput(messageToUser));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.\n");
            }
        }
    }

    public static float getFloatInput(String messageToUser) {
        while (true) {
            try {
                return Float.parseFloat(getStringInput(messageToUser));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid float.\n");
            }
        }
    }

    public static boolean getBooleanInput(String messageToUser) {
        while (true) {
            String input = getStringInput(messageToUser + " (yes/no): ").toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.\n");
            }
        }
    }

    public static char getCharInput(String messageToUser) {
        while (true) {
            try {
                String input = getStringInput(messageToUser);
                if (input.length() == 1) {
                    return input.charAt(0);
                } else {
                    System.out.println("Please enter exactly one character.\n");
                }
            } catch (Exception e) {
                System.out.println("Error reading input. Please try again.\n");
            }
        }
    }


}
