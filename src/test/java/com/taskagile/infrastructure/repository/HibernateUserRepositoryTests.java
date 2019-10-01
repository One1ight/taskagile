package com.taskagile.infrastructure.repository;

import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class HibernateUserRepositoryTests {

  @TestConfiguration
  public static class UserRepositoryTestContextConfiguration {
    @Bean
    public UserRepository userRepository(EntityManager entityManager) {
      return new HibernateUserRepository(entityManager);
    }
  }

  @Autowired
  private UserRepository repository;

  @Test(expected = PersistenceException.class)
  public void save_nullUsernameUser_shouldFail() {
    User invalidUser = User.create(null,"sunny@taskagile.com","password");
    repository.save(invalidUser);
  }

  @Test(expected = PersistenceException.class)
  public void save_nullEmailAddressUser_shouldFail() {
    User invalidUser = User.create("sunny",null,"password");
    repository.save(invalidUser);
  }

  @Test(expected = PersistenceException.class)
  public void save_nullPasswordUser_shouldFail() {
    User invalidUser = User.create("sunny","sunny@taskagile.com",null);
    repository.save(invalidUser);
  }

  @Test
  public void save_validUser_shouldSuccess() {
    String username = "sunny";
    String emailAddress = "sunny@taskagile.com";
    String password = "password";
    User newUser = User.create(username,emailAddress,password);
    repository.save(newUser);
    assertNotNull("New user's id should be generated",newUser.getId());
    assertNotNull("New user's created date should be generated",newUser.getCreateDate());
    assertEquals(username,newUser.getUsername());
    assertEquals(emailAddress,newUser.getEmailAddress());
    assertEquals("",newUser.getFirstName());
    assertEquals("",newUser.getLastName());
  }

  @Test
  public void save_usernameAlreadyExist_shouldFail() {
    // Create already exist user
    String username = "sunny";
    String emailAddress = "sunny@taskagile.com";
    User alreadyExist = User.create(username,emailAddress,"password");
    repository.save(alreadyExist);

    try {
      User newUser = User.create(username,"new@taskagile.com","password");
      repository.save(newUser);
    }catch (Exception e){
      assertEquals(ConstraintViolationException.class.toString(),e.getCause().getClass().toString());
    }
  }

  @Test
  public void save_emailAddressAlreadyExist_shouldFail(){
    //Create already exist user
    String username = "sunny";
    String emailAddress = "sunny@taskagile.com";
    User alreadyExist = User.create(username,emailAddress,"password");
    repository.save(alreadyExist);
    try {
      User newUser = User.create("new",emailAddress,"password");
      repository.save(newUser);
    }catch (Exception e) {
      assertEquals(ConstraintViolationException.class.toString(),e.getCause().getClass().toString());
    }

  }

  @Test
  public void findByEmailAddress_notExist_shouldReturnEmptyResult() {
    String emailAddress = "sunny@taskagile.com";
    User user = repository.findByEmailAddress(emailAddress);
    assertNull("No user should be found",user);
  }

  @Test
  public void findByEmailAddress_Exist_shouldReturnResult() {
    String emailAddress = "sunny@taskageile.com";
    String username = "sunny";
    User newUser = User.create(username,emailAddress,"password");
    repository.save(newUser);
    User found = repository.findByEmailAddress(emailAddress);
    assertEquals("Username should match",username,found.getUsername());
  }

  @Test
  public void findByUsername_notExist_shouldReturnEmptyResult() {
    String username = "sunny";
    User user = repository.findByUsername(username);
    assertNull("No user should be found",user);
  }

  @Test
  public void findByUsername_exist_shouldReturnResult() {
    String username = "sunny";
    String emailAddress = "sunny@taskagile.com";
    User newUser = User.create(username,emailAddress,"password");
    repository.save(newUser);
    User found = repository.findByUsername(username);
    assertEquals("Email address should match",emailAddress,found.getEmailAddress());
  }
}
