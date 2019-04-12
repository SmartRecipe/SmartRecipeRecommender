/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.User;
import Servlets.BaseServlet;
import com.google.gson.Gson;
import Databases.UserDatabase;

import java.util.UUID;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/signon"}
)
public class LoginServlet extends BaseServlet {
    
    private static final long serialVersionUID = -5655886484095458445L;
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
        String action = request != null ? request.getParameter("action") : "";
        if (action == null)
            action = "";
        HttpSession session = request.getSession(true);
        
        User user;
        String requestBody = "";
        UserDatabase userDb = UserDatabase.getInstance();
        
        switch (action) {
            case "sign_up":
                requestBody = getBody(request);
                user = gson.fromJson(requestBody, User.class);
                if (user == null) {
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, "{ \"message\": \"Invalid user\" }");
                    break;
                }
                if (user.getEmail().trim().isEmpty() || user.getPassword().trim().isEmpty()) {
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, "{ \"message\": \"Invalid user information\" }");
                    break;
                }
                user.setPassword(user.getPassword());
                user.setUserID(UUID.randomUUID());
                
                if (userDb.addUser(user)) {
                    if (session != null)
                        session.setAttribute("user", user);
                    
                    sendResponse(response, STATUS_HTTP_OK, gson.toJson(user));
                }
                else {
                    sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, "{ \"message\": \"Error; account associated with given email address already exists.\" ");
                }
                break;
            case "login":
                requestBody = getBody(request);
                user = gson.fromJson(requestBody, User.class);
                if (user == null) {
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, "{ \"message\": \"Invalid user\" }");
                    break;
                }
                user = userDb.login(user.getEmail(), user.getPassword());
                if (user == null) {
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, "{ \"message\": \"Invalid email or password\" }");
                } else {
                    if (session != null)
                        session.setAttribute("user", user);
                    
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
