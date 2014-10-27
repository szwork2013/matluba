package com.shopstuffs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shopstuffs.domain.Attribute;
import com.shopstuffs.domain.Product;
import com.shopstuffs.domain.ProductType;
import com.shopstuffs.repository.AttributeRepository;
import com.shopstuffs.repository.ProductRepository;
import com.shopstuffs.repository.sort.ProductSort;
import com.shopstuffs.repository.specifications.ProductSpecifications;
import com.shopstuffs.web.rest.dto.ProductAttributeDTO;
import com.shopstuffs.web.rest.dto.ProductCriteriaDTO;
import com.wordnik.swagger.core.filter.SpecFilter;
import org.hibernate.type.OrderedSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.persistence.criteria.Predicate;
import javax.xml.ws.Response;
import java.util.ArrayList;
import javax.ws.rs.PathParam;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.shopstuffs.repository.specifications.ProductSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/app")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    private ProductRepository productRepository;

    @Inject
    private AttributeRepository attributeRepository;

    private Integer NUMBER_OF_PRODUCTS_PER_PAGE = 20;

    /**
     * POST  /rest/products -> Create a new product.
     */
    @RequestMapping(value = "/rest/products",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Product> create(@RequestBody Product product) {
        log.debug("REST request to save Product : {}", product);
        final Product createdProduct = productRepository.save(product);
        return new ResponseEntity<Product>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * GET  /rest/products -> get all the products.
     */
    @RequestMapping(value = "/rest/products",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Product> getAll() {
        log.debug("REST request to get all Products");
        return productRepository.findAll(ProductSort.defaultSort());
    }

    /**
     * GET  /rest/products/:id -> get the "id" product.
     */
    @RequestMapping(value = "/rest/products/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Product> get(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        return Optional.ofNullable(productRepository.findOne(id))
                .map(product -> new ResponseEntity<>(
                        product,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/products/:id -> delete the "id" product.
     */
    @RequestMapping(value = "/rest/products/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productRepository.delete(id);
    }

    /**
     * GET  /rest/products/types -> get all product types.
     */
    @RequestMapping(value = "/rest/products/types",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductType> getProductTypes() {
        log.debug("REST request to get product types");
        return Arrays.asList(ProductType.values());
    }

    /**
     * POST  /rest/products/{productId}/{attrId} -> add attribute to a product.
     */
    @RequestMapping(value = "/rest/product/attribute",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAttribute(@RequestBody ProductAttributeDTO productAttribute) {
        log.debug("REST request to get product attributes");

        Product product = productRepository.findOne(productAttribute.getProductId());
        Collection<Attribute> attributes = product.getAttributes();

        for (Attribute attribute : attributes) {
            if (attribute.getId().equals(productAttribute.getAttributeId())) {
                return new ResponseEntity<String>("Product already has the attribute", HttpStatus.NOT_ACCEPTABLE);
            }
        }

        Attribute attr = attributeRepository.findOne(productAttribute.getAttributeId());
        if (attr != null) {
            attributes.add(attr);
            productRepository.save(product);
            return new ResponseEntity<String>("Attribute has been addded successfully!", HttpStatus.CREATED);
        }


        return new ResponseEntity<String>("Attribute not exist!", HttpStatus.BAD_REQUEST);
    }

    /**
     * DELETE  /rest/products/types -> get all product types.
     */
    @RequestMapping(value = "/rest/product/attributes/{productId}/{attrId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAttribute(@PathParam("productId") Long productId, @PathVariable("attrId") Long attrId) {
        log.debug("REST request to delete product");

        Product product = productRepository.findOne(productId);
        Collection<Attribute> attributes = product.getAttributes();

        Attribute attr = attributeRepository.findOne(attrId);
        if (attr != null) {
            attributes.remove(attr);
            productRepository.save(product);
        }

        return new ResponseEntity<String>("Attribute has been deleted successfully!", HttpStatus.OK);
    }

    /**
     * DELETE  /rest/products/types -> get all product types.
     */
    @RequestMapping(value = "/rest/product/filter",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> search(@RequestBody ProductCriteriaDTO criterias) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Pageable createPageSpec(Integer pageIndex, Sort sort) {
        Pageable pageSpecification = new PageRequest(pageIndex, NUMBER_OF_PRODUCTS_PER_PAGE, sort);
        return pageSpecification;
    }

    private void buildFilter(ProductCriteriaDTO criteriaDTO) {
        productRepository.findAll(where(hasTitle(criteriaDTO.getTitle()))
                        .and(inCategory(criteriaDTO.getCategoryName()))
                        .and(betweenDates(criteriaDTO.getReleaseDate(), criteriaDTO.getExpireDate())));
    }
}
