package management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.dto.ClassroomCreateDto;
import management_system.dto.ClassroomResponseDto;
import management_system.dto.StudentResponseDto;
import management_system.exception.ResourceNotFoundException;
import management_system.mapper.ClassroomMapper;
import management_system.mapper.StudentMapper;
import management_system.model.Classroom;
import management_system.model.Student;
import management_system.model.User;
import management_system.repository.ClassroomRepository;
import management_system.repository.StudentRepository;
import management_system.repository.UserRepository;
import management_system.service.ClassroomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ClassroomMapper classroomMapper;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public ClassroomResponseDto createClassroom(ClassroomCreateDto createDto) {
        User teacher = userRepository.findById(createDto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        Classroom classroom = classroomMapper.toEntity(createDto);
        classroom.setTeacher(teacher);
        return classroomMapper.toDto(classroomRepository.save(classroom));
    }

    @Override
    @Transactional(readOnly = true)
    public ClassroomResponseDto getClassroomById(UUID id) {
        return classroomMapper.toDto(getClassroomEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDto> getClassroomsByTeacher(UUID teacherId) {
        return classroomRepository.findAllByTeacherId(teacherId).stream()
                .map(classroomMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClassroomResponseDto updateClassroom(UUID id, ClassroomCreateDto updateDto) {
        Classroom classroom = getClassroomEntity(id);
        classroom.setName(updateDto.name());
        classroom.setDescription(updateDto.description());
        return classroomMapper.toDto(classroomRepository.save(classroom));
    }

    @Override
    @Transactional
    public void deleteClassroom(UUID id) {
        classroomRepository.delete(getClassroomEntity(id));
    }

    @Override
    @Transactional
    public void addStudentToClassroom(UUID classroomId, UUID studentId) {
        Classroom classroom = getClassroomEntity(classroomId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        
        classroom.getStudents().add(student);
        classroomRepository.save(classroom);
    }

    @Override
    @Transactional
    public void removeStudentFromClassroom(UUID classroomId, UUID studentId) {
        Classroom classroom = getClassroomEntity(classroomId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        
        classroom.getStudents().remove(student);
        classroomRepository.save(classroom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsByClassroom(UUID classroomId) {
        return getClassroomEntity(classroomId).getStudents().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    private Classroom getClassroomEntity(UUID id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));
    }
}