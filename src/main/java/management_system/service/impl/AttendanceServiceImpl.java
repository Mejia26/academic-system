package management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.dto.AttendanceCreateDto;
import management_system.dto.AttendanceResponseDto;
import management_system.exception.DuplicateResourceException;
import management_system.exception.ResourceNotFoundException;
import management_system.mapper.AttendanceMapper;
import management_system.model.Attendance;
import management_system.model.Classroom;
import management_system.model.Student;
import management_system.repository.AttendanceRepository;
import management_system.repository.ClassroomRepository;
import management_system.repository.StudentRepository;
import management_system.service.AttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    @Transactional
    public AttendanceResponseDto recordAttendance(AttendanceCreateDto createDto) {
        if (attendanceRepository.existsByClassroomIdAndStudentIdAndDate(
                createDto.classroomId(), createDto.studentId(), createDto.date())) {
            throw new DuplicateResourceException("Attendance already recorded for this date");
        }

        Attendance attendance = attendanceMapper.toEntity(createDto);
        attendance.setClassroom(getClassroom(createDto.classroomId()));
        attendance.setStudent(getStudent(createDto.studentId()));

        return attendanceMapper.toDto(attendanceRepository.save(attendance));
    }

    @Override
    @Transactional
    public List<AttendanceResponseDto> recordBatchAttendance(List<AttendanceCreateDto> createDtos) {
        log.info("Processing batch attendance for {} students", createDtos.size());
        // Using streams to map DTOs to Entities, checking duplicates along the way
        List<Attendance> attendances = createDtos.stream().map(dto -> {
            if (attendanceRepository.existsByClassroomIdAndStudentIdAndDate(dto.classroomId(), dto.studentId(), dto.date())) {
                throw new DuplicateResourceException("Duplicate attendance found in batch for student: " + dto.studentId());
            }
            Attendance entity = attendanceMapper.toEntity(dto);
            entity.setClassroom(getClassroom(dto.classroomId()));
            entity.setStudent(getStudent(dto.studentId()));
            return entity;
        }).collect(Collectors.toList());

        // Batch save
        return attendanceRepository.saveAll(attendances).stream()
                .map(attendanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendanceByClassroom(UUID classroomId) {
        return attendanceRepository.findAllByClassroomId(classroomId).stream()
                .map(attendanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendanceByStudentInClassroom(UUID classroomId, UUID studentId) {
        return attendanceRepository.findAllByClassroomIdAndStudentId(classroomId, studentId).stream()
                .map(attendanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AttendanceResponseDto updateAttendance(UUID id, AttendanceCreateDto updateDto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
        
        attendance.setStatus(updateDto.status());
        attendance.setNotes(updateDto.notes());
        return attendanceMapper.toDto(attendanceRepository.save(attendance));
    }

    // Helpers
    private Classroom getClassroom(UUID id) {
        return classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));
    }
    private Student getStudent(UUID id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }
}