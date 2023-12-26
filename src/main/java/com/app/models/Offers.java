package com.app.models;

import com.app.enums.Engine;
import com.app.enums.Transmission;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "offers")
public class Offers extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Users seller;
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Models model;
    private String description;
    private Engine engine;
    private String imageUrl;
    private Integer mileage;
    private BigDecimal price;
    private Transmission transmission;
    private Integer year;

    public Offers() {
    }

    public Models getModel() {
        return model;
    }

    public void setModel(Models model) {
        this.model = model;
    }

    public Users getSeller() {
        return seller;
    }

    public void setSeller(Users seller) {
        this.seller = seller;
    }
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "engine")
    @Enumerated(EnumType.STRING)
    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    @Column(name = "image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Column(name = "mileage")
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
    @Column(name = "price", precision = 19, scale = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @Column(name = "transmission")
    @Enumerated(EnumType.STRING)
    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }
    @Column(name = "year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
