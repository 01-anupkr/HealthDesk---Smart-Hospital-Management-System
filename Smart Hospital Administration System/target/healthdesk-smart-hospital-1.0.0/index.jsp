<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>HealthDesk - Smart Hospital Administration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
</head>
<body>
<div class="hero-bg"></div>
<main class="shell auth-shell">
    <section class="card intro-card">
        <h1>HealthDesk</h1>
        <p>Smart Hospital Administration System</p>
        <ul>
            <li>Secure digital records</li>
            <li>Appointments and doctor management</li>
            <li>Billing and operational reporting</li>
        </ul>
        <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <% if (request.getAttribute("success") != null) { %>
        <p class="success"><%= request.getAttribute("success") %></p>
        <% } %>
    </section>

    <section class="card form-card">
        <h2>Login</h2>
        <form method="post" action="${pageContext.request.contextPath}/auth">
            <label>Username</label>
            <input type="text" name="username" required>

            <label>Password</label>
            <input type="password" name="password" required>

            <button type="submit">Sign In</button>
        </form>

        <hr>

        <h2>Patient Registration</h2>
        <form method="post" action="${pageContext.request.contextPath}/register" onsubmit="return validateRegistration(this)">
            <label>Full Name</label>
            <input type="text" name="fullName" required>

            <label>Username</label>
            <input type="text" name="username" required>

            <label>Password</label>
            <input type="password" name="password" minlength="6" required>

            <label>Contact</label>
            <input type="text" name="contact" required>

            <label>Age</label>
            <input type="number" name="age" min="1" max="120" required>

            <label>Gender</label>
            <select name="gender" required>
                <option value="">Select</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
            </select>

            <label>Address</label>
            <textarea name="address" rows="2" required></textarea>

            <button type="submit">Create Patient Account</button>
        </form>
    </section>
</main>
<script src="${pageContext.request.contextPath}/assets/validation.js"></script>
</body>
</html>
