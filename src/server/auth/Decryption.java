package server.auth;

public class Decryption {

	private static final char SHIFT_KEY = 0;

	public String decrypt(String input) {
        StringBuilder decrypted = new StringBuilder();
        for (char character : input.toCharArray()) {
            char shiftedChar = character;
            if (character >= 'a' && character <= 'z') {
                shiftedChar = (char) ('a' + (character - 'a' - SHIFT_KEY + 26) % 26);
            } else if (character >= 'A' && character <= 'Z') {
                shiftedChar = (char) ('A' + (character - 'A' - SHIFT_KEY + 26) % 26);
            }
            decrypted.append(shiftedChar);
        }
        return decrypted.toString();
	}}
