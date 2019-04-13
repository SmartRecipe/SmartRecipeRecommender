package Servlets;

import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.lang.StringBuilder;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import Servlets.utils.BaseRequest;

/**
 * General utility class to handle request and response operations
 */
public class BaseServlet extends HttpServlet {
    public static final int STATUS_HTTP_OK = 200;
    public static final int STATUS_HTTP_FOUND = 204;
    public static final int STATUS_HTTP_BAD_REQUEST = 400;
    public static final int STATUS_HTTP_UNAUTHORIZED = 401;
    public static final int STATUS_HTTP_CONFLICT = 409;
    public static final int STATUS_HTTP_NOT_FOUND = 404;
    public static final int STATUS_HTTP_INTERNAL_ERROR = 500;

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
    }

    /**
     * Parses request body as a Json string
     * @param  request     request object
     * @return             json as a String
     * @throws IOException 
     */
    protected String getBody(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        body = stringBuilder.toString();
        return body;
    }
    
    protected BaseRequest getBaseRequest(HttpServletRequest request) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        return gson.fromJson(getBody(request), BaseRequest.class);
    }
    
    /**
     * Returns the request parameter for action.  If request or parameter is null, a blank
     * String is returned
     * @param request
     * @return the action parameter or blank string
     */
    protected String getAction(HttpServletRequest request) {
        return request != null && request.getParameter("action") != null 
                ? request.getParameter("action") : "";
        
    }

    /**
     * Sends response as a Application/Json object
     * @param  response    response object
     * @param  body        Json as a string
     * @throws IOException 
     */
    protected void sendResponse(HttpServletResponse response, int code, String body) throws IOException {
        response.setStatus(code);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.getWriter().write(body);
    }

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
     * Handles the HTTP <code>OPTIONS</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sendResponse(response, STATUS_HTTP_OK, "");
    }
}