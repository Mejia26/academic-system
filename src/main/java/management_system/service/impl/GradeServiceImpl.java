package management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.dto.GradeCreateDto;
import management_system.dto.GradeResponseDto;
import management_system.exception.DuplicateResourceException;
import management_system.exception.ResourceNotFoundException;
import management_system.mapper.GradeMapper;
import management_system.model.Classroom;
import management_system.model.Grade;
import management_system.model.Student;
import management_system.repository.ClassroomRepository;
import management_system.repository.GradeRepository;
import management_system.repository.StudentRepository;
import management_system.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final GradeMapper gradeMapper;

    @Override
    @Transactional
    public GradeResponseDto assignGrade(GradeCreateDto createDto) {
        if (gradeRepository.existsByClassroomIdAndStudentIdAndPeriod(
                createDto.classroomId(), createDto.studentId(), createDto.period())) {
            throw new DuplicateResourceException("Grade already assigned for this period");
        }

        Grade grade = gradeMapper.toEntity(createDto);
        grade.setClassroom(getClassroom(createDto.classroomId()));
        grade.setStudent(getStudent(createDto.studentId()));

        return gradeMapper.toDto(gradeRepository.save(grade));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeResponseDto> getGradesByClassroom(UUID classroomId) {
        return gradeRepository.findAllByClassroomId(classroomId).stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeResponseDto> getGradesByStudentInClassroom(UUID classroomId, UUID studentId) {
        return gradeRepository.findAllByClassroomIdAndStudentId(classroomId, studentId).stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GradeResponseDto updateGrade(UUID id, GradeCreateDto updateDto) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found"));
        
        grade.setScore(updateDto.score());
        grade.setObservations(updateDto.observations());
        return gradeMapper.toDto(gradeRepository.save(grade));
    }

    @Override
    @Transactional
    public void deleteGrade(UUID id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found"));
        gradeRepository.delete(grade);
    }

    private Classroom getClassroom(UUID id) {
        return classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));
    }
    private Student getStudent(UUID id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }
}