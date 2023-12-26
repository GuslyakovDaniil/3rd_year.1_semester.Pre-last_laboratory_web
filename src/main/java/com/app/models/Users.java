package com.app.models;

import com.app.enums.Role;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
public class Users extends BaseEntity {
    private String email;
    private String first_name;
    private String username;
    private String last_name;
    private String password;
    private Boolean is_active;
    private List<Role> role;
    @OneToMany(mappedBy = "seller",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Offers> offers;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    public List<Role> getRoles() {
        return role;
    }


    public Users(String username, String encode, String email, String first_name, String last_name) {
        this.username = username;
        this.password = encode;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }



    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {this.email = email;}
    @Column(name = "first_name")
    public String getFirst_name() {return first_name;}
    public void setFirst_name(String first_name) {this.first_name = first_name;}
    @Column(name = "last_name")
    public String getLast_name() {return last_name;}
    public void setLast_name(String last_name) {this.last_name = last_name;}
    @Column(name = "password")
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public Set<Offers> getOffers() {return offers;}
    public void setOffers(Set<Offers> offers) {this.offers = offers;}
    @Column(name = "is_active")
    public Boolean getIs_active() {return is_active;}
    public void setIs_active(Boolean is_active) {this.is_active = is_active;}
    public Users() {this.role = new ArrayList<>();
    }


}
