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

import atm.model.CreateAccount;
import atm.model.UserData;

public class CreateAccountPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CORSUtility.configureCORS(response);
        CORSUtility.handlePreflight(response);
        System.out.println("doOptions is called");
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

        

            // Check if the "name" property exists before accessing its value
   

         // Check if the "name" property exists before accessing its value
         String name = jsonNode.has("name") ? jsonNode.get("name").asText() : null;
         String adharNumber = jsonNode.has("adharNumber") ? jsonNode.get("adharNumber").asText() : null;
         String phone = jsonNode.has("phone") ? jsonNode.get("phone").asText() : null;
         String address = jsonNode.has("address") ? jsonNode.get("address").asText() : null;
         double initialDeposit = jsonNode.has("initialDeposit") ? jsonNode.get("initialDeposit").asDouble() : 0.0;

         // Use the extracted values as needed
         System.out.println("Name: " + name);
         System.out.println("Adhar Number: " + adharNumber);
         System.out.println("Phone: " + phone);
         System.out.println("Address: " + address);
         System.out.println("Initial Deposit: " + initialDeposit);

            
            CreateAccount create = new CreateAccount();

            // Pass instances to BankAccountController
            BankAccountController control = new BankAccountController(create);

        
            // Create a new account
            UserData userd = control.addAccount(name, adharNumber,  phone, address, initialDeposit);

            // Set appropriate content type for JSON
            response.setContentType("application/json");

            JsonMapper jsonMapper = new JsonMapper();
            // Use ObjectMapper to write JSON object directly
            jsonMapper.writeValue(response.getWriter(), userd);
        } catch (JsonProcessingException e) {
            // Handle JsonProcessingException (e.g., log or respond with an error)
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data");
        }
    }
}
