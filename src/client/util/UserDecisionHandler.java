package client.util;

public class UserDecisionHandler {

    public Boolean getUserDecision(String message) {
        while (true) {
            String input = InputHandler.getStringInput(message + " (yes/no/q/quit): ");
            switch (input.toLowerCase()) {
                case "yes":
                case "y":
                    return true;
                case "no":
                case "n":
                    return false;
                case "q":
                case "quit":
                    return null;
                default:
                    System.out.println("Invalid input. Please enter 'yes', 'no', 'q', or 'quit'.");
            }
        }
    }
}
