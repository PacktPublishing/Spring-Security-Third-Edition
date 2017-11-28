package com.packtpub.springsecurity.repository;

import com.packtpub.springsecurity.domain.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RememberMeTokenRepository extends JpaRepository<PersistentLogin, String> {

    PersistentLogin findBySeries(String series);
    List<PersistentLogin> findByUsername(String username);

} // The End...
