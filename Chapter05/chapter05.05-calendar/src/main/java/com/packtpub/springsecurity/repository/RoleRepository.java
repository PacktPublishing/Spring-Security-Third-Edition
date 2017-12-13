package com.packtpub.springsecurity.repository;

import com.packtpub.springsecurity.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, Integer> {

} // The End...
