package com.packtpub.springsecurity.crypto.password;

import com.packtpub.springsecurity.authentication.encoding.Sha256PasswordEncoderMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.util.Arrays;

/**
 * A utility class that can be used to convert passwords to use Sha256 with salt using Spring Security's crypto module.
 *
 * @author Rob Winch
 * @see Sha256PasswordEncoderMain
 */
public class CryptoSha256PasswordEncoderMain {

    private static final Logger logger = LoggerFactory
            .getLogger(CryptoSha256PasswordEncoderMain.class);

    public static String encode(String password) {
        StandardPasswordEncoder encoder = new StandardPasswordEncoder();
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
