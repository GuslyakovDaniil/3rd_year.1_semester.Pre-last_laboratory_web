package com.app.models;

import jakarta.persistence.*;
import com.app.enums.Role;

import java.util.List;

@Entity
@Table(name = "roles")
public class Roles extends BaseEntity {
    private Role name;

    public Roles(Role name) {
        this.name = name;
    }

    public Roles() {

    }

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    public Role getName() {
        return name;
    }

    public void setName(Role name) {
        this.name = name;
    }
}