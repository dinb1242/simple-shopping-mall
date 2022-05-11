package com.shoppingmall.example.domain.repository;

import com.shoppingmall.example.domain.model.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<Example, Long> {
}
