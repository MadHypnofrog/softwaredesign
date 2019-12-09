package mvc.controller;

import mvc.dao.TodoListDao;
import mvc.model.Todo;
import mvc.model.TodoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TodoListController {
    private final TodoListDao listDao;

    public TodoListController(TodoListDao listDao) {
        this.listDao = listDao;
    }

    @GetMapping("/todo-list")
    public String getTodoList(Model model) {
        prepareModelMap(model, listDao.getTodoList());
        return "todo_list";
    }

    @PostMapping("/add-todo")
    public String addTodo(@ModelAttribute("todo") TodoDTO todo) {
        listDao.addTodo(todo);
        return "redirect:/todo-list";
    }

    @PostMapping("/remove-todo")
    public String removeTodoList(@RequestParam("id") long id) {
        listDao.removeTodo(id);
        return "redirect:/todo-list";
    }

    @PostMapping("/toggle-todo")
    public String toggleTodo(@RequestParam("id") long id) {
        listDao.toggleTodo(id);
        return "redirect:/todo-list";
    }

    private void prepareModelMap(Model model, List<Todo> todoList) {
        model.addAttribute("todo_entities", todoList);
        model.addAttribute("todo", new TodoDTO());
    }
}
