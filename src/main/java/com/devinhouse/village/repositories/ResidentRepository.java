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
    @Query("SELECT new com.devinhouse.village.model.dao.Resident(r.id, r.firstName, r.lastName) FROM Resident r")
    List<Resident> getResidentsWithFilteredFields(Integer id);
	
	@Transactional
    @Modifying
    @Query("SELECT new com.devinhouse.village.model.dao.Resident(r.id, firstName, r.lastName) FROM Resident r where r.firstName like upper('%:name%') or r.lastName like upper('%:name%')")
    List<Resident> getResidentsByName(String name);
	
	@Transactional
    @Modifying
    @Query("SELECT new com.devinhouse.village.model.dao.Resident(r.id, r.firstName, r.lastName) FROM Resident r where r.age > :age ")
    List<Resident> getResidentsByAge(Integer age);
	
	@Transactional
    @Modifying
    @Query("SELECT new com.devinhouse.village.model.dao.Resident(r.id, r.firstName, r.lastName) FROM Resident r where extract (month from bornDate) = :month ")
	List<Resident> getResidentsByMonth(Integer month);
	
	
}
