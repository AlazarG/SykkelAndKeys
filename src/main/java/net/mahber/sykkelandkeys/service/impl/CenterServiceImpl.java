package net.mahber.sykkelandkeys.service.impl;

import net.mahber.sykkelandkeys.service.CenterService;
import net.mahber.sykkelandkeys.domain.Center;
import net.mahber.sykkelandkeys.repository.CenterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Center.
 */
@Service
@Transactional
public class CenterServiceImpl implements CenterService {

    private final Logger log = LoggerFactory.getLogger(CenterServiceImpl.class);

    private final CenterRepository centerRepository;

    public CenterServiceImpl(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    /**
     * Save a center.
     *
     * @param center the entity to save
     * @return the persisted entity
     */
    @Override
    public Center save(Center center) {
        log.debug("Request to save Center : {}", center);
        return centerRepository.save(center);
    }

    /**
     * Get all the centers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Center> findAll(Pageable pageable) {
        log.debug("Request to get all Centers");
        return centerRepository.findAll(pageable);
    }

    /**
     * Get one center by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Center findOne(Long id) {
        log.debug("Request to get Center : {}", id);
        return centerRepository.findOne(id);
    }

    /**
     * Delete the center by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Center : {}", id);
        centerRepository.delete(id);
    }
}
