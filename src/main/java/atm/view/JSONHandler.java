package atm.view;




import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import atm.model.UserData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

public class JSONHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode parseJSON(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            return objectMapper.readTree(requestBody);
        }
    }

    public static String convertUserDataToJson(UserData userData) throws IOException {
        return objectMapper.writeValueAsString(userData);
    }
}

