package com.devinhouse.village.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devinhouse.village.model.dao.Resident;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Integer> {
	
	@Transactional
    @Modifying
    @Query("SELECT new com.devinhouse.village.model.dao.Resident(r.id, r.firstname, r.lastname) FROM Resident r")
    List<Resident> getResidentsWithFilteredFields(Integer id);
	
	@Transactional
    @Modifying
    @Query("select id, firstname, lastname from resident where firstname ilike '%:name%' or lastname ilike  '%:name%'")
    List<Resident> getResidentsByName(String name);
	
	@Transactional
    @Modifying
    @Query("select id, firstname, lastname from resident where age > :age ")
    List<Resident> getResidentsByAge(Integer age);
	
	@Transactional
    @Modifying
    @Query("select id, firstname, lastname from resident where extract (month from borndate) = :month ")
	List<Resident> getResidentsByMonth(Integer month);
	
	
}
