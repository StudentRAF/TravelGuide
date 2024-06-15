package rs.raf.student.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.exception.ExceptionUtils;
import rs.raf.student.service.CommentService;

import static jakarta.ws.rs.core.Response.Status;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid CommentCreateDto createDto) {
        return ExceptionUtils.handleResponse(() -> Response.status(Status.CREATED)
                                                           .entity(service.create(createDto))
                                                           .build());
    }

}
