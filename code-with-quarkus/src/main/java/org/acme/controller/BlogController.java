package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.Blog;
import org.acme.User;
import org.acme.repository.BlogRepository;
import org.acme.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Path("/api/users/{userId}/blogs")
public class BlogController {

    @Inject
    BlogRepository blogRepository;

    @Inject
    UserRepository userRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBlogs(@PathParam("userId") Long userId) {
        List<Blog> blogs = blogRepository.findByUserId(userId);
        return Response.ok(blogs).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createBlog(@PathParam("userId") Long userId, Blog blog) {
        User user = userRepository.findById(userId);
        System.out.println("Blog is :::::::::::::: " + blog);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        blog.setUser(user);
        blog.setCreatedDate(new Date());
        blog.setId(null);

        try {
           blog.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating blog" + e.getMessage()).build();
        }
        return Response.status(Response.Status.CREATED).entity(blog).build();
    }

    @GET
    @Path("/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBlogById(@PathParam("userId") Long userId, @PathParam("blogId") Long blogId) {
        Blog blog = blogRepository.findByIdAndUserId(blogId, userId);
        return Response.ok(blog).build();
    }

    @PUT
    @Path("/{blogId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBlog(@PathParam("userId") Long userId, @PathParam("blogId") Long blogId, Blog updatedBlog) {
        Blog blog = blogRepository.findByIdAndUserId(blogId, userId);
        if (blog != null) {
            blog.setTitle(updatedBlog.getTitle());
            blog.setContent(updatedBlog.getContent());
            blogRepository.persist(blog);
            return Response.ok(blog).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{blogId}")
    public Response deleteBlog(@PathParam("userId") Long userId, @PathParam("blogId") Long blogId) {
        blogRepository.deleteByIdAndUserId(blogId, userId);
        return Response.noContent().build();
    }
}

