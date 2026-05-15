package com.healthdesk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.healthdesk.model.User;
import com.healthdesk.util.DbUtil;
import com.healthdesk.util.PasswordUtil;

public class UserDao {

    public Optional<User> authenticate(String username, String password) throws SQLException {
        String sql = "SELECT id, username, role, full_name, contact, specialization, availability " +
            "FROM users WHERE username = ? AND password_hash = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, PasswordUtil.sha256(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapUser(rs));
                }
            }
        }
        return Optional.empty();
    }

    public boolean registerPatient(String username, String password, String fullName,
                                   String contact, int age, String gender, String address) throws SQLException {
        String userSql = "INSERT INTO users(username, password_hash, role, full_name, contact) VALUES(?, ?, 'PATIENT', ?, ?)";
        String patientSql = "INSERT INTO patients(patient_id, age, gender, address) VALUES(?, ?, ?, ?)";

        try (Connection connection = DbUtil.getConnection()) {
            connection.setAutoCommit(false);
              try (PreparedStatement userPs = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement patientPs = connection.prepareStatement(patientSql)) {
                userPs.setString(1, username);
                userPs.setString(2, PasswordUtil.sha256(password));
                userPs.setString(3, fullName);
                userPs.setString(4, contact);
                int inserted = userPs.executeUpdate();
                if (inserted == 0) {
                    connection.rollback();
                    return false;
                }

                int patientId;
                try (ResultSet keys = userPs.getGeneratedKeys()) {
                    if (!keys.next()) {
                        connection.rollback();
                        return false;
                    }
                    patientId = keys.getInt(1);
                }

                patientPs.setInt(1, patientId);
                patientPs.setInt(2, age);
                patientPs.setString(3, gender);
                patientPs.setString(4, address);
                patientPs.executeUpdate();

                connection.commit();
                return true;
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public boolean addDoctor(String username, String password, String fullName, String contact,
                             String specialization, String availability) throws SQLException {
        String sql = "INSERT INTO users(username, password_hash, role, full_name, contact, specialization, availability) " +
            "VALUES(?, ?, 'DOCTOR', ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, PasswordUtil.sha256(password));
            ps.setString(3, fullName);
            ps.setString(4, contact);
            ps.setString(5, specialization);
            ps.setString(6, availability);
            return ps.executeUpdate() > 0;
        }
    }

    public List<User> getDoctors() throws SQLException {
        String sql = "SELECT id, username, role, full_name, contact, specialization, availability " +
            "FROM users WHERE role = 'DOCTOR' ORDER BY full_name";
        List<User> doctors = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                doctors.add(mapUser(rs));
            }
        }
        return doctors;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setRole(rs.getString("role"));
        user.setFullName(rs.getString("full_name"));
        user.setContact(rs.getString("contact"));
        user.setSpecialization(rs.getString("specialization"));
        user.setAvailability(rs.getString("availability"));
        return user;
    }
}
