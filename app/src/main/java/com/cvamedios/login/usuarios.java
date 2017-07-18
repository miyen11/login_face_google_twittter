package com.cvamedios.login;

/**
 * Created by cvamedios on 18/07/17.
 */

public class usuarios {

    public String mombre;
    public String apellido;
    public  String user;
    public String password;

    public usuarios(String mombre, String apellido, String user, String password) {
        this.mombre = mombre;
        this.apellido = apellido;
        this.user = user;
        this.password = password;
    }

    public usuarios(){

    }

    public String getMombre() {
        return mombre;
    }

    public void setMombre(String mombre) {
        this.mombre = mombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
