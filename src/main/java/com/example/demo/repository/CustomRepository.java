package com.example.demo.repository;

import com.example.demo.entity.Custom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRepository extends JpaRepository<Custom,Integer> {
}
