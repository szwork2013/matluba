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

    List<Attribute> findByParentIsNull();

    @Query("FROM Attribute a WHERE a.parent.id=:parentId")
    List<Attribute> findByParent(@Param("parentId") Long parentId);


}
