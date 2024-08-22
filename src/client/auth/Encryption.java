package client.auth;

public class Encryption {
    private static final int SHIFT_KEY = 4;

    public String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        for (char character : input.toCharArray()) {
            char shiftedChar = character;
            if (character >= 'a' && character <= 'z') {
                shiftedChar = (char) ('a' + (character - 'a' + SHIFT_KEY) % 26);
            } else if (character >= 'A' && character <= 'Z') {
                shiftedChar = (char) ('A' + (character - 'A' + SHIFT_KEY) % 26);
            }
            encrypted.append(shiftedChar);
        }
        return encrypted.toString();
    }}
