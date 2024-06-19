package client.auth;

import java.io.IOException;

import client.util.InputHandler;

public class Encryption {
    private static final int SHIFT_KEY = 4;
    public static  String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        for (char character : input.toCharArray()) {
            char shiftedChar = (char) (character + SHIFT_KEY);
            encrypted.append(shiftedChar);
        }
        return encrypted.toString();

    }
    
}
   

  
