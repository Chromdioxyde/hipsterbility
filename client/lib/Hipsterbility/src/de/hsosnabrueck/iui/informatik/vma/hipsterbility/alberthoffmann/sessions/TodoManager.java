package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Albert Hoffmann on 19.02.14.
 * Singlton manager class to manage todos.
 * Adding todos to sessions would make intents with session objects as payload much harder.
 */
public class TodoManager {

    //TODO: further implementation

    private static TodoManager instance = new TodoManager();
    private HashMap<Session, ArrayList<Todo>> todos;

    private TodoManager() {
        this.todos = new HashMap<Session, ArrayList<Todo>>();
    }

    public static TodoManager getInstance(){
        return instance;
    }

    public ArrayList<Todo> getTodos(Session session){
        return this.todos.get(session);
    }

    public void addTodo(Session s, Todo t){
        this.todos.get(s).add(t);
    }

    public void setTodos(Session s, ArrayList<Todo> tlist){
        this.todos.put(s, tlist);
    }
}
