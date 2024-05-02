package org.example.resources;

import org.example.entities.Comment;
import org.example.entities.Post;
import org.example.service.PostService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/posts")
public class PostResource {

    @Inject
    private PostService postService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.postService.getAllPosts()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer i) {
        return Response.ok(this.postService.getPostById(i)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid Post post) {
        return Response.ok(this.postService.addPost(post)).build();
    }

    @POST
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(@PathParam("id") Integer i, @Valid Comment comment) {
        return Response.ok(this.postService.addComment(i, comment)).build();
    }

}
