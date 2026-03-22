package management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.UUID;

import management_system.enums.AttendanceStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Payload for assigning a grade to a student for a specific period")
public record GradeCreateDto(
        @Schema(description = "UUID of the classroom", example = "123e4567-e89b-12d3-a456-426614174000") 
        UUID classroomId,
        
        @Schema(description = "UUID of the student", example = "987e6543-e21b-12d3-a456-426614174000") 
        UUID studentId,
        
        @Schema(description = "Academic period or term (e.g., Q1, Midterm, Final)", example = "Midterm Exam") 
        String period,
        
        @Schema(description = "Numeric score achieved by the student (0 to 100)", example = "95.50") 
        BigDecimal score,
        
        @Schema(description = "Optional observations or feedback from the teacher", example = "Excellent performance in problem-solving") 
        String observations
) {}