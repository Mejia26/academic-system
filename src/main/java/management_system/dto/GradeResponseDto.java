package management_system.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Response object containing a student's grade record")
public record GradeResponseDto(
        @Schema(description = "Unique identifier of the grade record", example = "777e8400-e29b-41d4-a716-446655440000") 
        UUID id,
        
        @Schema(description = "UUID of the classroom", example = "123e4567-e89b-12d3-a456-426614174000") 
        UUID classroomId,
        
        @Schema(description = "UUID of the student", example = "987e6543-e21b-12d3-a456-426614174000") 
        UUID studentId,
        
        @Schema(description = "Academic period or term", example = "Midterm Exam") 
        String period,
        
        @Schema(description = "Numeric score", example = "95.50") 
        BigDecimal score,
        
        @Schema(description = "Observations or feedback", example = "Excellent performance in problem-solving") 
        String observations
) {}