package management_system.service;

import java.util.List;
import java.util.UUID;

import management_system.dto.StudentCreateDto;
import management_system.dto.StudentResponseDto;

public interface StudentService {
    StudentResponseDto createStudent(StudentCreateDto createDto);
    StudentResponseDto getStudentById(UUID id);
    StudentResponseDto getStudentByCode(String studentCode);
    List<StudentResponseDto> getAllStudents();
    StudentResponseDto updateStudent(UUID id, StudentCreateDto updateDto);
    void deleteStudent(UUID id);
}