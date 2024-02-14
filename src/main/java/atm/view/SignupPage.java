package atm.view;


import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import atm.controller.BankAccountController;
import atm.model.BankAccountModel;
import atm.model.SignUp;

import java.io.IOException;


public class SignupPage extends HttpServlet {
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

            // Extract user details from JSON
            String email = jsonNode.get("email").asText();
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();

            // Create instances of BankAccountModel and UserAuth
            BankAccountModel model = new BankAccountModel();
            SignUp sign=new SignUp();

            // Pass instances to BankAccountController
            BankAccountController control = new BankAccountController(model,sign );

            // Debugging information
            System.out.println("Received signup request in the servlet.");
            System.out.println("Email: " + email);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            // Call the controller method to handle signup
            boolean isSignupSuccessful = control.signUp(username, email, password);
           

            // Set the content type to plain text
            response.setContentType("text/plain");

            // Debugging information
            System.out.println("Signup result: " + (isSignupSuccessful ? "Success" : "Failure"));

            if (isSignupSuccessful) {
                response.getWriter().write("Signup successful");
            } else {
                response.getWriter().write("Signup failed");
            }
        } catch (IOException e) {
            // Handle IOException (e.g., log or respond with an error)
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}

