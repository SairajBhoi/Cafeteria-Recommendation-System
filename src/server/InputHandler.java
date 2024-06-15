import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {
    private static final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    private static final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    public static String getStringInput(String messageToUser) throws IOException {
        System.out.print(messageToUser);
        return bufferedReader.readLine();
    }

    public static int getIntegerInput(String messageToUser) throws IOException {
        while (true) {
            try {
                return Integer.parseInt(getStringInput(messageToUser));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.\n");
            }
        }
    }

 
    public static float getFloatInput(String messageToUser) throws IOException {
        while (true) {
            try {
                return Float.parseFloat(getStringInput(messageToUser));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid float.\n");
            }
        }
    }

    public static boolean getBooleanInput(String messageToUser) throws IOException {
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

    public static void closeInputHandler() {
        try {
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            System.err.println("Error closing input streams: " + e.getMessage());
        }
    }
}
