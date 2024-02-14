package atm.view;



import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import atm.controller.BankAccountController;
import atm.model.Withdrawal;


public class WithdrawalPage extends HttpServlet {
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
            double amount = jsonNode.get("withdrawalAmount").asDouble();

            // Create instances of Withdrawal model
            Withdrawal withdrawal = new Withdrawal();

            // Pass instances to BankAccountController
            BankAccountController control = new BankAccountController(withdrawal);

            // Debugging information
            System.out.println("Received withdrawal request in the servlet.");
            System.out.println("User ID: " + userId);
            System.out.println("Withdrawal Amount: " + amount);

            // Withdraw money
            control.withdraw(userId, amount);

            // Set appropriate content type for JSON
            response.setContentType("application/json");

            JsonMapper jsonMapper = new JsonMapper();
           int x=10;
			// Use ObjectMapper to write JSON object directly
            jsonMapper.writeValue(response.getWriter(), x);
        } catch (Exception e) {
            // Handle exceptions (e.g., log or respond with an error)
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data or withdrawal failed");
        }
    }
}

