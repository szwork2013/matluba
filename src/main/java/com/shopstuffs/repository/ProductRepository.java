package com.shopstuffs.repository;

import com.shopstuffs.domain.Product;
        import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query("select count(p.i) ")
//    long hasAttribute(Product product, Long attrId);


}
