package com.example.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class State {
	@Id
	private Integer stateId;
	private String stateName;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="stateId")
	private Set<City> cities;

	
	
}
