package net.mahber.sykkelandkeys.service.impl;

import net.mahber.sykkelandkeys.service.StationService;
import net.mahber.sykkelandkeys.domain.Station;
import net.mahber.sykkelandkeys.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Station.
 */
@Service
@Transactional
public class StationServiceImpl implements StationService {

    private final Logger log = LoggerFactory.getLogger(StationServiceImpl.class);

    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    /**
     * Save a station.
     *
     * @param station the entity to save
     * @return the persisted entity
     */
    @Override
    public Station save(Station station) {
        log.debug("Request to save Station : {}", station);
        return stationRepository.save(station);
    }

    /**
     * Get all the stations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Station> findAll(Pageable pageable) {
        log.debug("Request to get all Stations");
        return stationRepository.findAll(pageable);
    }

    /**
     * Get one station by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Station findOne(Long id) {
        log.debug("Request to get Station : {}", id);
        return stationRepository.findOne(id);
    }

    /**
     * Delete the station by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Station : {}", id);
        stationRepository.delete(id);
    }
}
