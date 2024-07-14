package com.muhammadhh.awsshop.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhammadhh.awsshop.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
