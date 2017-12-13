package com.packtpub.springsecurity;

import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.dataaccess.EventDao;
import com.packtpub.springsecurity.dataaccess.JpaCalendarUserDao;
import com.packtpub.springsecurity.dataaccess.JpaEventDao;
import com.packtpub.springsecurity.repository.CalendarUserRepository;
import com.packtpub.springsecurity.repository.EventRepository;
import com.packtpub.springsecurity.repository.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

//    @Bean
//    public CalendarUserDao calendarUserDao(final CalendarUserRepository calendarUserRepository,
//                                           final RoleRepository roleRepository){
//        return new JpaCalendarUserDao(calendarUserRepository, roleRepository);
//    }

//    @Bean
//    public EventDao eventDao(final EventRepository eventRepository){
//        return new JpaEventDao(eventRepository);
//    }

} // The End...
