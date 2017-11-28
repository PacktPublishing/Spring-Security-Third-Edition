package com.packtpub.springsecurity.repository;

import com.packtpub.springsecurity.domain.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, Integer> {

    @Query("{'owner.id' : ?0}")
    List<Event> findByUser(Integer name);

} // The End...
