package com.example.product_catalog.domain.models;

import com.example.product_catalog.domain.dtos.ProductUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "tb_product")
@Table(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private String description;



    public void update(ProductUpdateDTO productUpdateDTO){

        if (productUpdateDTO.name() != null  && !Objects.equals(productUpdateDTO.name(), this.description)){
            this.name = productUpdateDTO.name();
        }

        if (productUpdateDTO.description() != null && !Objects.equals(productUpdateDTO.description(), this.description)){
            this.description = productUpdateDTO.description();
        }


    }
}
