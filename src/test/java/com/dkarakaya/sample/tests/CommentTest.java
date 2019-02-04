package com.dkarakaya.sample.tests;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.dkarakaya.sample.resources.PostResource;

public class CommentTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(PostResource.class);
	}

	// GET
	// http://localhost:8080/sample/posts/4/comments
	@Test
	public void givenGetCommentsOfPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts/4/comments").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetCommentsOfPost_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts/4/comments").request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts/4/comments?year=2019
	@Test
	public void givenGetYearCommentsOfPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts/4/comments").queryParam("year", 2019).request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetYearCommentsOfPost_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts/4/comments").queryParam("year", 2019).request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts/4/comments?year=2019&month=02
	@Test
	public void givenGetMonthCommentsOfPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts/4/comments").queryParam("year", 2019).queryParam("month", 02).request()
				.get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetMonthCommentsOfPost_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts/4/comments").queryParam("year", 2019).queryParam("month", 02).request()
				.get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts/4/comments?year=2019&month=02&day=02
	@Test
	public void givenGetDayCommentsOfPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts/4/comments").queryParam("year", 2019).queryParam("month", 02)
				.queryParam("day", 02).request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetDayCommentsOfPost_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts/4/comments").queryParam("year", 2019).queryParam("month", 02)
				.queryParam("day", 02).request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts/4/comments?postedToday=true
	@Test
	public void givenGetTodayCommentsOfPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts/4/comments").queryParam("postedToday", "true").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetTodayCommentsOfPost_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts/4/comments").queryParam("postedToday", "true").request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts/4/comments?start=5&size=10
	@Test
	public void givenGetPaginatedCommentsOfPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts/4/comments").queryParam("start", 2).queryParam("size", 2).request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetPaginatedCommentsOfPost_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts/4/comments").queryParam("start", 2).queryParam("size", 2).request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts/4/comments/7
	@Test
	public void givenGetCommentOfPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts/4/comments/7").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetCommentOfPost_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts/4/comments/7").request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// POST
	// http://localhost:8080/sample/posts/4/comments
	@Test
	public void givenPostComment_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("posts/4/comments").request()
				.post(Entity.json("{\"likeCount\":78,\"message\":\"Test Comment.\"}"));

		assertEquals("Http Response should be 201 ", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// PUT
	// http://localhost:8080/sample/posts/4/comments/43
	@Test
	public void givenPutComment_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("posts/4/comments/43").request()
				.put(Entity.json("{\"likeCount\":78,\"message\":\"Test Comment Updated.\"}"));

		assertEquals("Http Response should be 200 ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	@Test
	public void givenPutComment_whenCorrectRequest_thenResponseIsNoContent() {
		Response response = target("posts/4/comments/44").request()
				.put(Entity.json("{\"likeCount\":78,\"message\":\"Test\"}"));

		assertEquals("Http Response should be 204 ", Status.NO_CONTENT.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// DELETE
	// http://localhost:8080/sample/posts/4/comments/55
	@Test
	public void givenDeletePost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("posts/4/comments/55").request().delete();

		assertEquals("Http Response should be 204 ", Status.NO_CONTENT.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

}
