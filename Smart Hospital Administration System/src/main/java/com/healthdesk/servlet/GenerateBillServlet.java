package com.healthdesk.servlet;

import com.healthdesk.dao.BillingDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/admin/bill")
public class GenerateBillServlet extends HttpServlet {
    private final BillingDao billingDao = new BillingDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        int appointmentId = Integer.parseInt(req.getParameter("appointmentId"));
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));

        try {
            billingDao.createBill(appointmentId, amount);
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } catch (SQLException e) {
            throw new ServletException("Unable to create bill", e);
        }
    }
}
