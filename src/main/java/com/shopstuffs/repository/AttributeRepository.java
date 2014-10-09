package com.shopstuffs.repository;

import com.shopstuffs.domain.Attribute;
        import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Attribute entity.
 */
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Query("select a.id, a.value from Attribute a where a.parentAttribute is null")
    List<Attribute> findAllLabels();

    @Query("select a.id, a.value, a.name from Attribute a where a.parentAttribute.id=:parentId")
    List<Attribute> findLabelOptions(@Param("parentId") Long parentId);


}
