package de.hsosnabrueck.hipsterbility.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Albert on 26.09.2014.
 */
@Entity(name = "TaskTemplate")
public class TaskTemplateEntity {

    private static final String TABLE_NAME = "TaskTemplate";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String description;
    @ManyToOne @JsonBackReference
    private TodoTemplateEntity todoTemplate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public TodoTemplateEntity getTodoTemplate() {
        return todoTemplate;
    }

    public void setTodoTemplate(TodoTemplateEntity todoTemplate) {
        this.todoTemplate = todoTemplate;
    }
}
