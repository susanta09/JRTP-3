package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Country;
@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
	@Query("SELECT countryId ,countryName FROM  Country")
	List<Object[]> getAllCountry();
	
	@Query("SELECT s.stateId,s.stateName FROM  Country c JOIN c.states As s WHERE c.countryId=:cid")
	List<Object[]>getAllState(Integer cid);
}
