package com.backend.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.backend.todo.dto.TodoRequestDTO;
import com.backend.todo.dto.TodoResponseDTO;
import com.backend.todo.exception.ResourceNotFoundException;
import com.backend.todo.exception.UnauthorizedException;
import com.backend.todo.mapper.TodoMapper;
import com.backend.todo.model.Todo;
import com.backend.todo.model.User;
import com.backend.todo.repository.TodoRepository;
import com.backend.todo.repository.UserRepository;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
  
       private final TodoMapper todoMapper;

        @Autowired
    private UserRepository userRepository;

    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

 private String getCurrentUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

       public TodoResponseDTO createTodo(TodoRequestDTO dto) {
        String username = getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setCompleted(false);
        todo.setUser(user);

        return todoMapper.toResponseDTO(todoRepository.save(todo));
    }

   public List<TodoResponseDTO> getAllTodos() {
    String username = getCurrentUsername();

    return todoRepository.findByUserUsername(username)
            .stream()
            .map(todoMapper::toResponseDTO) // Todo → DTO
            .toList();
}


    
   public TodoResponseDTO getTodoById(Long id) {
    String username = getCurrentUsername();

    Todo todo = todoRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Todo not found"));

    if (!todo.getUser().getUsername().equals(username)) {
        throw new UnauthorizedException("Access denied");
    }

    return todoMapper.toResponseDTO(todo);
}


  
public TodoResponseDTO updateTodo(Long id, TodoRequestDTO dto) {
    String username = getCurrentUsername();

    Todo todo = todoRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Todo not found"));

    if (!todo.getUser().getUsername().equals(username)) {
        throw new UnauthorizedException("Access denied");
    }

    todo.setTitle(dto.getTitle());
    todo.setCompleted(dto.isCompleted());

    return todoMapper.toResponseDTO(todoRepository.save(todo));
}


   public void deleteTodo(Long id) {
    Todo todo = todoRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Todo not found"));

    todoRepository.delete(todo);
}

}
