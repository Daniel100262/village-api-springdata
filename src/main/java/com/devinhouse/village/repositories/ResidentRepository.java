package com.devinhouse.village.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devinhouse.village.model.Resident;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Integer> {
	
	@Transactional
    @Modifying
    @Query("SELECT new Resident(r.id, r.firstName, r.lastName) FROM Resident r")
    List<Resident> getResidentsWithFilteredFields(Integer id);
	
	@Transactional
    @Modifying
    @Query("SELECT r FROM Resident r WHERE  lower(r.firstName) like lower(concat('%', :name,'%')) or lower(r.lastName) like lower(concat('%', :name,'%')) ")
    List<Resident> getResidentsByName(String name);

	
	@Transactional
    @Modifying
    @Query("SELECT new Resident(r.id, r.firstName, r.lastName) FROM Resident r where r.age >= :age ")
    List<Resident> getResidentsByAge(Integer age);
	
	@Transactional
    @Modifying
    @Query("SELECT new Resident(r.id, r.firstName, r.lastName) FROM Resident r where extract (month from bornDate) = :month ")
	List<Resident> getResidentsByMonth(Integer month);
	
	@Transactional
    @Modifying
    @Query("SELECT new Resident(r.id, r.firstName, r.lastName) FROM Resident r")
	List<Resident> findAllFiltered();
	
	@Transactional
    @Modifying
    @Query("SELECT new Resident(r.firstName, r.lastName, r.age, r.bornDate, r.income, r.cpf, r.user) FROM Resident r WHERE r.id = :id")
	List<Resident> findByIdFiltered(Integer id);
	
	
}
