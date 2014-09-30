package com.shopstuffs.repository;

import com.shopstuffs.domain.Attribute;
        import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Attribute entity.
 */
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

}
