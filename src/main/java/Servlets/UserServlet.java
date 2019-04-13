/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.Recipe;
import Beans.User;
import Databases.UserDatabase;
import Servlets.utils.BaseRequest;
import Servlets.utils.BaseResponse;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author soup
 */
@WebServlet(
        name = "UserServlet",
        urlPatterns = {"/user"}
)
public class UserServlet extends BaseServlet {
    
    public static final String info = "User Servlet";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        User user;
        
        String requestBody = getBody(request);  // parse request body as json
        String action = request != null ? request.getParameter("action") : "";
        
        BaseRequest baseRequest = gson.fromJson(requestBody, BaseRequest.class);
        
        try {
            user = baseRequest.getUser();
        } catch(Exception e) {
            user = null;
        }
        
        
        BaseResponse baseResponse = new BaseResponse();
        
        switch (action) {
            case "update":
                if (user != null) {
                    if (UserDatabase.getInstance().updateUser(user)) {
                        baseResponse.setUser(user);
                        baseResponse.setMessage("Success");
                        sendResponse(response, STATUS_HTTP_OK, gson.toJson(baseResponse));
                    }
                    else {
                        baseResponse.setUser(user);
                        baseResponse.setMessage("Error occurred while updating virtual refrigerator");
                        sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                    }
                }
                else {
                    baseResponse.setMessage("Unauthorized");
                    sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
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