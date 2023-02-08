package com.mysite.hope.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long>{
	
	SiteUser findAllByusername(String username);
	Optional<SiteUser> findByusername(String username); 
	
	
	
}
