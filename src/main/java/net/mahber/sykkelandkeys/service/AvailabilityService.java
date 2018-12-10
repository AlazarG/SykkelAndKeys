package net.mahber.sykkelandkeys.service;

import net.mahber.sykkelandkeys.domain.Availability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Availability.
 */
public interface AvailabilityService {

    /**
     * Save a availability.
     *
     * @param availability the entity to save
     * @return the persisted entity
     */
    Availability save(Availability availability);

    /**
     * Get all the availabilities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Availability> findAll(Pageable pageable);

    /**
     * Get the "id" availability.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Availability findOne(Long id);

    /**
     * Delete the "id" availability.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
