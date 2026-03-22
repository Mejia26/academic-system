package management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import management_system.dto.ClassroomCreateDto;
import management_system.dto.ClassroomResponseDto;
import management_system.model.Classroom;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClassroomMapper {
    
    // We map the teacher.id from the Entity to the teacherId in the DTO
    @Mapping(source = "teacher.id", target = "teacherId")
    ClassroomResponseDto toDto(Classroom classroom);

    // When creating, we ignore the teacher object mapping because we usually fetch it from the DB using the ID in the Service layer
    @Mapping(target = "teacher", ignore = true) 
    @Mapping(target = "students", ignore = true)
    Classroom toEntity(ClassroomCreateDto createDto);
}