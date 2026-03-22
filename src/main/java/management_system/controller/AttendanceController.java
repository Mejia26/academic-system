package management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import management_system.dto.AttendanceCreateDto;
import management_system.dto.AttendanceResponseDto;
import management_system.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/attendances")
@RequiredArgsConstructor
@Tag(name = "Attendance Tracking", description = "Record daily attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @Operation(summary = "Record attendance for a single student")
    public ResponseEntity<AttendanceResponseDto> recordAttendance(@Valid @RequestBody AttendanceCreateDto request) {
        return new ResponseEntity<>(attendanceService.recordAttendance(request), HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    @Operation(summary = "Record attendance for multiple students at once")
    public ResponseEntity<List<AttendanceResponseDto>> recordBatchAttendance(@Valid @RequestBody List<AttendanceCreateDto> requests) {
        return new ResponseEntity<>(attendanceService.recordBatchAttendance(requests), HttpStatus.CREATED);
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByClassroom(@PathVariable UUID classroomId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByClassroom(classroomId));
    }

    @GetMapping("/classroom/{classroomId}/student/{studentId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByStudent(
            @PathVariable UUID classroomId, @PathVariable UUID studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudentInClassroom(classroomId, studentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(
            @PathVariable UUID id, @Valid @RequestBody AttendanceCreateDto request) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, request));
    }
}