package management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import management_system.dto.AttendanceCreateDto;
import management_system.dto.AttendanceResponseDto;
import management_system.model.Attendance;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttendanceMapper {
    
    @Mapping(source = "classroom.id", target = "classroomId")
    @Mapping(source = "student.id", target = "studentId")
    AttendanceResponseDto toDto(Attendance attendance);

    @Mapping(target = "classroom", ignore = true)
    @Mapping(target = "student", ignore = true)
    Attendance toEntity(AttendanceCreateDto createDto);
}