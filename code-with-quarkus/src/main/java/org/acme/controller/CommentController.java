package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.Blog;
import org.acme.Comment;
import org.acme.User;
import org.acme.repository.BlogRepository;
import org.acme.repository.CommentRepository;
import org.acme.repository.UserRepository;

import java.util.List;

@Path("/api/users/{userId}/blogs/{blogId}/comments")
public class CommentController {

    @Inject
    CommentRepository commentRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    BlogRepository blogRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@PathParam("blogId") Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        return Response.ok(comments).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addComment(@PathParam("userId") Long userId, @PathParam("blogId") Long blogId, Comment comment) {
        User user = userRepository.findById(userId);
        Blog blog = blogRepository.findById(blogId);

        if (user == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity("User  not found").build();
        }

        if (blog == null ) {
            return Response.status(Response.Status.NOT_FOUND).entity(" Blog not found").build();
        }


        comment.setUser(user);
        comment.setBlog(blog);
        comment.setId(null);
        commentRepository.persist(comment);

        return Response.status(Response.Status.CREATED).entity(comment).build();
    }


    @DELETE
    @Path("/{commentId}")
    public Response deleteComment(@PathParam("userId") Long userId, @PathParam("blogId") Long blogId, @PathParam("commentId") Long commentId) {
        commentRepository.deleteByIdAndUserIdAndBlogId(commentId, userId, blogId);
        return Response.noContent().build();
    }
}
