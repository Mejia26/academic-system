package management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import management_system.model.Grade;

import java.util.List;
import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<Grade, UUID> {
    
    // Prevents duplicate grades for the same period
    boolean existsByClassroomIdAndStudentIdAndPeriod(UUID classroomId, UUID studentId, String period);
    
    // Gets all grades for a specific classroom
    List<Grade> findAllByClassroomId(UUID classroomId);
    
    // Gets all grades for a specific student in a specific classroom
    List<Grade> findAllByClassroomIdAndStudentId(UUID classroomId, UUID studentId);
}