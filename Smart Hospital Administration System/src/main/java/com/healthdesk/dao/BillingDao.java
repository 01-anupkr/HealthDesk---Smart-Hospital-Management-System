package com.healthdesk.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.healthdesk.model.BillView;
import com.healthdesk.util.DbUtil;

public class BillingDao {

    public boolean createBill(int appointmentId, BigDecimal amount) throws SQLException {
        String sql = "INSERT INTO bills(appointment_id, amount, payment_status) VALUES(?, ?, 'PENDING') " +
            "ON DUPLICATE KEY UPDATE amount = VALUES(amount)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ps.setBigDecimal(2, amount);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updatePaymentStatus(int billId, String paymentStatus) throws SQLException {
        String sql = "UPDATE bills SET payment_status = ? WHERE id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, paymentStatus);
            ps.setInt(2, billId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<BillView> getAllBills() throws SQLException {
        String sql = "SELECT b.id, b.appointment_id, p.full_name AS patient_name, d.full_name AS doctor_name, b.amount, b.payment_status " +
            "FROM bills b " +
            "JOIN appointments a ON b.appointment_id = a.id " +
            "JOIN users p ON a.patient_id = p.id " +
            "JOIN users d ON a.doctor_id = d.id " +
            "ORDER BY b.created_at DESC";
        List<BillView> bills = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BillView bill = new BillView();
                bill.setId(rs.getInt("id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setPatientName(rs.getString("patient_name"));
                bill.setDoctorName(rs.getString("doctor_name"));
                bill.setAmount(rs.getBigDecimal("amount"));
                bill.setPaymentStatus(rs.getString("payment_status"));
                bills.add(bill);
            }
        }
        return bills;
    }

    public int[] getReportStats() throws SQLException {
        int patients = count("SELECT COUNT(*) FROM users WHERE role = 'PATIENT'");
        int doctors = count("SELECT COUNT(*) FROM users WHERE role = 'DOCTOR'");
        int appointments = count("SELECT COUNT(*) FROM appointments");
        int bills = count("SELECT COUNT(*) FROM bills");
        return new int[]{patients, doctors, appointments, bills};
    }

    private int count(String sql) throws SQLException {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
