package mvc.model;

import java.util.Date;
import java.util.Objects;

/**
 * @author akirakozov
 */
public class Todo {
    private long id;
    private String name;
    private String description;
    private Status status;
    private long creationTime;

    public Todo(long id, TodoDTO dto) {
        this(id, dto.getName(), dto.getDescription());
    }

    public Todo(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.NOT_DONE;
        this.creationTime = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status.toString();
    }

    public void toggleStatus() {
        status = Status.values()[(status.ordinal() + 1) % 2];
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo)) return false;
        Todo todo = (Todo) o;
        return id == todo.id &&
                name.equals(todo.name) &&
                Objects.equals(description, todo.description) &&
                creationTime == todo.creationTime &&
                status == todo.status;
    }


    private enum Status {
        DONE, NOT_DONE
    }

}
