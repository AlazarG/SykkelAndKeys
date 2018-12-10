package net.mahber.sykkelandkeys.service;

import net.mahber.sykkelandkeys.domain.Center;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Center.
 */
public interface CenterService {

    /**
     * Save a center.
     *
     * @param center the entity to save
     * @return the persisted entity
     */
    Center save(Center center);

    /**
     * Get all the centers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Center> findAll(Pageable pageable);

    /**
     * Get the "id" center.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Center findOne(Long id);

    /**
     * Delete the "id" center.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
