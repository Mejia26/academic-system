-- ==============================================================================
-- 02-data.sql
-- Inserts dummy data for frontend testing.
-- ==============================================================================

-- 1. Insert a test teacher
-- Password is 'password123' (BCrypt hashed)
INSERT INTO users (id, first_name, last_name, email, password_hash, role)
VALUES 
('11111111-1111-1111-1111-111111111111', 'John', 'Doe', 'teacher@example.com', '$2a$12$veg6h6AFrUT6NPB.Fug9B.DQpkRGo1YtRhL0rcinaUPb8HnkZirSW', 'TEACHER');

-- 2. Insert test classrooms
INSERT INTO classrooms (id, name, description, teacher_id)
VALUES 
('22222222-2222-2222-2222-222222222221', 'Mathematics 101', 'Introductory Math', '11111111-1111-1111-1111-111111111111'),
('22222222-2222-2222-2222-222222222222', 'Physics 202', 'Advanced Physics', '11111111-1111-1111-1111-111111111111');

-- 3. Insert test students
INSERT INTO students (id, first_name, last_name, student_code, email)
VALUES 
('33333333-3333-3333-3333-333333333331', 'Alice', 'Smith', 'STU-001', 'alice@student.com'),
('33333333-3333-3333-3333-333333333332', 'Bob', 'Johnson', 'STU-002', 'bob@student.com');

-- 4. Assign students to Mathematics 101
INSERT INTO classroom_students (classroom_id, student_id)
VALUES 
('22222222-2222-2222-2222-222222222221', '33333333-3333-3333-3333-333333333331'),
('22222222-2222-2222-2222-222222222221', '33333333-3333-3333-3333-333333333332');