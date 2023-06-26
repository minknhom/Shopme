package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
 	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		User userMinhNN = new User();
		userMinhNN.setEmail("minh@gmail.com");
		userMinhNN.setPassword("password");
		userMinhNN.setFirstName("Minh");
		userMinhNN.setLastName("Nguyen Nhat");
		
		Role roleAdmin = entityManager.find(Role.class, 1);
		userMinhNN.addRole(roleAdmin);
		
		User savedUser = repo.save(userMinhNN);;
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRole() {
		User userManhNN = new User();
		userManhNN.setEmail("manh@gmail.com");
		userManhNN.setPassword("password");
		userManhNN.setFirstName("Manh");
		userManhNN.setLastName("Nguyen Nhat");
		
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		userManhNN.addRole(roleEditor);
		userManhNN.addRole(roleAssistant);
		
		User savedUser = repo.save(userManhNN);;
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userMinh = repo.findById(1).get();
		System.out.println(userMinh);
		assertThat(userMinh).isNotNull();
	}
	
	@Test
	public void testDeleteUser() {
		repo.deleteById(2);
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userMinh = repo.findById(1).get();
		userMinh.setEnabled(true);
		
		repo.save(userMinh);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "minh@gmail.com";
		User user = repo.getUserByEmaiil(email);
		
		assertThat(user).isNotNull();
	}
}
