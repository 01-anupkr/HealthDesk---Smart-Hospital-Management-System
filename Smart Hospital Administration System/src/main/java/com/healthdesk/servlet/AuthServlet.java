package com.healthdesk.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import com.healthdesk.dao.UserDao;
import com.healthdesk.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Optional<User> userOpt = userDao.authenticate(username, password);
            if (userOpt.isEmpty()) {
                req.setAttribute("error", "Invalid username or password.");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
                return;
            }

            User user = userOpt.get();
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());
            session.setAttribute("fullName", user.getFullName());

            switch (user.getRole()) {
                case "ADMIN" -> resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                case "DOCTOR" -> resp.sendRedirect(req.getContextPath() + "/doctor/dashboard");
                default -> resp.sendRedirect(req.getContextPath() + "/patient/dashboard");
            }
        } catch (SQLException e) {
            throw new ServletException("Login failed", e);
        }
    }
}
