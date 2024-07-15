/*
 * Copyright (C) 2024. Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rs.raf.student.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
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

    //region Resource
    private static final String separator = "/";

    private static final String propertiesRoot = "/";

    private static final String propertiesFile = ".properties";
    private static final String localFile      = ".local";

    public static Properties loadLocalProperties(String fileName) throws IOException {
        Properties properties = new Properties();

        properties.load(Utilities.class.getResourceAsStream(propertiesRoot + fileName + localFile + propertiesFile));

        return properties;
    }

    //endregion

}
