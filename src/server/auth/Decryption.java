package server.auth;

public class Decryption {

	 public  String decrypt(String encryptedInput) {
		 final int SHIFT_KEY = 4;
	        StringBuilder decrypted = new StringBuilder();
	        for (char character : encryptedInput.toCharArray()) {
	            char shiftedChar = (char) (character - SHIFT_KEY);
	            decrypted.append(shiftedChar);
	        }
	        return decrypted.toString();
	    }
}
