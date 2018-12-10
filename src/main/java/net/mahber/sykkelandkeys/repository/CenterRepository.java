package net.mahber.sykkelandkeys.repository;

import net.mahber.sykkelandkeys.domain.Center;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Center entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

}
