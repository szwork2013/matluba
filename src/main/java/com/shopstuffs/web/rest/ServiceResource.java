package com.shopstuffs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shopstuffs.domain.Service;
import com.shopstuffs.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Service.
 */
@RestController
@RequestMapping("/app")
public class ServiceResource {

    private final Logger log = LoggerFactory.getLogger(ServiceResource.class);

    @Inject
    private ServiceRepository serviceRepository;

    /**
     * POST  /rest/services -> Create a new service.
     */
    @RequestMapping(value = "/rest/services",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Service service) {
        log.debug("REST request to save Service : {}", service);
        serviceRepository.save(service);
    }

    /**
     * GET  /rest/services -> get all the services.
     */
    @RequestMapping(value = "/rest/services",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Service> getAll() {
        log.debug("REST request to get all Services");
        return serviceRepository.findAll();
    }

    /**
     * GET  /rest/services/:id -> get the "id" service.
     */
    @RequestMapping(value = "/rest/services/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Service> get(@PathVariable Long id) {
        log.debug("REST request to get Service : {}", id);
        return Optional.ofNullable(serviceRepository.findOne(id))
            .map(service -> new ResponseEntity<>(
                service,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/services/:id -> delete the "id" service.
     */
    @RequestMapping(value = "/rest/services/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Service : {}", id);
        serviceRepository.delete(id);
    }
}
