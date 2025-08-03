package it.softengunina.userservice.repository;

import it.softengunina.userservice.model.RealEstateAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RealEstateAgentRepository  extends JpaRepository<RealEstateAgent, Long> {
    @Modifying
    @Query(value = "INSERT INTO real_estate_agents (id, agency_id) VALUES (:userId, :agencyId)", nativeQuery = true)
    void promoteUser(@Param("userId") Long userId, @Param("agencyId") Long agencyId);
}
