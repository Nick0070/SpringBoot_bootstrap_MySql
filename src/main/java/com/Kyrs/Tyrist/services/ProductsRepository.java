package com.Kyrs.Tyrist.services;


import com.Kyrs.Tyrist.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Integer>
{
}
