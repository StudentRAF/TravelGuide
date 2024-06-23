package rs.raf.student.filter;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import rs.raf.student.domain.HttpMethod;
import rs.raf.student.domain.PathRequest;
import rs.raf.student.exception.ExceptionUtils;
import rs.raf.student.model.UserRole;
import rs.raf.student.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.ws.rs.core.Response.Status;
import static rs.raf.student.domain.HttpMethod.DELETE;
import static rs.raf.student.domain.HttpMethod.GET;
import static rs.raf.student.domain.HttpMethod.OPTIONS;
import static rs.raf.student.domain.HttpMethod.POST;
import static rs.raf.student.domain.HttpMethod.PUT;
import static rs.raf.student.model.UserRole.ADMIN;
import static rs.raf.student.model.UserRole.ANYONE;
import static rs.raf.student.model.UserRole.EDITOR;

@Provider
@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {

    private static final Map<PathRequest, List<UserRole>> authorizationMap = new HashMap<>(100);

    static {
        authorizationMap.putAll(Map.of(
            PathRequest.of(GET,  "/users"      ), List.of(ADMIN),
            PathRequest.of(GET,  "/users/id"   ), List.of(ADMIN),
            PathRequest.of(POST, "/users"      ), List.of(ADMIN),
            PathRequest.of(PUT,  "/users"      ), List.of(ADMIN),
            PathRequest.of(POST, "/users/login"), List.of(ANYONE)
        ));

        authorizationMap.putAll(Map.of(
            PathRequest.of(GET,    "/destinations"   ), List.of(ANYONE),
            PathRequest.of(GET,    "/destinations/id"), List.of(ANYONE),
            PathRequest.of(POST,   "/destinations"   ), List.of(ADMIN, EDITOR),
            PathRequest.of(PUT,    "/destinations"   ), List.of(ADMIN, EDITOR),
            PathRequest.of(DELETE, "/destinations"   ), List.of(ADMIN, EDITOR)
        ));

        authorizationMap.putAll(Map.of(
            PathRequest.of(GET,    "/articles"               ), List.of(ANYONE),
            PathRequest.of(GET,    "/articles/id"            ), List.of(ANYONE),
            PathRequest.of(GET,    "/articles/destination/id"), List.of(ANYONE),
            PathRequest.of(GET,    "/articles/activity/id"   ), List.of(ANYONE),
            PathRequest.of(POST,   "/articles"               ), List.of(ADMIN, EDITOR),
            PathRequest.of(PUT,    "/articles"               ), List.of(ADMIN, EDITOR),
            PathRequest.of(DELETE, "/articles"               ), List.of(ADMIN, EDITOR)
        ));

        authorizationMap.putAll(Map.of(
            PathRequest.of(POST, "/comments"), List.of(ANYONE)
        ));

        authorizationMap.putAll(Map.of(
            PathRequest.of(GET, "/activities"), List.of(ANYONE)
        ));
    }

    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        HttpMethod method = HttpMethod.find(requestContext.getMethod());
        String     path   = requestContext.getUriInfo()
                                          .getPath();

        if (OPTIONS.equals(method))
            return;

        List<UserRole> authorizedRoles = authorizationMap.get(PathRequest.of(method, path));

        if (authorizedRoles == null) {
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
            return;
        }

        if (authorizedRoles.contains(ANYONE)) {
            return;
        }

        UserRole role = ExceptionUtils.handleReturning(() -> userService.decodeRole(requestContext.getHeaderString("Authorization")));

        if (role == null || !authorizedRoles.contains(role)) {
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
        }
    }

}
