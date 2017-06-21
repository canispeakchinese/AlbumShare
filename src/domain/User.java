package domain;

import java.io.Serializable;

public class User implements Serializable {
    
    private static final long serialVersionUID = 1520961851058396786L;
    private long id;
    private String username;
    private String password;
    private String email;
    private String facephoto;
    
    public User() {
    }
    
    public User(long id, String username, String password, String email,
    		String facephoto) {
    	this.id = id;
    	this.username = username;
    	this.password = password;
    	this.email = email;
    	this.facephoto = facephoto;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
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
    public String getEmail() {
    	return email;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    public String getFacephoto() {
    	return facephoto;
    }
    public void setFacephoto(String facephoto) {
    	this.facephoto = facephoto;
    }
}
