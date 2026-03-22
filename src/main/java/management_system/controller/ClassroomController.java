package management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import management_system.dto.ClassroomCreateDto;
import management_system.dto.ClassroomResponseDto;
import management_system.dto.StudentResponseDto;
import management_system.service.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/classrooms")
@RequiredArgsConstructor
@Tag(name = "Classrooms", description = "Classroom creation and student enrollment")
public class ClassroomController {

    private final ClassroomService classroomService;

    @PostMapping
    @Operation(summary = "Create a new classroom")
    public ResponseEntity<ClassroomResponseDto> createClassroom(@Valid @RequestBody ClassroomCreateDto request) {
        return new ResponseEntity<>(classroomService.createClassroom(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponseDto> getClassroomById(@PathVariable UUID id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ClassroomResponseDto>> getClassroomsByTeacher(@PathVariable UUID teacherId) {
        return ResponseEntity.ok(classroomService.getClassroomsByTeacher(teacherId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomResponseDto> updateClassroom(@PathVariable UUID id, @Valid @RequestBody ClassroomCreateDto request) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable UUID id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }

    // --- STUDENT ENROLLMENT ENDPOINTS ---

    @PostMapping("/{classroomId}/students/{studentId}")
    @Operation(summary = "Enroll a student in a classroom")
    public ResponseEntity<Void> addStudentToClassroom(@PathVariable UUID classroomId, @PathVariable UUID studentId) {
        classroomService.addStudentToClassroom(classroomId, studentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{classroomId}/students/{studentId}")
    @Operation(summary = "Remove a student from a classroom")
    public ResponseEntity<Void> removeStudentFromClassroom(@PathVariable UUID classroomId, @PathVariable UUID studentId) {
        classroomService.removeStudentFromClassroom(classroomId, studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{classroomId}/students")
    @Operation(summary = "List all students enrolled in a classroom")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByClassroom(@PathVariable UUID classroomId) {
        return ResponseEntity.ok(classroomService.getStudentsByClassroom(classroomId));
    }
}