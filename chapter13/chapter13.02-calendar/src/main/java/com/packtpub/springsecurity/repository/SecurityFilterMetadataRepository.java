package com.packtpub.springsecurity.repository;

import com.packtpub.springsecurity.domain.SecurityFilterMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityFilterMetadataRepository extends JpaRepository<SecurityFilterMetadata, Integer> {
} // The End...
