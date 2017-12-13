package com.packtpub.springsecurity.authentication;

import com.packtpub.springsecurity.core.authority.CalendarUserAuthorityUtils;
import com.packtpub.springsecurity.domain.CalendarUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * Social Authentication
 *
 * <pre>SocialAuthenticationUtils.authenticate(connection)</pre>
 */
public class SocialAuthenticationUtils {

    public static void authenticate(Connection<?> connection) {

        CalendarUser user = createCalendarUserFromProvider(connection);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        CalendarUserAuthorityUtils.createAuthorities(user));
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    public static CalendarUser createCalendarUserFromProvider(Connection<?> connection){

        // TODO: There is a defect with Facebook:
        // org.springframework.social.UncategorizedApiException: (#12) bio field is
        // deprecated for versions v2.8 and higher:
//
//        Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
//        Facebook facebook = connection.getApi();
//        String [] fields = { "id", "email",  "first_name", "last_name" };
//        User userProfile = facebook.fetchObject("me", User.class, fields);
//
//        Object api = connection.getApi();
//        if(api instanceof FacebookTemplate){
//            System.out.println("Facebook");
//        }


        // Does not work with spring-social-facebook:2.0.3.RELEASE
        UserProfile profile = connection.fetchUserProfile();

        CalendarUser user = new CalendarUser();

        if(profile.getEmail() != null){
            user.setEmail(profile.getEmail());
        }
        else if(profile.getUsername() != null){
            user.setEmail(profile.getUsername());
        }
        else {
            user.setEmail(connection.getDisplayName());
        }

        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());

        user.setPassword(randomAlphabetic(32));

        return user;

    }

} // The End...
