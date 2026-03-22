package management_system.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Response object containing student information")
public record StudentResponseDto(
        @Schema(description = "Unique internal identifier of the student", example = "987e6543-e21b-12d3-a456-426614174000") 
        UUID id,
        
        @Schema(description = "First name of the student", example = "Alice") 
        String firstName,
        
        @Schema(description = "Last name of the student", example = "Smith") 
        String lastName,
        
        @Schema(description = "Unique identification code or matricula", example = "STU-2026-001") 
        String studentCode,
        
        @Schema(description = "Email address of the student", example = "alice.smith@student.edu") 
        String email
) {}