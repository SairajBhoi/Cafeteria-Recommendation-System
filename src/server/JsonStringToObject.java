package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonStringToObject {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getPath(String jsonData) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            return jsonNode.get("path").asText();
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return null;
        } catch (NullPointerException e) {
            System.err.println("Path not found in JSON: " + e.getMessage());
            return null;
        }
    }

    public String getData(String jsonData) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            JsonNode dataNode = jsonNode.get("data");

            if (dataNode == null) {
                System.err.println("No data object found in JSON");
                return "";
            }

            return dataNode.toString();
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return "";
        } catch (NullPointerException e) {
            System.err.println("Data node not found in JSON: " + e.getMessage());
            return "";
        }
    }

    public String getValueFromData(String key, String jsonData) {
        try {
            JsonNode dataNode = objectMapper.readTree(jsonData);
            JsonNode valueNode = dataNode.get(key);

            if (valueNode == null) {
                System.err.println("No value found for key: " + key);
                return "";
            }

            return valueNode.asText();
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return "";
        } catch (NullPointerException e) {
            System.err.println("Data node not found in JSON: " + e.getMessage());
            return "";
        }
    }

    public <T> T fromJsonToObject(String jsonData, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonData, valueType);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting JSON to object: " + e.getMessage());
            return null;
        }
    }
}
