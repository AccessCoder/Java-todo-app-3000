package org.example.javatodoapp3000.service;

import org.example.javatodoapp3000.exceptions.TodoNotFoundException;
import org.example.javatodoapp3000.model.Todo;
import org.example.javatodoapp3000.model.TodoDto;
import org.example.javatodoapp3000.repository.TodoRepo;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {



    @Test
    void getAllTodo_ShouldReturnEmptyList_WhenCalledInitially() {
        //GIVEN
        TodoRepo mockrepo = mock(TodoRepo.class);
        IdService idService = mock(IdService.class);
        TodoService service = new TodoService(mockrepo, idService);
        when(mockrepo.findAll()).thenReturn(Collections.emptyList());
        List<Todo> expected = Collections.emptyList();
        //WHEN
        List<Todo> actual = service.getAllTodo();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getAllTodo_ShouldReturnListOfOneTodo_WhenCalledWithOneTodoInDatabase() {
        //GIVEN
        TodoRepo mockrepo = mock(TodoRepo.class);
        IdService idService = mock(IdService.class);
        TodoService service = new TodoService(mockrepo, idService);
        Todo newTodo = new Todo("1", "Test", "OPEN");
        when(mockrepo.findAll()).thenReturn(List.of(newTodo));
        List<Todo> expected = List.of(newTodo);
        //WHEN
        List<Todo> actual = service.getAllTodo();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void createNewTodo_ShouldReturnTodo_WhenCalledWithDTO() {
        //GIVEN
        TodoRepo mockrepo = mock(TodoRepo.class);
        IdService idService = mock(IdService.class);
        TodoService service = new TodoService(mockrepo, idService);

        TodoDto todoDto = new TodoDto("Test", "OPEN");
        Todo expected = new Todo("1", "Test", "OPEN");
        when(idService.generateId()).thenReturn("1");
        //WHEN
        Todo actual = service.createNewTodo(todoDto);
        //THEN
        assertEquals(expected, actual);
        verify(mockrepo).save(expected);
    }

    @Test
    void getTodoById_ShouldReturnSearchedTodo_WhenGivenValidId() throws TodoNotFoundException {
        //GIVEN
        TodoRepo mockrepo = mock(TodoRepo.class);
        IdService idService = mock(IdService.class);
        TodoService service = new TodoService(mockrepo, idService);

        Todo expected = new Todo("1", "Test", "OPEN");
        when(mockrepo.findById("1")).thenReturn(Optional.of(expected));
        //WHEN
        Todo actual = service.getTodoById(expected.id());
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getTodoById_ShouldThrowException_WhenGivenInValidId() {
        //GIVEN
        TodoRepo mockrepo = mock(TodoRepo.class);
        IdService idService = mock(IdService.class);
        TodoService service = new TodoService(mockrepo, idService);

        when(mockrepo.findById("1")).thenReturn(Optional.ofNullable(null));
        //WHEN
        try {
            service.getTodoById("1");
            fail();
        } catch (TodoNotFoundException e) {
            assertTrue(true);
        }

        //THEN

    }

    @Test
    void updateTodo_shouldReturnUpdatedTodo_WhenCalledWithValidId() throws TodoNotFoundException {
        //GIVEN
        TodoRepo mockrepo = mock(TodoRepo.class);
        IdService idService = mock(IdService.class);
        TodoService service = new TodoService(mockrepo, idService);

        Todo expected = new Todo("1", "Test", "OPEN");
        when(mockrepo.existsById("1")).thenReturn(true);
        //WHEN
        Todo actual = service.updateTodo(expected);
        //THEN
        assertEquals(expected, actual);
        verify(mockrepo).save(expected);
    }

    @Test
    void updateTodo_shouldThrowException_WhenCalledWithInValidId() {
        //GIVEN
        TodoRepo mockrepo = mock(TodoRepo.class);
        IdService idService = mock(IdService.class);
        TodoService service = new TodoService(mockrepo, idService);

        Todo todo = new Todo("1", "Test", "OPEN");
        when(mockrepo.existsById("1")).thenReturn(false);
        //WHEN
        try {
            service.updateTodo(todo);
            fail();
        } catch (TodoNotFoundException e) {
            assertTrue(true);
        }

        //THEN

    }

    @Test
    void deleteTodo_ShouldReturnDeletedTodo_WhenCalledWithValidId() throws TodoNotFoundException {
        //GIVEN
        TodoRepo mockrepo = mock(TodoRepo.class);
        IdService idService = mock(IdService.class);
        TodoService service = new TodoService(mockrepo, idService);

        Todo expected = new Todo("1", "Test", "OPEN");
        when(mockrepo.existsById("1")).thenReturn(true);
        when(mockrepo.findById("1")).thenReturn(Optional.of(expected));
        //WHEN
        Todo actual = service.deleteTodo(expected.id());
        //THEN
        assertEquals(expected, actual);
        verify(mockrepo).deleteById(expected.id());
    }

    @Test
    void deleteTodo_ShouldThrowException_WhenCalledWithInValidId() {
        //GIVEN
        TodoRepo mockrepo = mock(TodoRepo.class);
        IdService idService = mock(IdService.class);
        TodoService service = new TodoService(mockrepo, idService);

        Todo todo = new Todo("1", "Test", "OPEN");
        when(mockrepo.existsById("1")).thenReturn(false);
        //WHEN
        try {
            service.deleteTodo(todo.id());
            fail();
        } catch (TodoNotFoundException e) {
            assertTrue(true);
        }
    }
}