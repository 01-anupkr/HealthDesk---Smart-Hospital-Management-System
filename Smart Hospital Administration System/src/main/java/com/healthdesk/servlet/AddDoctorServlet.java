package com.healthdesk.servlet;

import com.healthdesk.dao.UserDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/doctor/add")
public class AddDoctorServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String contact = req.getParameter("contact");
        String specialization = req.getParameter("specialization");
        String availability = req.getParameter("availability");

        try {
            userDao.addDoctor(username, password, fullName, contact, specialization, availability);
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } catch (SQLException e) {
            throw new ServletException("Unable to add doctor", e);
        }
    }
}
