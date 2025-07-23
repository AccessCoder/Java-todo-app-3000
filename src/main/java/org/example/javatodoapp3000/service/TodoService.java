package org.example.javatodoapp3000.service;

import lombok.RequiredArgsConstructor;
import org.example.javatodoapp3000.exceptions.TodoNotFoundException;
import org.example.javatodoapp3000.model.Todo;
import org.example.javatodoapp3000.model.TodoDto;
import org.example.javatodoapp3000.repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepo repo;
    private final IdService idService;

    public TodoService(TodoRepo repo, IdService idService) {
        this.repo = repo;
        this.idService = idService;
    }

    public List<Todo> getAllTodo() {
        return repo.findAll();
    }

    public Todo createNewTodo(TodoDto newTodo) {
        Todo todo = new Todo(
                idService.generateId(),
                newTodo.description(),
                newTodo.status()
        );
        repo.save(todo);
        return todo;
    }

    public Todo getTodoById(String id) throws TodoNotFoundException {
         return repo.findById(id)
                 .orElseThrow(() -> new TodoNotFoundException("Todo with Id: " + id + " not found!"));
    }

    public Todo updateTodo(Todo updatedTodo) throws TodoNotFoundException {
        if (!repo.existsById(updatedTodo.id())){
            throw new TodoNotFoundException("Todo with Id: " + updatedTodo.id() + " not found!");
        }
        repo.save(updatedTodo);
        return updatedTodo;
    }

    public Todo deleteTodo(String id) throws TodoNotFoundException {
        if (!repo.existsById(id)){
            throw new TodoNotFoundException("Todo with Id: " + id + " not found!");
        }
        Todo deletedTodo = repo.findById(id).get();
        repo.deleteById(id);
        return deletedTodo;
    }
}
