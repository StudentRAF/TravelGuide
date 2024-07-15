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

package rs.raf.student.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.exception.ExceptionUtils;
import rs.raf.student.service.DestinationService;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status;

@Path("/destinations")
public class DestinationResource {

    @Inject
    private DestinationService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("page") int          page,
                           @QueryParam("size") int          size,
                           @QueryParam("sort") List<String> sort) {
        return ExceptionUtils.handleResponse(() -> Response.status(Status.OK)
                                                           .entity(service.getAll(Pageable.of(page, size, sort)))
                                                           .build());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        return ExceptionUtils.handleResponse(() -> Response.status(Status.OK)
                                                           .entity(service.getById(id))
                                                           .build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid DestinationCreateDto createDto) {
        return ExceptionUtils.handleResponse(() -> Response.status(Status.CREATED)
                                                           .entity(service.create(createDto))
                                                           .build());
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid DestinationUpdateDto updateDto) {
        return ExceptionUtils.handleResponse(() -> Response.status(Status.OK)
                                                           .entity(service.update(updateDto))
                                                           .build());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        return ExceptionUtils.handleResponse(() -> service.delete(id), Response.status(Status.OK)
                                                                               .build());
    }

}
