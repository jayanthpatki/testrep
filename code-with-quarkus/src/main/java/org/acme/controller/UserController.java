package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.HttpHeaders;
import org.acme.JWTUtil;
import org.acme.LoginResponse;
import org.acme.User;
import org.acme.repository.UserRepository;


@Path("/api/auth")
public class UserController {

    @Inject
    UserRepository userRepository;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User loginRequest) {
        User user = userRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            // Generate token logic here (pseudo code)
            String token = JWTUtil.generateToken(user);
            return Response.ok(new LoginResponse(user.getId(), user.getUsername(), token)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response register(User registrationRequest) {
        // Check if the username already exists
        User existingUser = userRepository.find("username", registrationRequest.getUsername()).firstResult();
        if (existingUser != null) {
            return Response.status(Response.Status.CONFLICT).entity("Username already exists").build();
        }

        // Save the new user to the database
        userRepository.persist(registrationRequest);
        return Response.status(Response.Status.CREATED).entity(registrationRequest).build();
    }
}

