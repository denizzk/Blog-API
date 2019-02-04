package com.dkarakaya.sample.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.dkarakaya.sample.models.Post;
import com.dkarakaya.sample.services.PostService;

@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

	PostService postService = new PostService();

	@GET
	public List<Post> getPosts(@BeanParam FilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			if (filterBean.getMonth() > 0) {
				if (filterBean.getDay() > 0)
					return postService.getAllPostsForDay(filterBean.getDay(), filterBean.getMonth(),
							filterBean.getYear());
				return postService.getAllPostsForMonth(filterBean.getMonth(), filterBean.getYear());
			}
			return postService.getAllPostsForYear(filterBean.getYear());

		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return postService.getAllPostsPaginated(filterBean.getStart(), filterBean.getSize());
		}

		if (filterBean.isPostedToday())
			return postService.getAllPostsForToday();
		return postService.getAllPosts();
	}

	@GET
	@Path("/{postId}")
	public Post getPost(@PathParam("postId") int id, @Context UriInfo uriInfo) {
		Post post = postService.getPost(id);
		if (post == null)
			return post;
		post.addLink(getUriForSelf(uriInfo, post), "self");
		post.addLink(getUriForComments(uriInfo, post), "comments");
		return post;
	}

	@POST
	public Response addPost(Post post, @Context UriInfo uriInfo) {
		Post newPost = postService.addPost(post);
		newPost.addLink(getUriForSelf(uriInfo, post), "self");
		newPost.addLink(getUriForComments(uriInfo, post), "comments");
		String newId = String.valueOf(newPost.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newPost).build();
	}

	@PUT
	@Path("/{postId}")
	public Post updatePost(@PathParam("postId") int id, Post post, @Context UriInfo uriInfo) {
		post.setId(id);
		Post updatedPost = postService.updatePost(post);
		if (updatedPost == null)
			return updatedPost;
		updatedPost.addLink(getUriForSelf(uriInfo, post), "self");
		updatedPost.addLink(getUriForComments(uriInfo, post), "comments");
		return updatedPost;
	}

	@DELETE
	@Path("/{postId}")
	public void deletePost(@PathParam("postId") int id) {
		postService.removePost(id);
	}

	@Path("/{postId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

	private String getUriForSelf(UriInfo uriInfo, Post post) {
		String uri = uriInfo.getBaseUriBuilder().path(PostResource.class).path(Long.toString(post.getId())).build()
				.toString();
		return uri;
	}

	private String getUriForComments(UriInfo uriInfo, Post post) {
		URI uri = uriInfo.getBaseUriBuilder().path(PostResource.class).path(PostResource.class, "getCommentResource")
				.path(CommentResource.class).resolveTemplate("postId", post.getId()).build();
		return uri.toString();
	}

}