package com.app.models;

import com.app.enums.Category;
import jakarta.persistence.*;
import java.util.Date;
@Entity
@Table(name = "models")
public class Models extends BaseEntity {
    private Category category;
    private Integer end_year;
    private String image_url;
    private String name;
    private Integer start_year;
    @OneToOne(mappedBy = "model",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Offers offer;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brands brand;
    public Models() {}
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    public Category getCategory() {return category;}
    public void setCategory(Category category) {this.category = category;}

    public Brands getBrand() {
        return brand;
    }
    public void setBrand(Brands brand) {
        this.brand = brand;
    }
    public Offers getOffer() {
        return offer;
    }
    public void setOffer(Offers offers) {
        this.offer = offer;
    }
    @Column(name = "end_year")
    public Integer getEnd_year() {
        return end_year;
    }
    public void setEnd_year(Integer end_year) {
        this.end_year = end_year;
    }
    @Column(name = "image_url")
    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "start_year")
    public Integer getStart_year() {
        return start_year;
    }
    public void setStart_year(Integer start_year) {
        this.start_year = start_year;
    }

    public void setBrand_id(int id) {
    }
}