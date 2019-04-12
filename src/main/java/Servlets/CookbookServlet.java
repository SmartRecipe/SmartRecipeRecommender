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
        List<Recipe> recipes;
        Recipe recipe;
        String[] filters;
        
        Gson gson = new Gson();
        HttpSession session = request.getSession(false);
        User user = null;
        
        if (session != null)
            user = (User) session.getAttribute("user");
        String action = request.getParameter("action");
        if (action == null) action = "";
        
        switch (action) {
            case "add_recipe":
                recipe = null;
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
                recipes = RecipeDatabase.getInstance().getAllRecipes();
                sendResponse(response, STATUS_HTTP_OK, gson.toJson(recipes));
                break;
            case "search_recipes":
                if (user != null) {
                    filters = request.getParameterValues("filters");
                    
                    recipes = user.getFridge().checkAllRecipes(filters);
                    sendResponse(response, STATUS_HTTP_OK, gson.toJson(recipes));
                }
                else
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, "{ \"message\": \"Error; you are not logged in!\"");
                break;
            case "recommend_recipe":
                if (user != null) {
                    recipe = user.getFridge().recommendRecipe(user.getCookbook());
                    sendResponse(response, STATUS_HTTP_OK, gson.toJson(recipe));
                }
                else
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, "{ \"message\": \"Error; you are not logged in!\"");
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
