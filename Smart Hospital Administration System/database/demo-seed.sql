USE healthdesk;

-- Demo doctor accounts
INSERT INTO users (username, password_hash, role, full_name, contact, specialization, availability)
SELECT 'doctor1', SHA2('doc12345', 256), 'DOCTOR', 'Dr. Ananya Mehta', '9876543210', 'Cardiology', 'Mon-Fri 10:00-17:00'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'doctor1');

INSERT INTO users (username, password_hash, role, full_name, contact, specialization, availability)
SELECT 'doctor2', SHA2('doc22345', 256), 'DOCTOR', 'Dr. Karan Verma', '9876543211', 'Orthopedics', 'Mon-Sat 12:00-18:00'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'doctor2');

INSERT INTO users (username, password_hash, role, full_name, contact, specialization, availability)
SELECT 'doctor3', SHA2('doc32345', 256), 'DOCTOR', 'Dr. Riya Nair', '9876543212', 'Dermatology', 'Tue-Sun 09:00-14:00'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'doctor3');

-- Demo patient accounts
INSERT INTO users (username, password_hash, role, full_name, contact)
SELECT 'patient1', SHA2('pat12345', 256), 'PATIENT', 'Rahul Sharma', '9123456780'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'patient1');

INSERT INTO users (username, password_hash, role, full_name, contact)
SELECT 'patient2', SHA2('pat22345', 256), 'PATIENT', 'Neha Gupta', '9123456781'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'patient2');

INSERT INTO users (username, password_hash, role, full_name, contact)
SELECT 'patient3', SHA2('pat32345', 256), 'PATIENT', 'Aarav Singh', '9123456782'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'patient3');

INSERT INTO users (username, password_hash, role, full_name, contact)
SELECT 'patient4', SHA2('pat42345', 256), 'PATIENT', 'Sneha Iyer', '9123456783'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'patient4');

-- Ensure patient profiles exist
INSERT INTO patients (patient_id, age, gender, address)
SELECT u.id, 29, 'Male', 'Noida Sector 62'
FROM users u
WHERE u.username = 'patient1'
  AND NOT EXISTS (SELECT 1 FROM patients p WHERE p.patient_id = u.id);

INSERT INTO patients (patient_id, age, gender, address)
SELECT u.id, 34, 'Female', 'Greater Noida West'
FROM users u
WHERE u.username = 'patient2'
  AND NOT EXISTS (SELECT 1 FROM patients p WHERE p.patient_id = u.id);

INSERT INTO patients (patient_id, age, gender, address)
SELECT u.id, 41, 'Male', 'Indirapuram, Ghaziabad'
FROM users u
WHERE u.username = 'patient3'
  AND NOT EXISTS (SELECT 1 FROM patients p WHERE p.patient_id = u.id);

INSERT INTO patients (patient_id, age, gender, address)
SELECT u.id, 26, 'Female', 'Sector 137, Noida'
FROM users u
WHERE u.username = 'patient4'
  AND NOT EXISTS (SELECT 1 FROM patients p WHERE p.patient_id = u.id);

SET @d1 = (SELECT id FROM users WHERE username = 'doctor1' LIMIT 1);
SET @d2 = (SELECT id FROM users WHERE username = 'doctor2' LIMIT 1);
SET @d3 = (SELECT id FROM users WHERE username = 'doctor3' LIMIT 1);
SET @p1 = (SELECT id FROM users WHERE username = 'patient1' LIMIT 1);
SET @p2 = (SELECT id FROM users WHERE username = 'patient2' LIMIT 1);
SET @p3 = (SELECT id FROM users WHERE username = 'patient3' LIMIT 1);
SET @p4 = (SELECT id FROM users WHERE username = 'patient4' LIMIT 1);

-- Demo appointments (mix of BOOKED and COMPLETED)
INSERT INTO appointments (patient_id, doctor_id, appt_date, appt_time, status, diagnosis, prescription, notes)
SELECT @p1, @d1, '2026-04-20', '11:00:00', 'BOOKED', NULL, NULL, 'Follow-up checkup'
WHERE @p1 IS NOT NULL AND @d1 IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM appointments WHERE patient_id=@p1 AND doctor_id=@d1 AND appt_date='2026-04-20' AND appt_time='11:00:00'
  );

INSERT INTO appointments (patient_id, doctor_id, appt_date, appt_time, status, diagnosis, prescription, notes)
SELECT @p1, @d1, '2026-04-10', '09:30:00', 'COMPLETED', 'Mild viral fever', 'Paracetamol 650mg twice daily for 3 days', 'Hydration and rest advised'
WHERE @p1 IS NOT NULL AND @d1 IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM appointments WHERE patient_id=@p1 AND doctor_id=@d1 AND appt_date='2026-04-10' AND appt_time='09:30:00'
  );

