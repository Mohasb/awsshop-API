package com.muhammadhh.awsshop.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhammadhh.awsshop.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
