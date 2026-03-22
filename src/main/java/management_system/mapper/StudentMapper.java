package management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import management_system.dto.StudentCreateDto;
import management_system.dto.StudentResponseDto;
import management_system.model.Student;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
    StudentResponseDto toDto(Student student);
    Student toEntity(StudentCreateDto createDto);
}