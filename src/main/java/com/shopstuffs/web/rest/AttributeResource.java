package com.shopstuffs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shopstuffs.domain.Attribute;
import com.shopstuffs.repository.AttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * REST controller for managing Attribute.
 */
@RestController
@RequestMapping("/app")
public class AttributeResource {

    private final Logger log = LoggerFactory.getLogger(AttributeResource.class);

    @Inject
    private AttributeRepository attributeRepository;

    /**
     * POST  /rest/attributes -> Create a new attribute.
     */
    @RequestMapping(value = "/rest/attributes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attribute> create(@RequestBody Attribute attribute) {
        log.debug("REST request to save Attribute : {}", attribute);
        Attribute saved = attributeRepository.save(attribute);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    /**
     * GET  /rest/attributes -> get all the attributes.
     */
    @RequestMapping(value = "/rest/attributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Attribute> getAll() {
        log.debug("REST request to get all Attributes");
        return attributeRepository.findAll();
    }

    /**
     * GET  /rest/attributes -> get all the labels.
     */
    @RequestMapping(value = "/rest/attribute/labels",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Attribute> getLabels() {
        log.debug("REST request to get all Attributes");
        return attributeRepository.findByParentIsNull();
    }


    /**
     * GET  /rest/options/id -> get all options.
     */
    @RequestMapping(value = "/rest/attribute/options/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Attribute> getOptions(@PathVariable Long id) {
        log.debug("REST request to get all Attributes");
        return attributeRepository.findByParent(id);
    }

    /**
     * GET  /rest/attributes/:id -> get the "id" attribute.
     */
    @RequestMapping(value = "/rest/attributes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attribute> get(@PathVariable Long id) {
        log.debug("REST request to get Attribute : {}", id);
        return Optional.ofNullable(attributeRepository.findOne(id))
                .map(attribute -> new ResponseEntity<>(
                        attribute,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/attributes/:id -> delete the "id" attribute.
     */
    @RequestMapping(value = "/rest/attributes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Attribute : {}", id);
        attributeRepository.delete(id);
    }
}
