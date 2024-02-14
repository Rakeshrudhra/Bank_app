package atm.view;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import atm.controller.BankAccountController;
import atm.model.BankAccountModel;
import atm.model.UserAuth;
import atm.model.UserDashboard_data;
import atm.model.UserData;

public class LoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CORSUtility.configureCORS(response);
        CORSUtility.handlePreflight(response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CORSUtility.configureCORS(response);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            CORSUtility.handlePreflight(response);
            return;
        }

        try {
            JsonNode jsonNode = JSONHandler.parseJSON(request);

            // Extract username and password from JSON
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();

            // Create instances of BankAccountModel and UserAuth
            BankAccountModel model = new BankAccountModel();
            UserAuth auth = new UserAuth();
            UserDashboard_data   userDash=new UserDashboard_data ();
            // Pass instances to BankAccountController
           BankAccountController control = new BankAccountController(model, auth);
           BankAccountController control1 = new BankAccountController( userDash); 
            // Debugging information
            System.out.println("Received request in the servlet.");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            int  isAuthenticated = control.authenticateUser(username, password);

            // Set the content type to plain text
       

            // Debugging information
            System.out.println("Authentication result: " + isAuthenticated);

   
                if (isAuthenticated != -1) {
                    UserData userd = control1.getBankDetailsWithTransactions(isAuthenticated);

                    // Set appropriate content type for JSON
                    response.setContentType("application/json");

                    JsonMapper jsonMapper = new JsonMapper();
					// Use ObjectMapper to write JSON object directly
                    jsonMapper.writeValue(response.getWriter(), userd);
                } else {
                    response.getWriter().write("Authentication failed");
                }
            } catch (JsonProcessingException e) {
                // Handle JsonProcessingException (e.g., log or respond with an error)
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data");
            }

        
}
}
