package com.example.picking.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PickingRepository extends JpaRepository<Pick, Long>{
	@Query("select s from Pick s where s.id=1")
	public Pick findNextPick();

	@Query("select s from Pick s where s.locnNbr=:locnNbr and s.id=:id")
	public Pick findById(@Param("locnNbr") Integer locnNbr, @Param("id") Long pickId);
}
