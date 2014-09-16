package de.hsosnabrueck.hipsterbility.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Albert on 14.09.2014.
 */
@Entity(name = "TestTemplate")
public class TestTemplateEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String name;
    private String description;
    @ManyToOne
    private TestAppEntity testApp;
    @OneToMany
    private List<TodoEntity> todos;

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

    public List<TodoEntity> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoEntity> todos) {
        this.todos = todos;
    }

    public TestAppEntity getTestApp() {
        return testApp;
    }

    public void setTestApp(TestAppEntity testApp) {
        this.testApp = testApp;
    }
}
