package com.packtpub.springsecurity;

import com.packtpub.springsecurity.domain.CalendarUser;

/**
 * CalendarUserStub.admin1()
 * CalendarUserStub.user1()
 * CalendarUserStub.admin1()
 */
public class CalendarUserStub {

    public static CalendarUser user1() {
        CalendarUser user = new CalendarUser();
        user.setEmail("user1@example.com");
        user.setFirstName("User");
        user.setLastName("1");
        user.setId(0);
        return user;
    }


    public static CalendarUser admin1() {
        CalendarUser user = new CalendarUser();
        user.setEmail("admin1@example.com");
        user.setFirstName("Admin");
        user.setLastName("1");
        user.setId(1);
        return user;
    }


    public static CalendarUser user2() {
        CalendarUser user = new CalendarUser();
        user.setEmail("user2@example.com");
        user.setFirstName("User");
        user.setLastName("2");
        user.setId(2);
        return user;
    }

    public static CalendarUser bob1() {
        CalendarUser user = new CalendarUser();
        user.setEmail("bob1@example.com");
        user.setFirstName("Bob");
        user.setLastName("One");
        user.setId(0);
        return user;
    }

}
