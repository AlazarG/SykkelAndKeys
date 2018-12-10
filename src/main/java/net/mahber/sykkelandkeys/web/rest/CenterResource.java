package net.mahber.sykkelandkeys.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.mahber.sykkelandkeys.domain.Center;
import net.mahber.sykkelandkeys.service.CenterService;
import net.mahber.sykkelandkeys.web.rest.errors.BadRequestAlertException;
import net.mahber.sykkelandkeys.web.rest.util.HeaderUtil;
import net.mahber.sykkelandkeys.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Center.
 */
@RestController
@RequestMapping("/api")
public class CenterResource {

    private final Logger log = LoggerFactory.getLogger(CenterResource.class);

    private static final String ENTITY_NAME = "center";

    private final CenterService centerService;

    public CenterResource(CenterService centerService) {
        this.centerService = centerService;
    }

    /**
     * POST  /centers : Create a new center.
     *
     * @param center the center to create
     * @return the ResponseEntity with status 201 (Created) and with body the new center, or with status 400 (Bad Request) if the center has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/centers")
    @Timed
    public ResponseEntity<Center> createCenter(@RequestBody Center center) throws URISyntaxException {
        log.debug("REST request to save Center : {}", center);
        if (center.getId() != null) {
            throw new BadRequestAlertException("A new center cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Center result = centerService.save(center);
        return ResponseEntity.created(new URI("/api/centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /centers : Updates an existing center.
     *
     * @param center the center to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated center,
     * or with status 400 (Bad Request) if the center is not valid,
     * or with status 500 (Internal Server Error) if the center couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/centers")
    @Timed
    public ResponseEntity<Center> updateCenter(@RequestBody Center center) throws URISyntaxException {
        log.debug("REST request to update Center : {}", center);
        if (center.getId() == null) {
            return createCenter(center);
        }
        Center result = centerService.save(center);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, center.getId().toString()))
            .body(result);
    }

    /**
     * GET  /centers : get all the centers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of centers in body
     */
    @GetMapping("/centers")
    @Timed
    public ResponseEntity<List<Center>> getAllCenters(Pageable pageable) {
        log.debug("REST request to get a page of Centers");
        Page<Center> page = centerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/centers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /centers/:id : get the "id" center.
     *
     * @param id the id of the center to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the center, or with status 404 (Not Found)
     */
    @GetMapping("/centers/{id}")
    @Timed
    public ResponseEntity<Center> getCenter(@PathVariable Long id) {
        log.debug("REST request to get Center : {}", id);
        Center center = centerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(center));
    }

    /**
     * DELETE  /centers/:id : delete the "id" center.
     *
     * @param id the id of the center to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/centers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCenter(@PathVariable Long id) {
        log.debug("REST request to delete Center : {}", id);
        centerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
