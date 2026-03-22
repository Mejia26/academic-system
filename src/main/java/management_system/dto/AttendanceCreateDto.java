package management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.UUID;

import management_system.enums.AttendanceStatus;

@Schema(description = "Payload for recording a student's attendance in a specific classroom")
public record AttendanceCreateDto(
        @Schema(description = "UUID of the classroom", example = "123e4567-e89b-12d3-a456-426614174000") 
        UUID classroomId,
        
        @Schema(description = "UUID of the student", example = "987e6543-e21b-12d3-a456-426614174000") 
        UUID studentId,
        
        @Schema(description = "Date of the attendance", example = "2026-03-21") 
        LocalDate date,
        
        @Schema(description = "Status of the attendance (e.g., PRESENT, ABSENT, LATE, EXCUSED)", example = "PRESENT") 
        AttendanceStatus status,
        
        @Schema(description = "Optional notes or remarks regarding the attendance", example = "Student arrived 15 minutes late due to traffic") 
        String notes
) {}