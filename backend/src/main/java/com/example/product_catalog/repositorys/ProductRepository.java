package com.example.product_catalog.repositorys;

import com.example.product_catalog.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
