package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct1() {
		Brand brand = entityManager.find(Brand.class, 5);
		Category category = entityManager.find(Category.class, 16);
		
		Product product = new Product();
		
		product.setName("minh");
		product.setAlias("minh");
		product.setShortDescription("minh short description");
		product.setFullDescription("minh full description");
		product.setBrand(brand);
		product.setCategory(category);
		
		product.setEnabled(true);
		product.setInStock(true);
		
		product.setPrice(100);
		product.setDiscountPercent(0);
		
		product.setHeight(10);
		product.setWeight(10);
		product.setWidth(10);
		product.setLength(10);
		
		Product savedProduct = repo.save(product);
		
		assertThat(savedProduct.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListProduct() {
		Iterable<Product> listProducts = repo.findAll();
		listProducts.forEach(product -> System.out.println(product));
	}
	
	@Test
	public void testSaveProductWithDetails() {
		Integer productId = 3;
		Product product = repo.findById(productId).get();
		
		product.addDetail("cap 1", "oke");
		product.addDetail("cap 2", "oke");
		product.addDetail("cap 3", "oke");
		
		Product savedProduct = repo.save(product);
		assertThat(savedProduct.getDetails()).isNotEmpty();
	}
}
