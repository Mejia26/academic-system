package management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.UUID;

import management_system.enums.AttendanceStatus;

import java.util.UUID;

//Notice we only send the teacherId to the frontend, not the whole User object, to avoid infinite loops.
@Schema(description = "Response object containing classroom details. Notice we only send the teacherId to avoid infinite JSON loops.")
public record ClassroomResponseDto(
        @Schema(description = "Unique identifier of the classroom", example = "123e4567-e89b-12d3-a456-426614174000") 
        UUID id,
        
        @Schema(description = "Name of the classroom", example = "Advanced Mathematics 101") 
        String name,
        
        @Schema(description = "Description of the classroom", example = "Introduction to calculus and linear algebra") 
        String description,
        
        @Schema(description = "UUID of the teacher assigned to this classroom", example = "111e4567-e89b-12d3-a456-426614174000") 
        UUID teacherId
) {}