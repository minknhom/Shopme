package com.shopme.admin.product;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Product;


@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll(){
		return (List<Product>) repo.findAll();
	}
	
	public Product save(Product product){
		return repo.save(product);
	}
	
	public Product get(Integer id) throws UserNotFoundException {
		try {
			return repo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any brand with ID "+id );
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = repo.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any brand with ID "+id );
		}
		repo.deleteById(id);
	}
}
