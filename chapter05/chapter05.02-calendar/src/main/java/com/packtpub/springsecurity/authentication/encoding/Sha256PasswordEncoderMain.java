package com.packtpub.springsecurity.authentication.encoding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import com.packtpub.springsecurity.crypto.password.CryptoSha256PasswordEncoderMain;

import java.util.Arrays;

/**
 * A utility class that can be used to convert passwords to use Sha256 without any salt. This uses Spring Security's old
 * {@link PasswordEncoder} interface. Typically applications should prefer the new crypto module's PasswordEncoder as
 * demonstrated in {@link CryptoSha256PasswordEncoderMain}.
 *
 * @author Rob Winch
 * @see CryptoSha256PasswordEncoderMain
 */
public class Sha256PasswordEncoderMain {

    private static final Logger logger = LoggerFactory
            .getLogger(Sha256PasswordEncoderMain.class);

    public static String encode(String password) {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String encodedPassword = encoder.encodePassword(password, null);
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
