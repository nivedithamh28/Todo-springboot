package com.backend.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.todo.dto.TodoRequestDTO;
import com.backend.todo.dto.TodoResponseDTO;
import com.backend.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // USER can create todo
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public TodoResponseDTO createTodo(@RequestBody TodoRequestDTO dto) {
        return todoService.createTodo(dto);
    }

    // USER can view todos
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<TodoResponseDTO> getAllTodos() {
        return todoService.getAllTodos();
    }

    // USER can view single todo
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public TodoResponseDTO getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    // USER can update todo
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public TodoResponseDTO updateTodo(@PathVariable Long id,
                                     @RequestBody TodoRequestDTO dto) {
        return todoService.updateTodo(id, dto);
    }

    // ONLY ADMIN can delete todo
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}
