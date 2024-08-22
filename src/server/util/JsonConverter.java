package server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertObjectToJson(String path, Object object) {
        try {
            return objectMapper.writeValueAsString(new JsonRequest(path, object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

       public static String convertStatusAndMessageToJson(String status, String message) {
        try {
            return objectMapper.writeValueAsString(new JsonResponse(status, message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String createJson(String key, Object value) {
        try {
            ObjectNode objectNode = objectMapper.createObjectNode();
            addKeyValue(objectNode, key, value);
            return objectMapper.writeValueAsString(objectNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   
    public static String addKeyValueToJson(String jsonString, String key, Object value) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                addKeyValue(objectNode, key, value);
                return objectMapper.writeValueAsString(objectNode);
            } else {
                throw new IllegalArgumentException("Provided JSON string is not an object.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   
    public static String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

   
    private static void addKeyValue(ObjectNode objectNode, String key, Object value) {
        objectNode.putPOJO(key, value);
    }

  
    public static class JsonRequest {
        private String path;
        private Object data;

        public JsonRequest(String path, Object data) {
            this.path = path;
            this.data = data;
        }

        public JsonRequest(Object data) {
            this.data = data;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    public static class JsonResponse {
        private String status;
        private String message;

        public JsonResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
