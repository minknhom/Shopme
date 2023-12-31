package com.shopme.admin.brand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Brand;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer> {
	
	public Long countById(Integer id);
	
	@Query("Select b from Brand b where concat(b.id, ' ', b.name) like %?1%")
	public Page<Brand> findAll(String keyword, Pageable pageable);
}
