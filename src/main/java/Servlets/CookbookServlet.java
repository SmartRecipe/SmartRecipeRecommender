/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.Recipe;
import Beans.User;
import Databases.RecipeDatabase;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author soup
 */
@WebServlet(
        name = "CookbookServlet",
        urlPatterns = {"/cookbook"}
    )
public class CookbookServlet extends BaseServlet {
    
    public static final String info = "Cookbook Servlet";
	
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
        String action = request.getParameter("action");
        if (action == null) action = "";
        
        switch (action) {
            case "add_recipe":
                Recipe recipe = null;
                try {
                    recipe = gson.fromJson(request.getParameter("recipe"), Recipe.class);
                } catch (Exception e) {
                    sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, "{ \"message\": \"Error; please try again later.\" }");
                    break;
                }
                RecipeDatabase.getInstance().addRecipe(recipe);
                sendResponse(response, STATUS_HTTP_OK, "{ \"message\": \"Recipe added!\" }");
                break;
            case "get_recipes":
                List<Recipe> recipes = RecipeDatabase.getInstance().getAllRecipes();
                sendResponse(response, STATUS_HTTP_OK, gson.toJson(recipes));
                break;
            case "search_recipes":
                HttpSession session = request.getSession(false);
                
                User user = (User) session.getAttribute("user");
                
                List<Recipe> searchRecipes = user.getFridge().checkAllRecipes();
                sendResponse(response, STATUS_HTTP_OK, gson.toJson(searchRecipes));
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
