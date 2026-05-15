package com.healthdesk.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.healthdesk.dao.AppointmentDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/doctor/dashboard")
public class DoctorDashboardServlet extends HttpServlet {
    private final AppointmentDao appointmentDao = new AppointmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"DOCTOR".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        int doctorId = (int) session.getAttribute("userId");
        try {
            req.setAttribute("appointments", appointmentDao.getAppointmentsForDoctor(doctorId));
            req.getRequestDispatcher("/WEB-INF/views/doctor-dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to load doctor dashboard", e);
        }
    }
}
