package com.shopstuffs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shopstuffs.domain.CompanyJson;
import com.shopstuffs.domain.Company;
import com.shopstuffs.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Company.
 */
@RestController
@RequestMapping("/app")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    @Inject
    private CompanyRepository companyRepository;

    private final String JSON_PATH = "D:\\projects\\shopstuffs_with_jhipster\\matluba\\src\\main\\webapp\\json\\company.json";

    /**
     * POST  /rest/companys -> Create a new company.
     */
    @RequestMapping(value = "/rest/company",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody CompanyJson company) {
        log.debug("REST request to save Company : {}", company);

        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            jsonMapper.writeValue(new File(JSON_PATH), company);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * GET  /rest/companys/:id -> get the "id" company.
     */
    @RequestMapping(value = "/rest/company",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> get() {
        log.debug("REST request to get Company ");

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(JSON_PATH));
            String currentLine;
            StringBuilder sb = new StringBuilder();
            while ((currentLine = buffer.readLine()) != null) {
                sb.append(currentLine);
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
