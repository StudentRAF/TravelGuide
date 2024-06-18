package rs.raf.student.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rs.raf.student.domain.Pageable;
import rs.raf.student.exception.ExceptionUtils;
import rs.raf.student.service.ActivityService;

import java.util.List;

@Path("/activities")
public class ActivityResource {

    @Inject
    private ActivityService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("page") int          page,
                           @QueryParam("size") int          size,
                           @QueryParam("sort") List<String> sort) {
        return ExceptionUtils.handleResponse(() -> Response.status(Response.Status.OK)
                                                           .entity(service.getAll(Pageable.of(page, size, sort)))
                                                           .build());
    }

}
