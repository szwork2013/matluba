package com.shopstuffs.repository;

import com.shopstuffs.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Image entity.
 */
public interface ImageRepository extends JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {
    //    @Query("FROM Image i WHERE i.product.id=:id")
    List<Image> findByProduct_Id(Long productId);

    List<Image> findByProduct_IdAndId(Long productId, Long id);
}

