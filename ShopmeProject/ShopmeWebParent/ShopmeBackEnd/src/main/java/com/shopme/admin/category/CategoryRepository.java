package com.shopme.admin.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

	public Long countById(Integer id);
	
	@Query("Select c from Category c where concat(c.id, ' ', c.name, ' ', c.alias) like %?1%")
	public Page<Category> findAll(String keyword, Pageable pageable);
}
