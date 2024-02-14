package atm.view;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import atm.controller.BankAccountController;
import atm.model.Deposite;
import atm.model.GetBalance;
import atm.model.OnlineTransaction;
import atm.model.Withdrawal;


public class OnlineTransactionServlet extends HttpServlet {
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
             System.out.println("Its hit servlet");
            // Extract necessary information from JSON
            int senderId = jsonNode.get("senderAccount").asInt();
            
           int receiverId= jsonNode.get("receiverAccount").asInt();
           double amount =jsonNode.get("amount").asDouble();
            // Create instances of BankAccountModel and UserDashboard_data
            OnlineTransaction online =new OnlineTransaction();
            Deposite dep = new Deposite ();
            Withdrawal withdrawal = new Withdrawal();
            GetBalance balance=new GetBalance();
            // Pass instances to BankAccountController
            BankAccountController control = new BankAccountController(online,balance,dep,withdrawal);

            // Debugging information
            System.out.println("Received request for online transaction in the servlet.");
            System.out.println("receiverId: " + receiverId);
            System.out.println("senderId: " + senderId);
            System.out.println("Transaction Amount: " + amount);

            // Perform online transaction
        control.sendMoney(senderId,receiverId, amount);

            // Set the content type to plain text
        response.setContentType("application/json");

            if (senderId!=receiverId) {
                response.getWriter().write("Online transaction successful");
            } else {
                response.getWriter().write("Online transaction failed");
            }

        } catch (JsonProcessingException e) {
            // Handle JsonProcessingException (e.g., log or respond with an error)
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data");
        }
    }
}
