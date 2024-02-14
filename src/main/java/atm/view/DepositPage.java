package atm.view;


import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import atm.controller.BankAccountController;
import atm.model.Deposite;


public class DepositPage extends HttpServlet {
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

            // Extract user ID and amount from JSON
            int userId = jsonNode.get("accountNumber").asInt();
            double amount = jsonNode.get("depositAmount").asDouble();

            // Create instances of BankAccountModel and UserDashboard_data
          Deposite dep = new Deposite ();
          

            // Pass instances to BankAccountController
            BankAccountController control = new BankAccountController(dep);

            // Debugging information
            System.out.println("Received deposit request in the servlet.");
            System.out.println("User ID: " + userId);
            System.out.println("Deposit Amount: " + amount);

            // Deposit money
            int x=control.deposit(userId, amount);

            // Get updated user details after deposit
           

            // Set appropriate content type for JSON
            response.setContentType("application/json");

            JsonMapper jsonMapper = new JsonMapper();
            // Use ObjectMapper to write JSON object directly
            jsonMapper.writeValue(response.getWriter(), x);
        } catch (Exception e) {
            // Handle exceptions (e.g., log or respond with an error)
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data or deposit failed");
        }
    }
}
