package com.example.dataloader;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.entity.City;
import com.example.entity.Country;
import com.example.entity.State;
import com.example.repository.CityRepository;
import com.example.repository.CountryRepository;
import com.example.repository.StateRepository;
@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		cityRepository.deleteAll();
		stateRepository.deleteAll();
		countryRepository.deleteAll();
		
		City i1= new City(301,"kolkata");
		City i2= new City(302,"medinipur");
		City i3= new City(303,"mangalor");
		City i4= new City(304,"bangalore");
		
		City u1= new City(305,"california");
		City u2= new City(306,"newyeok");
		cityRepository.saveAll(Arrays.asList(i1,i2,i3,i4,u1,u2));
		
		State is1=new State(201,"WB", Set.of(i1,i2));
		State is2=new State(202,"karnataka", Set.of(i3,i4));
		
		State us1=new State(203,"aaa", Set.of(u1,u2));
		
		stateRepository.saveAll(Arrays.asList(is1,is2,us1));
		
		Country in=new Country(111,"india", Set.of(is1,is2));
		Country us=new Country(112,"usa", Set.of(us1));
		
		countryRepository.saveAll(Arrays.asList(in,us));
	
	}

}
