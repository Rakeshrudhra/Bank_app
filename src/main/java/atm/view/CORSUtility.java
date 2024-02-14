package atm.view;



import jakarta.servlet.http.HttpServletResponse;



public class CORSUtility {

    public static void configureCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    public static void handlePreflight(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

