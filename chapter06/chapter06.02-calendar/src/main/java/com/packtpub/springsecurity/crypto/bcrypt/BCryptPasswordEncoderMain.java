package com.packtpub.springsecurity.crypto.bcrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

/**
 * A utility class that can be used to convert passwords to use BCrypt.
 * Implementation of PasswordEncoder that uses the BCrypt strong hashing function.
 *
 * @author Mick Knutson
 * @see BCryptPasswordEncoder
 */
public class BCryptPasswordEncoderMain {

    private static final Logger logger = LoggerFactory
            .getLogger(BCryptPasswordEncoderMain.class);

    public static String encode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String encodedPassword = encoder.encode(password);
        return encodedPassword;
    }

    /**
     * Encode a single password if supplied as args[0]
     * Otherwise, encode the standard passwords:
     * <pre>"user1", "admin1", "user2"</pre>
     *
     * @param args single password to encode
     */
    public static void main(String[] args) {
        String[] passwords = {"user1", "admin1", "user2"};

        if (args.length == 1) {
            logger.info(encode(args[0]));
        }
        else {
            logger.info("Encoding passwords: {}", Arrays.toString(passwords));
            for(String psswd: passwords){
                logger.info("[{}]", encode(psswd));
            }
        }
    }

} // The End...
