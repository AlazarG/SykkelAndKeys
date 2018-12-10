package net.mahber.sykkelandkeys.service.impl;

import net.mahber.sykkelandkeys.service.AvailabilityService;
import net.mahber.sykkelandkeys.domain.Availability;
import net.mahber.sykkelandkeys.repository.AvailabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Availability.
 */
@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService {

    private final Logger log = LoggerFactory.getLogger(AvailabilityServiceImpl.class);

    private final AvailabilityRepository availabilityRepository;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    /**
     * Save a availability.
     *
     * @param availability the entity to save
     * @return the persisted entity
     */
    @Override
    public Availability save(Availability availability) {
        log.debug("Request to save Availability : {}", availability);
        return availabilityRepository.save(availability);
    }

    /**
     * Get all the availabilities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Availability> findAll(Pageable pageable) {
        log.debug("Request to get all Availabilities");
        return availabilityRepository.findAll(pageable);
    }

    /**
     * Get one availability by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Availability findOne(Long id) {
        log.debug("Request to get Availability : {}", id);
        return availabilityRepository.findOne(id);
    }

    /**
     * Delete the availability by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Availability : {}", id);
        availabilityRepository.delete(id);
    }
}
