package org.example.javatodoapp3000.controller;

import lombok.RequiredArgsConstructor;
import org.example.javatodoapp3000.exceptions.TodoNotFoundException;
import org.example.javatodoapp3000.model.Todo;
import org.example.javatodoapp3000.model.TodoDto;
import org.example.javatodoapp3000.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAllTodo(){
        return service.getAllTodo();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable String id) throws TodoNotFoundException {
        return service.getTodoById(id);
    }

    @PostMapping
    public Todo createNewTodo(@RequestBody TodoDto newTodo){
        return service.createNewTodo(newTodo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@RequestBody Todo updatedTodo) throws TodoNotFoundException {
        return service.updateTodo(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public Todo deleteTodo(@PathVariable String id) throws TodoNotFoundException {
        return service.deleteTodo(id);
    }
}
