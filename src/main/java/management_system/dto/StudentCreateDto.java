package management_system.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload for registering a new student in the global system registry")
public record StudentCreateDto(
        @Schema(description = "First name of the student", example = "Alice") 
        String firstName,
        
        @Schema(description = "Last name of the student", example = "Smith") 
        String lastName,
        
        @Schema(description = "Unique identification code or matricula for the student", example = "STU-2026-001") 
        String studentCode,
        
        @Schema(description = "Optional email address of the student", example = "alice.smith@student.edu") 
        String email
) {}