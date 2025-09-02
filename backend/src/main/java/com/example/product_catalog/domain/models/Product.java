package com.example.product_catalog.domain.models;

import com.example.product_catalog.domain.dtos.ProductUpdateDTO;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "tb_product")
@Table(name = "tb_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "image_url")
    private String imageUrl;

    public void update(ProductUpdateDTO productUpdateDTO){

        if (productUpdateDTO.name() != null  && !Objects.equals(productUpdateDTO.name(), this.name)){
            this.name = productUpdateDTO.name();
        }

        if (productUpdateDTO.description() != null && !Objects.equals(productUpdateDTO.description(), this.description)){
            this.description = productUpdateDTO.description();
        }

        if(productUpdateDTO.price() != null && !Objects.equals(productUpdateDTO.price(), this.price)){
            this.price = productUpdateDTO.price();
        }

        if(productUpdateDTO.quantity() != null && !Objects.equals(productUpdateDTO.quantity(), this.quantity)){
            this.quantity = productUpdateDTO.quantity();
        }

        if(productUpdateDTO.imageUrl() != null && !Objects.equals(productUpdateDTO.imageUrl(), this.imageUrl)){
            this.imageUrl = productUpdateDTO.imageUrl();
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
