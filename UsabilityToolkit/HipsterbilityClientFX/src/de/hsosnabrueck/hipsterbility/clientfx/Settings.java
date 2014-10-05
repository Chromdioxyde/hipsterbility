package de.hsosnabrueck.hipsterbility.clientfx;

import javafx.beans.property.*;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by Albert on 03.10.2014.
 */
@Singleton
public class Settings {

    private Preferences prefs = Preferences.userNodeForPackage(HipsterbilityClientApp.class);

    private StringProperty server;
    private IntegerProperty port;
    private BooleanProperty ssl;
    private StringProperty user;
    private StringProperty password;
    private BooleanProperty savePassword;

    @PostConstruct
    public void init(){
        // Crete properties, read values from preferences and add listener to store chages
        server = new SimpleStringProperty(prefs.get("server","localhost"));
        server.addListener(l -> prefs.put("server", getServer()));
        port = new SimpleIntegerProperty(prefs.getInt("port", 8080));
        port.addListener(l -> prefs.putInt("port", getPort()));
        ssl = new SimpleBooleanProperty(prefs.getBoolean("ssl", false));
        ssl.addListener(l -> prefs.putBoolean("ssl", getSsl()));
        user = new SimpleStringProperty(prefs.get("user", ""));
        user.addListener(l -> prefs.put("user",getUser()));
        savePassword = new SimpleBooleanProperty(prefs.getBoolean("savepassword", false));
        savePassword.addListener(l -> prefs.putBoolean("savepassword", getSavePassword()));
        // save and load password value only if savepassword value is true, else store an empty string
        // to overwrite the previous value
        password = new SimpleStringProperty(savePassword.get() ? prefs.get("password", "") : "");
        password.addListener(l -> prefs.put("password", savePassword.get() ? getPassword() : ""));
    }

    public void clear(){
        try {
            prefs.clear();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public String getServer() {
        return server.get();
    }

    public StringProperty serverProperty() {
        return server;
    }

    public void setServer(String server) {
        this.server.set(server);
    }

    public int getPort() {
        return port.get();
    }

    public IntegerProperty portProperty() {
        return port;
    }

    public void setPort(int port) {
        this.port.set(port);
    }

    public boolean getSsl() {
        return ssl.get();
    }

    public BooleanProperty sslProperty() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl.set(ssl);
    }

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
        prefs.put("user", user);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
        if(savePassword.get()) prefs.put("password", password);
    }

    public boolean getSavePassword() {
        return savePassword.get();
    }

    public BooleanProperty savePasswordProperty() {
        return savePassword;
    }

    public void setSavePassword(boolean savePassword) {
        this.savePassword.set(savePassword);
    }
}
