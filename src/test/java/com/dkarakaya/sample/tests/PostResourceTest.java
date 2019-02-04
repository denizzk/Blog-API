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

public class PostResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(PostResource.class);
	}

	// GET
	// http://localhost:8080/sample/posts
	@Test
	public void givenGetPosts_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetPosts_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts").request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts?year=2019
	@Test
	public void givenGetYearPosts_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts").queryParam("year", 2019).request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetYearPosts_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts").queryParam("year", 2019).request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts?year=2019&month=02
	@Test
	public void givenGetMonthPosts_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts").queryParam("year", 2019).queryParam("month", 02).request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetMonthPosts_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts").queryParam("year", 2019).queryParam("month", 02).request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts?year=2019&month=02&day=02
	@Test
	public void givenGetDayPosts_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts").queryParam("year", 2019).queryParam("month", 02).queryParam("day", 02)
				.request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetDayPosts_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts").queryParam("year", 2019).queryParam("month", 02).queryParam("day", 02)
				.request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts?postedToday=true
	@Test
	public void givenGetTodayPosts_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts").queryParam("postedToday", "true").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetTodayPosts_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts").queryParam("postedToday", "true").request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts?start=5&size=10
	@Test
	public void givenGetPaginatedPosts_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts").queryParam("start", 2).queryParam("size", 2).request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetPaginatedPosts_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts").queryParam("start", 2).queryParam("size", 2).request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// GET
	// http://localhost:8080/sample/posts/4
	@Test
	public void givenGetPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/posts/4").request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetPost_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/posts/4").request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// POST
	// http://localhost:8080/sample/posts
	@Test
	public void givenPostPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("posts").request().post(Entity.json(
				"{\"imageUrl\":\"https://picsum.photos/200/300/?image=22\",\"likeCount\":78,\"message\":\"Test\"}"));

		assertEquals("Http Response should be 201 ", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// PUT
	// http://localhost:8080/sample/posts/35
	@Test
	public void givenPutPost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("posts/35").request().put(Entity.json(
				"{\"imageUrl\":\"https://picsum.photos/200/300/?image=22\",\"likeCount\":78,\"message\":\"Test\"}"));

		assertEquals("Http Response should be 200 ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	@Test
	public void givenPutPost_whenCorrectRequest_thenResponseIsNoContent() {
		Response response = target("posts/33").request().put(Entity.json(
				"{\"imageUrl\":\"https://picsum.photos/200/300/?image=22\",\"likeCount\":78,\"message\":\"Test\"}"));

		assertEquals("Http Response should be 204 ", Status.NO_CONTENT.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	// DELETE
	// http://localhost:8080/sample/posts/36
	@Test
	public void givenDeletePost_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("posts/33").request().delete();

		assertEquals("Http Response should be 204 ", Status.NO_CONTENT.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", null, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}
}
