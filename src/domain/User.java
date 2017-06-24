package domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class User implements Serializable {
    
    private static final long serialVersionUID = 1520961851058396786L;
    private int id;
    private String username;
    private String password;
    private String email;
    private MultipartFile facephoto;
    
    public User() {
    }
    
    public User(int id, String username, String password, String email,
    		MultipartFile facephoto) {
    	this.id = id;
    	this.username = username;
    	this.password = password;
    	this.email = email;
    	this.facephoto = facephoto;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public MultipartFile getFacephoto() {
    	return facephoto;
    }
    public void setFacephoto(MultipartFile facephoto) {
    	this.facephoto = facephoto;
    }
}
