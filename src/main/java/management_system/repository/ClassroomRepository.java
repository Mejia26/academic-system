package management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import management_system.model.Classroom;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
    
    // Fetches all classrooms assigned to a specific teacher
    List<Classroom> findAllByTeacherId(UUID teacherId);
}