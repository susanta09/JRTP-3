package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.State;

public interface StateRepository extends JpaRepository<State, Integer>{
	@Query("SELECT c.cityId,c.cityName FROM State s JOIN s.cities AS c  WHERE s.stateId=:sid")
	List<Object[]>getAllCity(Integer sid);
}
