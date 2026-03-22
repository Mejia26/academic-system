package management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import management_system.dto.GradeCreateDto;
import management_system.dto.GradeResponseDto;
import management_system.model.Grade;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GradeMapper {

    @Mapping(source = "classroom.id", target = "classroomId")
    @Mapping(source = "student.id", target = "studentId")
    GradeResponseDto toDto(Grade grade);

    @Mapping(target = "classroom", ignore = true)
    @Mapping(target = "student", ignore = true)
    Grade toEntity(GradeCreateDto createDto);
}