package com.shopstuffs.repository;

import com.shopstuffs.domain.Company;
        import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Company entity.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
