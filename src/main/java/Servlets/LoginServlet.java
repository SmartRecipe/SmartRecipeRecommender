/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.User;
import Beans.Recipe;
import Servlets.BaseServlet;
import Databases.RecipeDatabase;

import java.util.List;
import com.google.gson.Gson;
import Databases.UserDatabase;

import java.util.UUID;
import java.io.IOException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/signon"}
    )
public class LoginServlet extends BaseServlet {
    
    public static final String info = "Login Servlet";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        String action = request.getParameter("action");

        User user;
        String requestBody = "";
        UserDatabase userDb = UserDatabase.getInstance();
        
        switch (action) {
            case "sign_up":
                requestBody = getBody(request);
                user = gson.fromJson(requestBody, User.class);
                user.setPassword(user.getPassword());
                user.setUserID(UUID.randomUUID());
                userDb.addUser(user);
                sendResponse(response, STATUS_HTTP_OK, gson.toJson(user));
                break;
            case "login":
                requestBody = getBody(request);
                user = gson.fromJson(requestBody, User.class);
                user = userDb.login(user.getEmail(), user.getPassword());
                if (user == null) {
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, "{ \"message\": \"Invalid email or password\" }");
                } else {
                    sendResponse(response, STATUS_HTTP_OK, gson.toJson(user));
                }
                break;
            default:
                sendResponse(response, STATUS_HTTP_NOT_FOUND, "{ \"message\": \"Not Found\" }");
                break;
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return info;
    }
    
}
