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

import com.dkarakaya.sample.models.Comment;
import com.dkarakaya.sample.services.CommentService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CommentResource {

	private CommentService commentService = new CommentService();

	@GET
	public List<Comment> getComments(@PathParam("postId") int postId, @BeanParam FilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			if (filterBean.getMonth() > 0) {
				if (filterBean.getDay() > 0)
					return commentService.getCommentsOfPostForDay(postId, filterBean.getDay(), filterBean.getMonth(),
							filterBean.getYear());
				return commentService.getCommentsOfPostForMonth(postId, filterBean.getMonth(), filterBean.getYear());
			}
			return commentService.getCommentsOfPostForYear(postId, filterBean.getYear());

		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return commentService.getCommentsOfPostPaginated(postId, filterBean.getStart(), filterBean.getSize());
		}

		if (filterBean.isPostedToday())
			return commentService.getCommentsOfPostForToday(postId);
		return commentService.getCommentsOfPost(postId);
	}

	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("postId") int postId, @PathParam("commentId") int commentId,
			@Context UriInfo uriInfo) {
		Comment comment = commentService.getComment(postId, commentId);
		if(comment == null)
			return comment;
		comment.addLink(getUriForSelf(uriInfo, comment), "self");
		comment.addLink(getUriForPost(uriInfo, comment), "post");
		return comment;
	}

	@POST
	public Response addComment(@PathParam("postId") int postId, Comment comment, @Context UriInfo uriInfo) {
		Comment newComment = commentService.addComment(postId, comment);
		newComment.addLink(getUriForSelf(uriInfo, comment), "self");
		newComment.addLink(getUriForPost(uriInfo, comment), "post");
		String newId = String.valueOf(newComment.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newComment).build();
	}

	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("postId") int postId, @PathParam("commentId") int commentId,
			Comment comment, @Context UriInfo uriInfo) {
		Comment updatedComment = commentService.updateComment(postId, commentId, comment);
		if(updatedComment == null)
			return updatedComment;
		updatedComment.addLink(getUriForSelf(uriInfo, comment), "self");
		updatedComment.addLink(getUriForPost(uriInfo, comment), "post");
		return updatedComment;
	}

	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("postId") int postId, @PathParam("commentId") int commentId) {
		commentService.removeComment(postId, commentId);
	}

	private String getUriForPost(UriInfo uriInfo, Comment comment) {
		URI uri = uriInfo.getBaseUriBuilder().path(PostResource.class).path(String.valueOf(comment.getPostId()))
				.build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Comment comment) {
		URI uri = uriInfo.getBaseUriBuilder().path(PostResource.class).path(PostResource.class, "getCommentResource")
				.path(CommentResource.class).path(String.valueOf(comment.getId()))
				.resolveTemplate("postId", comment.getPostId()).build();
		return uri.toString();
	}

}
