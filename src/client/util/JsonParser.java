package client.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import client.model.DiscardedFoodItem;

public class JsonParser {
    private ObjectMapper objectMapper;

    public JsonParser() {
        this.objectMapper = new ObjectMapper();
    }

    public List<DiscardedFoodItem> parseDiscardedFoodItems(String jsonResponse) {
        try {
            return objectMapper.readValue(jsonResponse, new TypeReference<List<DiscardedFoodItem>>() {});
        } catch (IOException e) {
            System.out.println("Error parsing server response.");
            e.printStackTrace();
            return null;
        }
    }
}
