<%@ page import="java.util.List" %>
<%@ page import="com.healthdesk.model.AppointmentView" %>
<%
    List<AppointmentView> appointments = (List<AppointmentView>) request.getAttribute("appointments");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Doctor Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
</head>
<body>
<header class="topbar">
    <h1>Doctor Dashboard</h1>
    <div>
        <span>Welcome, Dr. <%= session.getAttribute("fullName") %></span>
        <form method="post" action="${pageContext.request.contextPath}/auth" class="inline-form">
            <input type="hidden" name="action" value="logout">
            <button type="submit" class="secondary">Logout</button>
        </form>
    </div>
</header>

<main class="shell">
    <section class="card wide">
        <h2>Assigned Appointments</h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Patient</th>
                <th>Date</th>
                <th>Time</th>
                <th>Status</th>
                <th>Update</th>
            </tr>
            </thead>
            <tbody>
            <% for (AppointmentView a : appointments) { %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getPatientName() %></td>
                <td><%= a.getDate() %></td>
                <td><%= a.getTime() %></td>
                <td><span class="badge"><%= a.getStatus() %></span></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/doctor/appointment/update" class="row-form">
                        <input type="hidden" name="appointmentId" value="<%= a.getId() %>">
                        <select name="status" required>
                            <option value="BOOKED" <%= "BOOKED".equals(a.getStatus()) ? "selected" : "" %>>BOOKED</option>
                            <option value="COMPLETED" <%= "COMPLETED".equals(a.getStatus()) ? "selected" : "" %>>COMPLETED</option>
                            <option value="CANCELLED" <%= "CANCELLED".equals(a.getStatus()) ? "selected" : "" %>>CANCELLED</option>
                        </select>
                        <input type="text" name="diagnosis" placeholder="Diagnosis" value="<%= a.getDiagnosis() == null ? "" : a.getDiagnosis() %>">
                        <input type="text" name="prescription" placeholder="Prescription" value="<%= a.getPrescription() == null ? "" : a.getPrescription() %>">
                        <button type="submit">Save</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </section>
</main>
</body>
</html>
