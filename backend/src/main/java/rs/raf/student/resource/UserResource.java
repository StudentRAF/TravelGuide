package rs.raf.student.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.service.UserService;

import static jakarta.ws.rs.core.Response.Status;

@Path("/users")
public class UserResource {

    @Inject
    private UserService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.status(Status.OK)
                       .entity(service.getAll())
                       .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        return Response.status(Status.OK)
                       .entity(service.getById(id))
                       .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid UserCreateDto createDto) {
        return Response.status(Status.CREATED)
                       .entity(service.create(createDto))
                       .build();
    }

}
