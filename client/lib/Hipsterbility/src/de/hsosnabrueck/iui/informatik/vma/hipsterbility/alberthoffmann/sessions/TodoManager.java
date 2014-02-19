package de.hsosnabrueck.iui.informatik.vma.hipsterbility.alberthoffmann.sessions;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Albert Hoffmann on 19.02.14.
 * Singlton manager class to manage todos.
 * Adding todos to sessions would make intents with session objects as payload much harder.
 */
public class TodoManager {

    //TODO: further implementation

    private static TodoManager instance = new TodoManager();
    private ArrayList<Todo> todos;

    private TodoManager() {
        this.todos = new ArrayList<Todo>();
    }

    public static TodoManager getInstance(){
        return instance;
    }

    private ArrayList<Todo> getTodosForSession(Session session){
        ArrayList<Todo> results = new ArrayList<Todo>();
        for(Todo t : this.todos){
            if(t.getSession().equals(session)){
                results.add(t);
            }
        }
        return results;
    }
}
