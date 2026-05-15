package com.healthdesk.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.healthdesk.model.AppointmentView;
import com.healthdesk.util.DbUtil;

public class AppointmentDao {

    public boolean bookAppointment(int patientId, int doctorId, String date, String time, String notes) throws SQLException {
        String sql = "INSERT INTO appointments(patient_id, doctor_id, appt_date, appt_time, notes) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setDate(3, Date.valueOf(date));
            ps.setTime(4, Time.valueOf(time + ":00"));
            ps.setString(5, notes);
            return ps.executeUpdate() > 0;
        }
    }

    public List<AppointmentView> getAppointmentsForPatient(int patientId) throws SQLException {
        String sql = "SELECT a.id, p.full_name AS patient_name, d.full_name AS doctor_name, a.appt_date, a.appt_time, " +
            "a.status, a.diagnosis, a.prescription " +
            "FROM appointments a " +
            "JOIN users p ON a.patient_id = p.id " +
            "JOIN users d ON a.doctor_id = d.id " +
            "WHERE a.patient_id = ? ORDER BY a.appt_date DESC, a.appt_time DESC";
        return fetchAppointments(sql, patientId);
    }

    public List<AppointmentView> getAppointmentsForDoctor(int doctorId) throws SQLException {
        String sql = "SELECT a.id, p.full_name AS patient_name, d.full_name AS doctor_name, a.appt_date, a.appt_time, " +
            "a.status, a.diagnosis, a.prescription " +
            "FROM appointments a " +
            "JOIN users p ON a.patient_id = p.id " +
            "JOIN users d ON a.doctor_id = d.id " +
            "WHERE a.doctor_id = ? ORDER BY a.appt_date DESC, a.appt_time DESC";
        return fetchAppointments(sql, doctorId);
    }

    public List<AppointmentView> getAllAppointments() throws SQLException {
        String sql = "SELECT a.id, p.full_name AS patient_name, d.full_name AS doctor_name, a.appt_date, a.appt_time, " +
            "a.status, a.diagnosis, a.prescription " +
            "FROM appointments a " +
            "JOIN users p ON a.patient_id = p.id " +
            "JOIN users d ON a.doctor_id = d.id " +
            "ORDER BY a.appt_date DESC, a.appt_time DESC";
        List<AppointmentView> appointments = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                appointments.add(mapAppointment(rs));
            }
        }
        return appointments;
    }

    public boolean updateAppointmentByDoctor(int appointmentId, String status, String diagnosis, String prescription) throws SQLException {
        String sql = "UPDATE appointments SET status = ?, diagnosis = ?, prescription = ? WHERE id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, diagnosis);
            ps.setString(3, prescription);
            ps.setInt(4, appointmentId);
            return ps.executeUpdate() > 0;
        }
    }

    private List<AppointmentView> fetchAppointments(String sql, int id) throws SQLException {
        List<AppointmentView> appointments = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapAppointment(rs));
                }
            }
        }
        return appointments;
    }

    private AppointmentView mapAppointment(ResultSet rs) throws SQLException {
        AppointmentView appt = new AppointmentView();
        appt.setId(rs.getInt("id"));
        appt.setPatientName(rs.getString("patient_name"));
        appt.setDoctorName(rs.getString("doctor_name"));
        appt.setDate(rs.getDate("appt_date").toLocalDate());
        appt.setTime(rs.getTime("appt_time").toLocalTime());
        appt.setStatus(rs.getString("status"));
        appt.setDiagnosis(rs.getString("diagnosis"));
        appt.setPrescription(rs.getString("prescription"));
        return appt;
    }
}
