package com.shopstuffs.repository;

import com.shopstuffs.domain.Service;
        import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Service entity.
 */
public interface ServiceRepository extends JpaRepository<Service, Long> {

}
