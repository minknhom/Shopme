package com.shopme.admin.brand;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;


@RestController
public class BrandRestController {
	
	@Autowired
	private BrandService service;
	
	@GetMapping("/brands/{id}/categories") 
	List<CategoryDTO> listCategoryByBrand(@PathVariable(name = "id") Integer brandId) throws UserNotFoundException {
		List<CategoryDTO> listCategories = new ArrayList<>();
		
		Brand brand = service.get(brandId);
		Set<Category> categories = brand.getCategories();

		for (Category category : categories) {
			CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
			listCategories.add(dto);
		}
		
		return listCategories;
	}
	
	
}
