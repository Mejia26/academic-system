package management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.dto.StudentCreateDto;
import management_system.dto.StudentResponseDto;
import management_system.exception.DuplicateResourceException;
import management_system.exception.ResourceNotFoundException;
import management_system.mapper.StudentMapper;
import management_system.model.Student;
import management_system.repository.StudentRepository;
import management_system.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public StudentResponseDto createStudent(StudentCreateDto createDto) {
        if (studentRepository.existsByStudentCode(createDto.studentCode())) {
            throw new DuplicateResourceException("Student code already exists");
        }
        Student student = studentMapper.toEntity(createDto);
        return studentMapper.toDto(studentRepository.save(student));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(UUID id) {
        return studentMapper.toDto(getStudentEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentByCode(String studentCode) {
        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentResponseDto updateStudent(UUID id, StudentCreateDto updateDto) {
        Student student = getStudentEntity(id);
        student.setFirstName(updateDto.firstName());
        student.setLastName(updateDto.lastName());
        student.setEmail(updateDto.email());
        return studentMapper.toDto(studentRepository.save(student));
    }

    @Override
    @Transactional
    public void deleteStudent(UUID id) {
        studentRepository.delete(getStudentEntity(id));
    }

    // Helper method
    private Student getStudentEntity(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }
}