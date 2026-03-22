package management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import management_system.dto.StudentCreateDto;
import management_system.dto.StudentResponseDto;
import management_system.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student Registry", description = "Global management of students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(summary = "Register a new student")
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentCreateDto request) {
        return new ResponseEntity<>(studentService.createStudent(request), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all students")
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student information")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable UUID id, @Valid @RequestBody StudentCreateDto request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft-delete a student")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}