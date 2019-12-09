package mvc.dao;

import mvc.model.Todo;
import mvc.model.TodoDTO;

import java.util.List;
import java.util.Optional;

public interface TodoListDao {

    List<Todo> getTodoList();

    Optional<Todo> findTodoById(long id);

    void addTodo(TodoDTO todo);

    void removeTodo(long id);

    void toggleTodo(long id);

}
