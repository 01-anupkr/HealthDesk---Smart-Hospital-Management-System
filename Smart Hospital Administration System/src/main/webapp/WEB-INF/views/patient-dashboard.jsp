<%@ page import="java.util.List" %>
<%@ page import="com.healthdesk.model.User" %>
<%@ page import="com.healthdesk.model.AppointmentView" %>
<%
    List<User> doctors = (List<User>) request.getAttribute("doctors");
    List<AppointmentView> appointments = (List<AppointmentView>) request.getAttribute("appointments");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Patient Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
</head>
<body>
<header class="topbar">
    <h1>Patient Dashboard</h1>
    <div>
        <span>Welcome, <%= session.getAttribute("fullName") %></span>
        <form method="post" action="${pageContext.request.contextPath}/auth" class="inline-form">
            <input type="hidden" name="action" value="logout">
            <button type="submit" class="secondary">Logout</button>
        </form>
    </div>
</header>

<main class="shell dashboard-grid">
    <section class="card">
        <h2>Book Appointment</h2>
        <form method="post" action="${pageContext.request.contextPath}/patient/book">
            <label>Doctor</label>
            <select name="doctorId" required>
                <option value="">Choose doctor</option>
                <% for (User d : doctors) { %>
                <option value="<%= d.getId() %>"><%= d.getFullName() %> - <%= d.getSpecialization() %> (<%= d.getAvailability() %>)</option>
                <% } %>
            </select>

            <label>Date</label>
            <input type="date" name="date" required>

            <label>Time</label>
            <input type="time" name="time" required>

            <label>Notes</label>
            <textarea name="notes" rows="3" placeholder="Symptoms / message"></textarea>

            <button type="submit">Book</button>
        </form>
    </section>

    <section class="card wide">
        <h2>My Appointments</h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Doctor</th>
                <th>Date</th>
                <th>Time</th>
                <th>Status</th>
                <th>Diagnosis</th>
                <th>Prescription</th>
            </tr>
            </thead>
            <tbody>
            <% for (AppointmentView a : appointments) { %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getDoctorName() %></td>
                <td><%= a.getDate() %></td>
                <td><%= a.getTime() %></td>
                <td><span class="badge"><%= a.getStatus() %></span></td>
                <td><%= a.getDiagnosis() == null ? "-" : a.getDiagnosis() %></td>
                <td><%= a.getPrescription() == null ? "-" : a.getPrescription() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </section>
</main>
</body>
</html>
