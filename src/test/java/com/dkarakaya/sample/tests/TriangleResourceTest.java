package com.dkarakaya.sample.tests;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.dkarakaya.sample.resources.TriangleResource;

public class TriangleResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(TriangleResource.class);
	}

	@Test
	public void givenGetTriangle_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("/triangle").queryParam("a", 2).queryParam("b", 2).queryParam("c", 2).request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void givenGetTriangle_whenCorrectRequest_thenMediaTypeIsCorrect() {
		Response response = target("/triangle").request().get();

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}
	
	@Test
	public void givenGetEquilateralTriangle_whenCorrectRequest_thenResponseIsCorrect() {
		Response response = target("/triangle").queryParam("a", 2).queryParam("b", 2).queryParam("c", 2).request().get();

		String content = response.readEntity(String.class);
		assertEquals("Content of response is: ", "Equilateral", content);
	}
	
	@Test
	public void givenGetIsoscelesTriangle_whenCorrectRequest_thenResponseIsCorrect() {
		Response response = target("/triangle").queryParam("a", 2).queryParam("b", 2).queryParam("c", 3).request().get();

		String content = response.readEntity(String.class);
		assertEquals("Content of response is: ", "Isosceles", content);
	}
	
	@Test
	public void givenGetScaleneTriangle_whenCorrectRequest_thenResponseIsCorrect() {
		Response response = target("/triangle").queryParam("a", 2).queryParam("b", 3).queryParam("c", 4).request().get();

		String content = response.readEntity(String.class);
		assertEquals("Content of response is: ", "Scalene", content);
	}
	
	@Test
	public void givenGetNotTriangle_whenCorrectRequest_thenResponseIsCorrect() {
		Response response = target("/triangle").queryParam("a", 2).queryParam("b", 2).queryParam("c", 4).request().get();

		String content = response.readEntity(String.class);
		assertEquals("Content of response is: ", "Not a triangle", content);
	}
}
