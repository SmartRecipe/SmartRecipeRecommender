/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.Recipe;
import Databases.RecipeDatabase;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

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
                    //TODO handle this correctly
                    request.getServletContext().getRequestDispatcher("index.html").forward(request, response);
                    //sendResponse(response, STATUS_HTTP_UNAUTHORIZED, "{ \"message\": \"Invalid email or password\" }");
                    break;
                }
                RecipeDatabase.getInstance().addRecipe(recipe);
                request.getServletContext().getRequestDispatcher("index.html").forward(request, response);
                break;
            case "get_recipes":
                List<Recipe> recipes = RecipeDatabase.getInstance().getAllRecipes();
                request.setAttribute("recipes", recipes);
                request.getServletContext().getRequestDispatcher("index.html").forward(request, response);
                break;
            default:
                request.getServletContext().getRequestDispatcher("index.html").forward(request, response);
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
