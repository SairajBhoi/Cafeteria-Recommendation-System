package server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertObjectToJson(String path,Object object) {
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
    
    public static String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
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
