package rs.raf.student.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import rs.raf.student.domain.Page;
import rs.raf.student.dto.user.UserGetDto;
import rs.raf.student.service.UserService;

@Path("/users")
public class UserResource {

    @Inject
    private UserService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<UserGetDto> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserGetDto getById(@PathParam("id") Long id) {
        return service.getById(id);
    }

}
