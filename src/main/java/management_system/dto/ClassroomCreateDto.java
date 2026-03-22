package management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.UUID;

import management_system.enums.AttendanceStatus;

import java.util.UUID;

@Schema(description = "Payload for creating or updating a classroom")
public record ClassroomCreateDto(
        @Schema(description = "Name of the classroom or subject", example = "Advanced Mathematics 101") 
        String name,
        
        @Schema(description = "Brief description of the classroom's purpose or syllabus", example = "Introduction to calculus and linear algebra") 
        String description,
        
        @Schema(description = "UUID of the teacher assigned to this classroom", example = "111e4567-e89b-12d3-a456-426614174000") 
        UUID teacherId
) {}