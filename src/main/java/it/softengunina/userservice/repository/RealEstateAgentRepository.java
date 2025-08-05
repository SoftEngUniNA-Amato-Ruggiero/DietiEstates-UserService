package it.softengunina.userservice.repository;

import it.softengunina.userservice.model.RealEstateAgent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RealEstateAgentRepository<T extends RealEstateAgent>  extends UserRepository<T> {
    @Modifying
    @Query(value = "INSERT INTO  real_estate_agents  (id, agency_id) VALUES (:userId, :agencyId)", nativeQuery = true)
    void promoteUser(@Param("userId") Long userId, @Param("agencyId") Long agencyId);

    @Query("SELECT a FROM #{#entityName} a WHERE a.agency.id = :agencyId")
    Set<T> findByAgencyId(Long agencyId);
}
