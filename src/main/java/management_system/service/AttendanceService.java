package management_system.service;


import java.util.List;
import java.util.UUID;

import management_system.dto.AttendanceCreateDto;
import management_system.dto.AttendanceResponseDto;

public interface AttendanceService {
    // Records attendance for a single student
    AttendanceResponseDto recordAttendance(AttendanceCreateDto createDto);
    
    // Records attendance for multiple students at once (Batch processing)
    List<AttendanceResponseDto> recordBatchAttendance(List<AttendanceCreateDto> createDtos);
    
    List<AttendanceResponseDto> getAttendanceByClassroom(UUID classroomId);
    List<AttendanceResponseDto> getAttendanceByStudentInClassroom(UUID classroomId, UUID studentId);
    
    AttendanceResponseDto updateAttendance(UUID id, AttendanceCreateDto updateDto);
}