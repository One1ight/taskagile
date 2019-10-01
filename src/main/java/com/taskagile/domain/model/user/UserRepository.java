package com.taskagile.domain.model.user;

/**
 * User repository interface
 */
public interface UserRepository {

  /**
   * Find user by a username
   * @param username username the provided username used for finding user
   * @return an instance of <code>User</code> if found,null otherwise
   */
  User findByUsername(String username);

  /**
   * Find user by an email address
   * @param emailAddress emailAddress the provided email address used for finding user
   * @return an instance of <code>User</code> if found,null otherwise
   */
  // @Query("select u from User  u where u.emailAddress = ?1")
  // @Query(value = "SELECT * FROM USERS WHERE EMIAL_ADDRESS = ?1",nativeQuery = true)
  User findByEmailAddress(String emailAddress);

  // List<User> findByCreatedDateBetween(Date date,Date date1);
  // List<User> findByLastNameOrderByFirstNameDesc(String lastname);
  // List<User> findByLastName(String lastName, SpringDataWebProperties.Pageable pageable);

  void save(User user);
}