INSERT INTO appointments (patient_id, doctor_id, appt_date, appt_time, status, diagnosis, prescription, notes)
SELECT @p2, @d2, '2026-04-21', '15:00:00', 'BOOKED', NULL, NULL, 'Knee pain for one week'
WHERE @p2 IS NOT NULL AND @d2 IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM appointments WHERE patient_id=@p2 AND doctor_id=@d2 AND appt_date='2026-04-21' AND appt_time='15:00:00'
  );

INSERT INTO appointments (patient_id, doctor_id, appt_date, appt_time, status, diagnosis, prescription, notes)
SELECT @p3, @d2, '2026-04-09', '13:15:00', 'COMPLETED', 'Ligament strain', 'Ibuprofen 400mg and physiotherapy', 'Avoid heavy lifting for 10 days'
WHERE @p3 IS NOT NULL AND @d2 IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM appointments WHERE patient_id=@p3 AND doctor_id=@d2 AND appt_date='2026-04-09' AND appt_time='13:15:00'
  );

INSERT INTO appointments (patient_id, doctor_id, appt_date, appt_time, status, diagnosis, prescription, notes)
SELECT @p4, @d3, '2026-04-22', '10:30:00', 'BOOKED', NULL, NULL, 'Skin rash consultation'
WHERE @p4 IS NOT NULL AND @d3 IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM appointments WHERE patient_id=@p4 AND doctor_id=@d3 AND appt_date='2026-04-22' AND appt_time='10:30:00'
  );

INSERT INTO appointments (patient_id, doctor_id, appt_date, appt_time, status, diagnosis, prescription, notes)
SELECT @p2, @d3, '2026-04-08', '09:45:00', 'COMPLETED', 'Allergic dermatitis', 'Topical corticosteroid cream', 'Review after 7 days'
WHERE @p2 IS NOT NULL AND @d3 IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM appointments WHERE patient_id=@p2 AND doctor_id=@d3 AND appt_date='2026-04-08' AND appt_time='09:45:00'
  );

SET @a1 = (SELECT id FROM appointments WHERE patient_id=@p1 AND doctor_id=@d1 AND appt_date='2026-04-10' AND appt_time='09:30:00' LIMIT 1);
SET @a2 = (SELECT id FROM appointments WHERE patient_id=@p1 AND doctor_id=@d1 AND appt_date='2026-04-20' AND appt_time='11:00:00' LIMIT 1);
SET @a3 = (SELECT id FROM appointments WHERE patient_id=@p3 AND doctor_id=@d2 AND appt_date='2026-04-09' AND appt_time='13:15:00' LIMIT 1);
SET @a4 = (SELECT id FROM appointments WHERE patient_id=@p2 AND doctor_id=@d2 AND appt_date='2026-04-21' AND appt_time='15:00:00' LIMIT 1);
SET @a5 = (SELECT id FROM appointments WHERE patient_id=@p2 AND doctor_id=@d3 AND appt_date='2026-04-08' AND appt_time='09:45:00' LIMIT 1);
SET @a6 = (SELECT id FROM appointments WHERE patient_id=@p4 AND doctor_id=@d3 AND appt_date='2026-04-22' AND appt_time='10:30:00' LIMIT 1);

-- Demo bills linked to appointments
INSERT INTO bills (appointment_id, amount, payment_status)
SELECT @a1, 1200.00, 'PAID'
WHERE @a1 IS NOT NULL
ON DUPLICATE KEY UPDATE amount=VALUES(amount), payment_status=VALUES(payment_status);

INSERT INTO bills (appointment_id, amount, payment_status)
SELECT @a2, 800.00, 'PENDING'
WHERE @a2 IS NOT NULL
ON DUPLICATE KEY UPDATE amount=VALUES(amount), payment_status=VALUES(payment_status);

INSERT INTO bills (appointment_id, amount, payment_status)
SELECT @a3, 1500.00, 'PAID'
WHERE @a3 IS NOT NULL
ON DUPLICATE KEY UPDATE amount=VALUES(amount), payment_status=VALUES(payment_status);

INSERT INTO bills (appointment_id, amount, payment_status)
SELECT @a4, 1000.00, 'PENDING'
WHERE @a4 IS NOT NULL
ON DUPLICATE KEY UPDATE amount=VALUES(amount), payment_status=VALUES(payment_status);

INSERT INTO bills (appointment_id, amount, payment_status)
SELECT @a5, 900.00, 'PAID'
WHERE @a5 IS NOT NULL
ON DUPLICATE KEY UPDATE amount=VALUES(amount), payment_status=VALUES(payment_status);

INSERT INTO bills (appointment_id, amount, payment_status)
SELECT @a6, 1100.00, 'PENDING'
WHERE @a6 IS NOT NULL
ON DUPLICATE KEY UPDATE amount=VALUES(amount), payment_status=VALUES(payment_status);
