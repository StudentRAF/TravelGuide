package rs.raf.student.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Utilities {

    //region Iterator

    public static <Type> Stream<Type> createStream(Iterator<Type> iterator) {
        return StreamSupport.stream(((Iterable<Type>)(() -> iterator)).spliterator(), false);
    }

    public static <Type> List<Type> createList(Iterator<Type> iterator) {
        return createStream(iterator).collect(Collectors.toList());
    }

    //endregion

    //region Password

    private static final Random saltGenerator = new SecureRandom();

    public static String hashPassword(String password, String salt) {
        return encodeBase64(DigestUtils.sha256(password + salt));
    }

    public static String generateSalt() {
        byte[] salt = new byte[16];

        saltGenerator.nextBytes(salt);

        return encodeBase64(salt);
    }

    private static String encodeBase64(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    //endregion

}
