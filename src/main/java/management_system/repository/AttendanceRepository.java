package management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import management_system.model.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    
    // Prevents duplicate attendance for a student on the same day in the same classroom
    boolean existsByClassroomIdAndStudentIdAndDate(UUID classroomId, UUID studentId, LocalDate date);
    
    // Gets all attendance records for a specific classroom
    List<Attendance> findAllByClassroomId(UUID classroomId);
    
    // Gets all attendance records for a specific student in a specific classroom
    List<Attendance> findAllByClassroomIdAndStudentId(UUID classroomId, UUID studentId);
}