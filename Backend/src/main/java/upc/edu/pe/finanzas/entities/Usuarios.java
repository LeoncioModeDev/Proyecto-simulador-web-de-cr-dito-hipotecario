package upc.edu.pe.finanzas.entities;

import jakarta.persistence.*;

@Entity
@Table(name="Usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "pass_hash", length = 255, nullable = false)
    private String pass_hash;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    public Usuarios() {}

    public Usuarios(String username, String pass_hash, String email) {
        this.username = username;
        this.pass_hash = pass_hash;
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass_hash() {
        return pass_hash;
    }

    public void setPass_hash(String pass_hash) {
        this.pass_hash = pass_hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}