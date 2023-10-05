package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.City;

public interface CityRepository extends JpaRepository<City, Integer>{
}
