package com.mysite.hope.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long>{

	Optional<SiteUser> findByusername(String username);
	
	SiteUser findAllByusername(String username);

}
