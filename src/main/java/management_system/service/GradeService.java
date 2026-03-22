package management_system.service;

import java.util.List;
import java.util.UUID;

import management_system.dto.GradeCreateDto;
import management_system.dto.GradeResponseDto;

public interface GradeService {
    GradeResponseDto assignGrade(GradeCreateDto createDto);
    
    List<GradeResponseDto> getGradesByClassroom(UUID classroomId);
    List<GradeResponseDto> getGradesByStudentInClassroom(UUID classroomId, UUID studentId);
    
    GradeResponseDto updateGrade(UUID id, GradeCreateDto updateDto);
    void deleteGrade(UUID id);
}