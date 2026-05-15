package com.healthdesk.servlet;

import com.healthdesk.dao.BillingDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/bill/status")
public class UpdateBillStatusServlet extends HttpServlet {
    private final BillingDao billingDao = new BillingDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        int billId = Integer.parseInt(req.getParameter("billId"));
        String paymentStatus = req.getParameter("paymentStatus");

        try {
            billingDao.updatePaymentStatus(billId, paymentStatus);
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } catch (SQLException e) {
            throw new ServletException("Unable to update bill status", e);
        }
    }
}
