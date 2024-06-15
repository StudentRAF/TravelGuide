package rs.raf.student.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HttpMethod {
    GET    ("GET"    ),
    POST   ("POST"   ),
    PUT    ("PUT"    ),
    DELETE ("DELETE" ),
    PATCH  ("PATCH"  ),
    HEAD   ("HEAD"   ),
    OPTIONS("OPTIONS");

    private final String name;

    public static HttpMethod find(String value) {
        for (HttpMethod method : HttpMethod.values())
            if (method.name.equalsIgnoreCase(value))
                return method;

        return null;
    }

}
