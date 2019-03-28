/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.Recipe;
import Beans.User;
import Databases.RecipeDatabase;
import Databases.UserDatabase;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author soup
 */
public class LoginServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        String action = request.getParameter("action");

        User user;
        switch (action) {
            case "sign_up":
                String json = request.getParameter("user");
                user = gson.fromJson(json, User.class);
                user.setPassword(request.getParameter("password"));
                UserDatabase.addUser(user);
                request.setAttribute("user", user);
                request.setAttribute("token", UUID.randomUUID());
                request.getServletContext()
                        .getRequestDispatcher("index.html")
                        .forward(request, response);
                break;
            case "login":
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                user = UserDatabase.login(email, password);
                if (user == null) {
                    request.getServletContext()
                            .getRequestDispatcher("login.html")
                            .forward(request, response);
                } else {
                    request.setAttribute("user", user);
                    request.setAttribute("token", UUID.randomUUID());
                    request.getServletContext()
                            .getRequestDispatcher("index.html")
                            .forward(request, response);
                }
                break;
            default:
                //Shouldn't ever get here.
                request.getServletContext()
                        .getRequestDispatcher("error.html")
                        .forward(request, response);
                break;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
