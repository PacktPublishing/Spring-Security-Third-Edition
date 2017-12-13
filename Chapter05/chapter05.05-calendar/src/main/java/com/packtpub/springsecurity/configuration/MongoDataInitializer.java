package com.packtpub.springsecurity.configuration;

import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;
import com.packtpub.springsecurity.domain.Role;
import com.packtpub.springsecurity.repository.CalendarUserRepository;
import com.packtpub.springsecurity.repository.EventRepository;
import com.packtpub.springsecurity.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Initialize the initial data in the MongoDb
 * This replaces data.sql
 */
@Configuration
public class MongoDataInitializer {

    private static final Logger logger = LoggerFactory
            .getLogger(MongoDataInitializer.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CalendarUserRepository calendarUserRepository;

    @Autowired
    private EventRepository eventRepository;

    @PostConstruct
    public void setUp() {
        logger.info("*******************************************************");
        logger.info("clean the database");
        calendarUserRepository.deleteAll();
        roleRepository.deleteAll();
        eventRepository.deleteAll();

        logger.info("seedRoles");
        seedRoles();

        logger.info("seedCalendarUsers");
        seedCalendarUsers();
        logger.info("seedEvents");
        seedEvents();
        logger.info("*******************************************************");
    }

    CalendarUser user, admin, user2;

    // CalendarUsers
    {
        user = new CalendarUser(0, "user1@example.com","$2a$04$qr7RWyqOnWWC1nwotUW1nOe1RD5.mKJVHK16WZy6v49pymu1WDHmi","User","1");
        admin = new CalendarUser(1,"admin1@example.com","$2a$04$0CF/Gsquxlel3fWq5Ic/ZOGDCaXbMfXYiXsviTNMQofWRXhvJH3IK","Admin","1");
        user2 = new CalendarUser(2,"user2@example.com","$2a$04$PiVhNPAxunf0Q4IMbVeNIuH4M4ecySWHihyrclxW..PLArjLbg8CC","User2","2");

    }

    Role user_role, admin_role;

    /**
     * -- ROLES --
     * insert into role(id, name) values (0, "ROLE_USER");
     * insert into role(id, name) values (1, "ROLE_ADMIN");
     */
    private void seedRoles(){
        user_role = new Role(0, "ROLE_USER");
        user_role = roleRepository.save(user_role);

        admin_role = new Role(1, "ROLE_ADMIN");
        admin_role = roleRepository.save(admin_role);
    }


    /**
     * Seed initial events
     */
    private void seedEvents(){

        // Event 1
        Event event1 = new Event(
                100,
                "Birthday Party",
                "This is going to be a great birthday",
                new GregorianCalendar(2017,6,3,6,36,00),
                user,
                admin
                );

        // Event 2
        Event event2 = new Event(
                101,
                "Conference Call",
                "Call with the client",
                new GregorianCalendar(2017,11,23,13,00,00),
                user2,
                user
                );

        // Event 3
        Event event3 = new Event(
                102,
                "Vacation",
                "Paragliding in Greece",
                new GregorianCalendar(2017,8,14,11,30,00),
                admin,
                user2
                );

        // save Event
        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);

        List<Event> events = eventRepository.findAll();

        logger.info("Events: {}", events);

    }


    private void seedCalendarUsers(){

//        user.setRoles(new HashSet<>(Arrays.asList(user_role)));
//        admin.setRoles(new HashSet<>(Arrays.asList(user_role, admin_role)));
//        user2.setRoles(new HashSet<>(Arrays.asList(user_role)));

        // user1
        user.addRole(user_role);

        // admin2
        admin.addRole(user_role);
        admin.addRole(admin_role);

        // user2
        user2.addRole(user_role);

        // CalendarUser
        calendarUserRepository.save(user);
        calendarUserRepository.save(admin);
        calendarUserRepository.save(user2);

        List<CalendarUser> users = calendarUserRepository.findAll();

        logger.info("CalendarUsers: {}", users);
    }

} // The End...
