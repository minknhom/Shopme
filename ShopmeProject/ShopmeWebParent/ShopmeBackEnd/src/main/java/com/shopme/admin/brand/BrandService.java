package com.shopme.admin.brand;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.category.CategoryRepository;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;

@Service
public class BrandService {
	
	@Autowired
	private BrandRepository brandRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	public static final int  BRANDS_PER_PAGE = 4;
	
	public List<Brand> listAll(){
		return (List<Brand>) brandRepo.findAll();
	}
	
	public Page<Brand> listByPage(int pageNum, String sortField, String sortDir, String keyword){
		 Sort sort = Sort.by(sortField);
		 
		 sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		 
		 Pageable pageable =  PageRequest.of(pageNum - 1, BRANDS_PER_PAGE, sort);
		 
		 if (keyword != null) {
			 return brandRepo.findAll(keyword, pageable);
		 }
		 
		 return brandRepo.findAll(pageable);
	 }
	
	public List<Category> listCategories(){
		return (List<Category>) categoryRepo.findAll();
	}
	
	public List<Category> listCategoriesUsedInForm(){
		List<Category> categoriesUsedInForm = new ArrayList<>();
		
		Iterable<Category> categoriesInDB = categoryRepo.findAll();
		
		for (Category category : categoriesInDB) {
			if (category.getParent() == null) {
				categoriesUsedInForm.add(Category.copyIdAndName(category));
				
				Set<Category> children = category.getChildren();
				
				for(Category subCategory : children) {
					String name = "--" + subCategory.getName();
					categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
					listChildren(categoriesUsedInForm, subCategory, 1);
				}
			}
		}
		
		return categoriesUsedInForm;
	}
	public void listChildren(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for(Category subCategory : children) {
			String name = "";
			for(int i = 0; i < newSubLevel; i++) {
				name += "--"; 
			}
			name += subCategory.getName();
			
			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
			
			listChildren(categoriesUsedInForm, subCategory, newSubLevel);
		}
		
	}
	
	public Brand save(Brand brand){
		return brandRepo.save(brand);
	}
	public Brand get(Integer id) throws UserNotFoundException {
		try {
			return brandRepo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any brand with ID "+id );
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = brandRepo.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any brand with ID "+id );
		}
		brandRepo.deleteById(id);
	}
}
