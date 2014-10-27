package com.shopstuffs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shopstuffs.domain.Attribute;
import com.shopstuffs.domain.Product;
import com.shopstuffs.domain.ProductType;
import com.shopstuffs.repository.AttributeRepository;
import com.shopstuffs.repository.ProductRepository;
import com.shopstuffs.repository.sort.ProductSort;
import com.shopstuffs.web.rest.dto.ProductAttributeDTO;
import com.shopstuffs.web.rest.dto.ProductCriteriaDTO;
import com.shopstuffs.web.rest.dto.ProductResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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

    @RequestMapping(value = "/rest/product/search/{page}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResultDTO> search(@PathVariable("page") String page, @RequestBody ProductCriteriaDTO criterias) {
        List<Specification<Product>> specs = new ArrayList<Specification<Product>>();


        if (criterias.getTitle() != null) {
            specs.add(hasTitle(criterias.getTitle()));
        }

        if (criterias.getCategoryName() != null) {
            specs.add(inCategory(criterias.getCategoryName()));
        }


        Specifications<Product> linkedSpecs = null;
        for (Specification<Product> productSpecification : specs) {
            if (linkedSpecs == null) {
                linkedSpecs = where(productSpecification);
            } else {
                linkedSpecs = linkedSpecs.and(productSpecification);
            }
        }


        ProductResultDTO result = null;
        if (linkedSpecs != null){
                Pageable pageable = createPageRequest(Integer.parseInt(page), ProductSort.defaultSort());
                Page<Product> pages = productRepository.findAll(linkedSpecs, pageable);
                if (pages != null) {
                    result = new ProductResultDTO(pages.getContent(),
                            pages.getTotalPages(),
                            pages.getNumber() + 1);
                }
        }

        return Optional.ofNullable(result)
                .map(r -> new ResponseEntity<>(
                        r,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private Pageable createPageRequest(int pageIndex, Sort sort) {
        return new PageRequest(Math.max(0, pageIndex - 1), NUMBER_OF_PRODUCTS_PER_PAGE, sort);
    }
}