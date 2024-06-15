package rs.raf.student.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PathRequest {

    public static PathRequest of(HttpMethod method, String path) {
        if (path.charAt(0) != '/')
            path = "/" + path;

        path = path.replaceAll("[0-9]+", "id");

        return new PathRequest(method, path);
    }

    private HttpMethod method;
    private String     path;

}
