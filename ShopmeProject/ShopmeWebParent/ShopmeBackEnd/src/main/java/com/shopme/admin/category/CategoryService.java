package com.shopme.admin.category;

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

import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Category;


@Service
public class CategoryService {

	public static final int CATEGORIES_PER_PAGE = 4;
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll(){
		return (List<Category>) repo.findAll();
	}
	
	public Page<Category> listByPage(int pageNum, String sortField, String sortDir, String keyword){
		 Sort sort = Sort.by(sortField);
		 
		 sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		 
		 Pageable pageable =  PageRequest.of(pageNum - 1, CATEGORIES_PER_PAGE, sort);
		 
		 if (keyword != null) {
			 return repo.findAll(keyword, pageable);
		 }
		 
		 return repo.findAll(pageable);
	 }
	
	public List<Category> listCategoriesUsedInForm(){
		List<Category> categoriesUsedInForm = new ArrayList<>();
		
		Iterable<Category> categoriesInDB = repo.findAll();
		
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
	
	public Category save(Category category){
		return repo.save(category);
	}
	
	public Category get(Integer id) throws UserNotFoundException {
		try {
			return repo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID "+id );
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = repo.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID "+id );
		}
		repo.deleteById(id);
	}
}
