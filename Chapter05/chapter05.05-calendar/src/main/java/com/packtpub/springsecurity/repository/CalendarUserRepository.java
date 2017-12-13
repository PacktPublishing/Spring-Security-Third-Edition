package com.packtpub.springsecurity.repository;

import com.packtpub.springsecurity.domain.CalendarUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CalendarUserRepository extends MongoRepository<CalendarUser, Integer> {

    CalendarUser findByEmail(String email);

} // The End...
