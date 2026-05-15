# HealthDesk - Smart Hospital Administration System

A Java Servlet + JSP + MySQL web application based on your synopsis requirements.

## Features Implemented

- Role-based access for `ADMIN`, `DOCTOR`, `PATIENT`
- Patient registration and secure login
- Doctor profile and availability management
- Appointment booking and tracking
- Doctor diagnosis/prescription updates
- Billing generation and payment status updates
- Admin dashboard and simple reports

## Tech Stack

- Java 17
- Javax Servlet 3.1
- JSP, HTML, CSS, JavaScript
- MySQL 8+
- Apache Tomcat
- Maven

## Install and Configure (macOS)

```bash
brew install maven
brew services start mysql
```

Verify:

```bash
java -version
mvn -v
mysql --version
```

## Database Setup

1. Start MySQL server.
2. Run [database/schema.sql](database/schema.sql).
3. (Optional but recommended) Load demo dataset:

```bash
mysql -uroot < database/demo-seed.sql
```

4. Login credentials:
   - Admin: `admin` / `admin123`
   - Doctor 1: `doctor1` / `doc12345`
   - Doctor 2: `doctor2` / `doc22345`
   - Doctor 3: `doctor3` / `doc32345`
   - Patient 1: `patient1` / `pat12345`
   - Patient 2: `patient2` / `pat22345`
   - Patient 3: `patient3` / `pat32345`
   - Patient 4: `patient4` / `pat42345`

## Configure DB Credentials

Edit [src/main/webapp/WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml):

- `dbUrl`
- `dbUser`
- `dbPassword`

Default local config in this project uses:

- `dbUser=root`
- `dbPassword=` (empty)

## Run Locally

```bash
mvn clean package
mvn tomcat7:run
```

Open:

- http://localhost:8080/healthdesk

## Role Flows

- Patient: Register -> Login -> Book appointment -> View appointments
- Doctor: Login -> View assigned appointments -> Add diagnosis/prescription -> Mark completed/cancelled
- Admin: Login -> Add doctors -> View reports -> Generate bills

## Suggested Next Enhancements

- Email/SMS notifications
- Online payment gateway
- Cloud deployment
- Pharmacy and Lab modules
- EMR integration
