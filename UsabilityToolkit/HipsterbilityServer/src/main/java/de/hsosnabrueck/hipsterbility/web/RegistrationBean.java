package de.hsosnabrueck.hipsterbility.web;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Locale;

/**
 * Created by Albert on 29.09.2014.
 */
@ManagedBean(name="regBean")
@SessionScoped()
public class RegistrationBean {
    private String username;
    private String password;
    private String confirmpassword;
    private String firstname;
    private String lastname;
    private String email;
    private String confirmemail;
    private String country;
    private Locale locale;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmemail() {
        return confirmemail;
    }

    public void setConfirmemail(String confirmemail) {
        this.confirmemail = confirmemail;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private void addMessage(FacesMessage message){
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String addUser(){
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "User Registration Successful!!!", null));
        return "success";
    }

}