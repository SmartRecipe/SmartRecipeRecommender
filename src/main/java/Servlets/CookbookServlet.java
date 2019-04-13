/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Servlets;

import Beans.Recipe;
import Beans.User;
import Servlets.utils.BaseRequest;
import Servlets.utils.BaseResponse;
import Databases.RecipeDatabase;
import Databases.UserDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
        User user;
        Recipe recipe;
        Gson gson = new Gson();
        
        String filters[];
        String action = request != null ? request.getParameter("action") : "";

        BaseRequest baseRequest;
        BaseResponse baseResponse = new BaseResponse();

        // try to convert the request body into an instance of BaseRequest class
        // all requests that fail to convert are malformed, we don't understand them
        try{
            String requestBody = getBody(request);  // parse request body as json
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
        
        try {
            recipe = baseRequest.getRecipe();
        } catch (Exception e) {
            recipe = null;
        }
        
        switch (action) {
            case "add_recipe":
                try {
                    if (RecipeDatabase.getInstance().addRecipe(recipe)) {
                        baseResponse.setRecipe(recipe);
                        baseResponse.setMessage("Success");
                        sendResponse(response, STATUS_HTTP_OK, gson.toJson(baseResponse));
                    }
                    else {
                        baseResponse.setMessage("A recipe by that name may already exist within the database.");
                        sendResponse(response, STATUS_HTTP_CONFLICT, gson.toJson(baseResponse));
                    }
                } catch (Exception e) {
                    baseResponse.setMessage("Error occurred while adding recipe.");
                    sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                    break;
                }
                break;
            case "get_recipes":
                recipes = RecipeDatabase.getInstance().getAllRecipes();
                baseResponse.setMessage("Success");
                baseResponse.setRecipes(recipes);
                sendResponse(response, STATUS_HTTP_OK, gson.toJson(baseResponse));
                break;
            case "edit_recipe":
                try {
                    if (RecipeDatabase.getInstance().updateRecipe(recipe)) {
                        baseResponse.setRecipe(recipe);
                        baseResponse.setMessage("Success");
                        sendResponse(response, STATUS_HTTP_OK, gson.toJson(baseResponse));
                    }
                    else {
                        baseResponse.setMessage("Error occured while updating recipe.");
                        sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                    }
                } catch (Exception e) {
                    baseResponse.setMessage("Error occurred while updating recipe");
                    sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                }
                break;
            case "search_recipes":
                if (user != null) {
                    try {
                        recipes = user.getFridge().checkAllRecipes(baseRequest.getFilters());
                        baseResponse.setMessage("Success");
                        baseResponse.setRecipes(recipes);
                        sendResponse(response, STATUS_HTTP_OK, gson.toJson(baseResponse));
                    }
                    catch (Exception e) {
                        baseResponse.setMessage("Error searching recipes");
                        sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                    }
                }
                else {
                    baseResponse.setMessage("Unauthorized");
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, gson.toJson(baseResponse));
                }
                break;
            case "recommend_recipe":
                if (user != null) {
                    recipe = user.getFridge().recommendRecipe(user.getCookbook());
                    baseResponse.setRecipe(recipe);
                    sendResponse(response, STATUS_HTTP_OK, gson.toJson(baseResponse));
                }
                else {
                    baseResponse.setMessage("Unauthorized");
                    sendResponse(response, STATUS_HTTP_UNAUTHORIZED, gson.toJson(baseResponse));
                }
                break;
            case "add_history":
                if (user != null) {
                    if (user.getCookbook().addToHistory(recipe)) {
                        UserDatabase.getInstance().updateUser(user);
                        baseResponse.setUser(user);
                        baseResponse.setMessage("Success");
                        sendResponse(response, STATUS_HTTP_OK, gson.toJson(baseResponse));
                    }
                    else {
                        UserDatabase.getInstance().updateUser(user);
                        baseResponse.setUser(user);
                        baseResponse.setMessage("Error adding recipe to history");
                        sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                    }
                }
                else {
                    baseResponse.setMessage("Unauthorized");
                    sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                }
                break;
            case "add_favorites":
                if (user != null) {
                    if (user.getCookbook().addToFavorites(recipe)) {
                        UserDatabase.getInstance().updateUser(user);
                        baseResponse.setUser(user);
                        baseResponse.setMessage("Success");
                        sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                    }
                    else {
                        UserDatabase.getInstance().updateUser(user);
                        baseResponse.setUser(user);
                        baseResponse.setMessage("Error adding recipe to favorites");
                        sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                    }
                }
                else {
                    baseResponse.setMessage("Unauthorized");
                    sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                }
                break;
            case "remove_favorites":
                if (user != null) {
                    if (user.getCookbook().removeFromFavorites(recipe)) {
                        UserDatabase.getInstance().updateUser(user);
                        
                        baseResponse.setUser(user);
                        baseResponse.setMessage("Success");
                        sendResponse(response, STATUS_HTTP_INTERNAL_ERROR, gson.toJson(baseResponse));
                    }
                    else {
                        UserDatabase.getInstance().updateUser(user);
                        
                        baseResponse.setUser(user);
                        baseResponse.setMessage("Error remove recipe from favorites");
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
