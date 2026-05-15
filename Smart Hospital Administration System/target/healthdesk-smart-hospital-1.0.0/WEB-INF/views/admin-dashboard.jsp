<%@ page import="java.util.List" %>
<%@ page import="com.healthdesk.model.User" %>
<%@ page import="com.healthdesk.model.AppointmentView" %>
<%@ page import="com.healthdesk.model.BillView" %>
<%
    List<User> doctors = (List<User>) request.getAttribute("doctors");
    List<AppointmentView> appointments = (List<AppointmentView>) request.getAttribute("appointments");
    List<BillView> bills = (List<BillView>) request.getAttribute("bills");
    int[] stats = (int[]) request.getAttribute("stats");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
</head>
<body>
<header class="topbar">
    <h1>Admin Dashboard</h1>
    <div>
        <span>Welcome, <%= session.getAttribute("fullName") %></span>
        <form method="post" action="${pageContext.request.contextPath}/auth" class="inline-form">
            <input type="hidden" name="action" value="logout">
            <button type="submit" class="secondary">Logout</button>
        </form>
    </div>
</header>

<main class="shell dashboard-grid">
    <section class="card wide">
        <h2>Hospital Summary</h2>
        <div class="stats">
            <div><strong><%= stats[0] %></strong><span>Patients</span></div>
            <div><strong><%= stats[1] %></strong><span>Doctors</span></div>
            <div><strong><%= stats[2] %></strong><span>Appointments</span></div>
            <div><strong><%= stats[3] %></strong><span>Bills</span></div>
        </div>
    </section>

    <section class="card">
        <h2>Add Doctor</h2>
        <form method="post" action="${pageContext.request.contextPath}/admin/doctor/add">
            <label>Full Name</label>
            <input type="text" name="fullName" required>
            <label>Username</label>
            <input type="text" name="username" required>
            <label>Password</label>
            <input type="password" name="password" minlength="6" required>
            <label>Contact</label>
            <input type="text" name="contact" required>
            <label>Specialization</label>
            <input type="text" name="specialization" required>
            <label>Availability</label>
            <input type="text" name="availability" placeholder="Mon-Fri 10:00-17:00" required>
            <button type="submit">Add Doctor</button>
        </form>
    </section>

    <section class="card wide">
        <h2>Doctors</h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Specialization</th>
                <th>Availability</th>
                <th>Contact</th>
            </tr>
            </thead>
            <tbody>
            <% for (User d : doctors) { %>
            <tr>
                <td><%= d.getId() %></td>
                <td><%= d.getFullName() %></td>
                <td><%= d.getSpecialization() %></td>
                <td><%= d.getAvailability() %></td>
                <td><%= d.getContact() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </section>

    <section class="card wide">
        <h2>Appointments and Billing</h2>
        <table>
            <thead>
            <tr>
                <th>Appointment ID</th>
                <th>Patient</th>
                <th>Doctor</th>
                <th>Date/Time</th>
                <th>Status</th>
                <th>Generate Bill</th>
            </tr>
            </thead>
            <tbody>
            <% for (AppointmentView a : appointments) { %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getPatientName() %></td>
                <td><%= a.getDoctorName() %></td>
                <td><%= a.getDate() %> <%= a.getTime() %></td>
                <td><span class="badge"><%= a.getStatus() %></span></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin/bill" class="row-form">
                        <input type="hidden" name="appointmentId" value="<%= a.getId() %>">
                        <input type="number" step="0.01" min="0" name="amount" placeholder="Amount" required>
                        <button type="submit">Generate</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </section>

    <section class="card wide">
        <h2>Bills</h2>
        <table>
            <thead>
            <tr>
                <th>Bill ID</th>
                <th>Appointment</th>
                <th>Patient</th>
                <th>Doctor</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Update</th>
            </tr>
            </thead>
            <tbody>
            <% for (BillView b : bills) { %>
            <tr>
                <td><%= b.getId() %></td>
                <td><%= b.getAppointmentId() %></td>
                <td><%= b.getPatientName() %></td>
                <td><%= b.getDoctorName() %></td>
                <td><%= b.getAmount() %></td>
                <td><span class="badge"><%= b.getPaymentStatus() %></span></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin/bill/status" class="row-form">
                        <input type="hidden" name="billId" value="<%= b.getId() %>">
                        <select name="paymentStatus">
                            <option value="PENDING" <%= "PENDING".equals(b.getPaymentStatus()) ? "selected" : "" %>>PENDING</option>
                            <option value="PAID" <%= "PAID".equals(b.getPaymentStatus()) ? "selected" : "" %>>PAID</option>
                        </select>
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
