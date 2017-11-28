package com.packtpub.springsecurity.authentication;

import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.domain.CalendarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

/**
 * This ConnectionSignUp can be used to put the Social
 * User into your local database.
 */
@Service
public class ProviderConnectionSignup implements ConnectionSignUp {

    @Autowired
    private CalendarUserDao calendarUserDao;

    @Override
    public String execute(Connection<?> connection) {

        CalendarUser user = SocialAuthenticationUtils.createCalendarUserFromProvider(connection);

        calendarUserDao.createUser(user);

        return user.getEmail();
    }

} // The End...
