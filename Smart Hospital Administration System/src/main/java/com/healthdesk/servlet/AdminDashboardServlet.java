package com.healthdesk.servlet;

import com.healthdesk.dao.AppointmentDao;
import com.healthdesk.dao.BillingDao;
import com.healthdesk.dao.UserDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();
    private final AppointmentDao appointmentDao = new AppointmentDao();
    private final BillingDao billingDao = new BillingDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        try {
            req.setAttribute("doctors", userDao.getDoctors());
            req.setAttribute("appointments", appointmentDao.getAllAppointments());
            req.setAttribute("bills", billingDao.getAllBills());
            req.setAttribute("stats", billingDao.getReportStats());
            req.getRequestDispatcher("/WEB-INF/views/admin-dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to load admin dashboard", e);
        }
    }
}
