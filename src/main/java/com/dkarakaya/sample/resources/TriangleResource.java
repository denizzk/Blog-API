package com.dkarakaya.sample.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.dkarakaya.sample.services.TriangleService;

@Path("/triangle")
@Produces(MediaType.APPLICATION_JSON)
public class TriangleResource {

	TriangleService triangleService = new TriangleService();

	@GET
	public String getTriangle(@QueryParam("a") int a, @QueryParam("b") int b, @QueryParam("c") int c) {

		return triangleService.checkTriangle(a, b, c);
	}

}
