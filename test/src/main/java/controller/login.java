/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import model.DbConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rebaz
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    try (Connection conn = DbConnection.getConnection()) {
        HttpSession session = request.getSession();
        String sql = "SELECT id, username, password FROM user WHERE username = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
    String dbUsername = resultSet.getString("username");
    String dbPassword = resultSet.getString("password");

    session.setAttribute("username", dbUsername);

    if (password.equals(dbPassword)) {
        out.println("Logged in"); // User credentials match

        // Create a new cookie for the username
        Cookie usernameCookie = new Cookie("username", dbUsername);
        usernameCookie.setMaxAge(2 * 60 * 60); // Set expiration time to 2 hours in seconds
        response.addCookie(usernameCookie);

        response.sendRedirect("Home.jsp");
    } else {
        out.println("Invalid username or password"); // Password doesn't match
    }
}
 else {
            out.println("Invalid username or password");
            response.sendRedirect("index.html");// Username doesn't exist in the database
        }

        resultSet.close();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        out.println("An error occurred"); // Error occurred while querying the database
    }
}
}
