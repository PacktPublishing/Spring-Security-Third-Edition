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
//        Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
//        Facebook facebook = connection.getApi();
//        String [] fields = { "id", "email",  "first_name", "last_name" };
//        User userProfile = facebook.fetchObject("me", User.class, fields);

//        Object api = connection.getApi();
//        if(connection instanceof FacebookTemplate){
//            System.out.println("HERE");
//        }
        /*
            <form name='facebookSocialloginForm'
                  action="<c:url value='/auth/facebook' />" method='POST'>
                <input type="hidden" name="scope"
                    value="public_profile,email,user_about_me,user_birthday,user_likes"/>
                ...
            </form>
         */

        // FIXME: Does not work with Facebook:
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
