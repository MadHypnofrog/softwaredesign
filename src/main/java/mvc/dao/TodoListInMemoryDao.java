package mvc.dao;

import mvc.model.Todo;
import mvc.model.TodoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TodoListInMemoryDao implements TodoListDao {

    private final AtomicLong nextId = new AtomicLong(0);
    private final Map<Long, Todo> todoList;

    public TodoListInMemoryDao() {
        todoList = new HashMap<>();
    }

    @Override
    public List<Todo> getTodoList() {
        return Collections.unmodifiableList(new ArrayList<>(todoList.values()));
    }

    @Override
    public Optional<Todo> findTodoById(long id) {
        return Optional.ofNullable(todoList.get(id));
    }

    @Override
    public void addTodo(TodoDTO todo) {
        long id = nextId.incrementAndGet();
        todoList.put(id, new Todo(id, todo));
    }

    @Override
    public void removeTodo(long id) {
        if (!todoList.containsKey(id)) {
            throw new IllegalArgumentException("id not found in the list!");
        }
        todoList.remove(id);
    }

    @Override
    public void toggleTodo(long id) {
        Optional.ofNullable(todoList.get(id)).ifPresent(Todo::toggleStatus);
    }
}
