package management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import management_system.dto.GradeCreateDto;
import management_system.dto.GradeResponseDto;
import management_system.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
@Tag(name = "Grades Management", description = "Evaluate students and track scores")
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    @Operation(summary = "Assign a grade to a student")
    public ResponseEntity<GradeResponseDto> assignGrade(@Valid @RequestBody GradeCreateDto request) {
        return new ResponseEntity<>(gradeService.assignGrade(request), HttpStatus.CREATED);
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<GradeResponseDto>> getGradesByClassroom(@PathVariable UUID classroomId) {
        return ResponseEntity.ok(gradeService.getGradesByClassroom(classroomId));
    }

    @GetMapping("/classroom/{classroomId}/student/{studentId}")
    public ResponseEntity<List<GradeResponseDto>> getGradesByStudent(
            @PathVariable UUID classroomId, @PathVariable UUID studentId) {
        return ResponseEntity.ok(gradeService.getGradesByStudentInClassroom(classroomId, studentId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing grade")
    public ResponseEntity<GradeResponseDto> updateGrade(
            @PathVariable UUID id, @Valid @RequestBody GradeCreateDto request) {
        return ResponseEntity.ok(gradeService.updateGrade(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable UUID id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}