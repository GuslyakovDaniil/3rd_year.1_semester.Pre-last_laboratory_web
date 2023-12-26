package com.app.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brands extends BaseEntity {
    @OneToMany(mappedBy = "brand",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Models> model;
    private String name;

    public Set<Models> getModel() {
        return model;
    }
    public void setModel(Set<Models> model) {
        this.model = model;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    protected Brands() {
    }

}
