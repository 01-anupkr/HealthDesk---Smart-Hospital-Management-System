package com.healthdesk.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.healthdesk.dao.AppointmentDao;
import com.healthdesk.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/patient/dashboard")
public class PatientDashboardServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();
    private final AppointmentDao appointmentDao = new AppointmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"PATIENT".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        int patientId = (int) session.getAttribute("userId");
        try {
            req.setAttribute("doctors", userDao.getDoctors());
            req.setAttribute("appointments", appointmentDao.getAppointmentsForPatient(patientId));
            req.getRequestDispatcher("/WEB-INF/views/patient-dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to load patient dashboard", e);
        }
    }
}
