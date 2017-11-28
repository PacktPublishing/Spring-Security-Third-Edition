package com.packtpub.springsecurity.repository;

import com.packtpub.springsecurity.domain.CalendarUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarUserRepository extends JpaRepository<CalendarUser, Integer> {

    CalendarUser findByEmail(String email);

} // The End...
