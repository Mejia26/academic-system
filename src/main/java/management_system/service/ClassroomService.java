package management_system.service;

import java.util.List;
import java.util.UUID;

import management_system.dto.ClassroomCreateDto;
import management_system.dto.ClassroomResponseDto;
import management_system.dto.StudentResponseDto;

public interface ClassroomService {
    ClassroomResponseDto createClassroom(ClassroomCreateDto createDto);
    ClassroomResponseDto getClassroomById(UUID id);
    
    // Fetches classrooms belonging to the logged-in teacher
    List<ClassroomResponseDto> getClassroomsByTeacher(UUID teacherId);
    
    // Updates classroom details (like name or description)
    ClassroomResponseDto updateClassroom(UUID id, ClassroomCreateDto updateDto);
    
    // Soft-deletes the classroom
    void deleteClassroom(UUID id);

    // Business logic to manage students within a classroom
    void addStudentToClassroom(UUID classroomId, UUID studentId);
    void removeStudentFromClassroom(UUID classroomId, UUID studentId);
    List<StudentResponseDto> getStudentsByClassroom(UUID classroomId);
}