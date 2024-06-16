package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertObjectToJson(Object object, String path) {
        try {
            return objectMapper.writeValueAsString(new JsonRequest(path, object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class JsonRequest {
        private String path;
        private Object data;

        public JsonRequest(String path, Object data) {
            this.path = path;
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
}
