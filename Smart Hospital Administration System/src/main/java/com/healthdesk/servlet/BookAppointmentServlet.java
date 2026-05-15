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

@WebServlet("/patient/book")
public class BookAppointmentServlet extends HttpServlet {
    private final AppointmentDao appointmentDao = new AppointmentDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"PATIENT".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        int patientId = (int) session.getAttribute("userId");
        int doctorId = Integer.parseInt(req.getParameter("doctorId"));
        String date = req.getParameter("date");
        String time = req.getParameter("time");
        String notes = req.getParameter("notes");

        try {
            appointmentDao.bookAppointment(patientId, doctorId, date, time, notes);
            resp.sendRedirect(req.getContextPath() + "/patient/dashboard");
        } catch (SQLException e) {
            throw new ServletException("Unable to book appointment", e);
        }
    }
}
