package com.backend.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.todo.dto.TodoRequestDTO;
import com.backend.todo.dto.TodoResponseDTO;
import com.backend.todo.exception.ResourceNotFoundException;
import com.backend.todo.mapper.TodoMapper;
import com.backend.todo.model.Todo;
import com.backend.todo.repository.TodoRepository;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
       private final TodoMapper todoMapper;

    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

 


    public TodoResponseDTO createTodo(TodoRequestDTO dto) {
        Todo todo = todoMapper.toEntity(dto);
        return todoMapper.toResponseDTO(todoRepository.save(todo));
    }


    public List<TodoResponseDTO> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(todoMapper::toResponseDTO)
                .toList();
    }

    
    public TodoResponseDTO getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        return todoMapper.toResponseDTO(todo);
    }

  
    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO dto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todo.setTitle(dto.getTitle());
        todo.setCompleted(dto.isCompleted());

        return todoMapper.toResponseDTO(todoRepository.save(todo));
    }

     public void deleteTodo(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Todo not found with id: " + id));

        todoRepository.delete(todo);
    }
}
