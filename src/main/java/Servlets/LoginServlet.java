/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.User;
import Servlets.utils.BaseRequest;
import Servlets.utils.BaseResponse;
import Servlets.BaseServlet;
import com.google.gson.Gson;
import Databases.UserDatabase;

import java.util.UUID;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        User user;
        Gson gson = new Gson();
        String action = getAction(request);
        
        BaseRequest baseRequest;
        BaseResponse baseResponse = new BaseResponse();
        
        // try to convert the request body into an instance of BaseRequest class
        // all requests that fail to convert are malformed, we don't understand them
        try{
            String requestBody = getBody(request); // parse request body as json
            baseRequest = gson.fromJson(requestBody, BaseRequest.class);
        } catch (Exception e) {
            baseResponse.setMessage("Bad request");
            sendResponse(response, STATUS_HTTP_BAD_REQUEST, gson.toJson(baseResponse));
            return;
        }
        
        // Get the user object included in the request
        try {
            user = baseRequest.getUser();
        } catch (Exception e) {
            user = null;
        }
        
        UserDatabase userDb = UserDatabase.getInstance();
        
        switch (action) {
            case "sign_up":
                if (user == null) {
                    baseResponse.setMessage("Invalid user");
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, gson.toJson(baseResponse));
                    break;
                }
                if (user.getEmail().trim().isEmpty() || user.getPassword().trim().isEmpty()) {
                    baseResponse.setMessage("Invalid user information");
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, gson.toJson(baseResponse));
                    break;
                }
                user.setPassword(user.getPassword());
                user.setUserID(UUID.randomUUID());
                
                if (userDb.addUser(user)) {
                    baseResponse.setUser(user);
                    sendResponse(response, STATUS_HTTP_OK, gson.toJson(baseResponse));
                }
                else {
                    baseResponse.setMessage("Account associated with given email address already exists");
                    sendResponse(response, STATUS_HTTP_CONFLICT, gson.toJson(baseResponse));
                }
                break;
            case "login":
                if (user == null) {
                    baseResponse.setMessage("Invalid user");
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, gson.toJson(baseResponse));
                    break;
                }
                user = userDb.login(user.getEmail(), user.getPassword());
                if (user == null) {
                    baseResponse.setMessage("Invalid email or password");
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, gson.toJson(baseResponse));
                } else {
                    baseResponse.setUser(user);
                    sendResponse(response, STATUS_HTTP_OK, gson.toJson(baseResponse));
                }
                break;
            default:
                baseResponse.setMessage("Not found");
                sendResponse(response, STATUS_HTTP_NOT_FOUND, gson.toJson(baseResponse));
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
