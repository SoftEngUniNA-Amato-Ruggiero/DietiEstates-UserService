package it.softengunina.userservice.repository;

import it.softengunina.userservice.model.RealEstateManager;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RealEstateManagerRepository extends RealEstateAgentRepository<RealEstateManager> {
    @Modifying
    @Query(value = "INSERT INTO real_estate_managers (id) VALUES (:agentId)", nativeQuery = true)
    void promoteAgent(@Param("agentId") Long agentId);
}
