package com.healthdesk.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.healthdesk.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class PatientRegisterServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String contact = req.getParameter("contact");
        int age = Integer.parseInt(req.getParameter("age"));
        String gender = req.getParameter("gender");
        String address = req.getParameter("address");

        try {
            boolean created = userDao.registerPatient(username, password, fullName, contact, age, gender, address);
            if (created) {
                req.setAttribute("success", "Registration successful. Please log in.");
            } else {
                req.setAttribute("error", "Unable to create account.");
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Registration failed: " + e.getMessage());
        }

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
