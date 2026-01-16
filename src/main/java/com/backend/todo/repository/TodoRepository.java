package com.backend.todo.repository;

import com.backend.todo.model.Todo;
import com.backend.todo.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUserUsername(String username);
}
