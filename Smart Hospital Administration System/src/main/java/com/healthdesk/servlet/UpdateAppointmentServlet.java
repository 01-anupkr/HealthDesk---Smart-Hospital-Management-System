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

@WebServlet("/doctor/appointment/update")
public class UpdateAppointmentServlet extends HttpServlet {
    private final AppointmentDao appointmentDao = new AppointmentDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"DOCTOR".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        int appointmentId = Integer.parseInt(req.getParameter("appointmentId"));
        String status = req.getParameter("status");
        String diagnosis = req.getParameter("diagnosis");
        String prescription = req.getParameter("prescription");

        try {
            appointmentDao.updateAppointmentByDoctor(appointmentId, status, diagnosis, prescription);
            resp.sendRedirect(req.getContextPath() + "/doctor/dashboard");
        } catch (SQLException e) {
            throw new ServletException("Unable to update appointment", e);
        }
    }
}
