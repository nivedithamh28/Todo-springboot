package com.backend.todo.mapper;




import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


import com.backend.todo.dto.TodoRequestDTO;
import com.backend.todo.dto.TodoResponseDTO;
import com.backend.todo.model.Todo;

@Component
@Mapper(componentModel = "spring")
public interface TodoMapper {

    @Mapping(target = "id", ignore = true)
    Todo toEntity(TodoRequestDTO dto);

    TodoResponseDTO toResponseDTO(Todo todo);
}
