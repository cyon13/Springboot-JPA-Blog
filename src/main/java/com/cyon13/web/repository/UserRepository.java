package com.cyon13.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cyon13.web.model.User;

// DAO
// 자동으로 bean 등록이 된다.
//@Repository // 생략 가능하다
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username); 
// 해당 Repository 는 User가 관리하는 Repository이다. 그리고 User의 PrimaryKey는 Integer다.
	
	
	
	
	
	
	// JPA Naming 쿼리  전략
	// SELECT * FROM user WHERE username = ?1 AND password = ?2
	//User findByUsernameAndPassword(String username, String password);
	
//	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	User login(String username,String password);
}
