package au.edu.unsw.soacourse.foundIT.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.soacourse.foundIT.modeler.applicationBean;
import au.edu.unsw.soacourse.foundIT.serviceImpl.applicationImpl;

@Path("applications")
public class applicationServiceProvider {

	@Context
	Request request;
	@Context
	UriInfo uri;
	public applicationImpl applicationImpl = new applicationImpl();

	@DELETE
	@Path("{Id}")
	public void delete(@PathParam("Id") String id) {

		boolean flag = applicationImpl.deleteApplication(id);
		if (!flag)
			throw new RuntimeException("DELETE: application with " + id + " not found");

	}

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<applicationBean> getAllApplications() {

		List<applicationBean> bs = new ArrayList<applicationBean>();
		bs = applicationImpl.getAllApplications();

		return bs;

	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		// int count = BooksDao.instance.getStore().size();
		return Integer.toString(applicationImpl.getAllApplications().size());
	}

	@GET
	@Path("{Id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public applicationBean getApplication(@PathParam("Id") String id) {

		applicationBean b = applicationImpl.getSpecificApplication(id);
		if (b == null) {
			throw new RuntimeException("GET: application with " + id + " not found");
		}
		return b;
	}

	// @XmlType(propOrder = { "id", "detail", "skill", "experience" })
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String newPosting(
			// @FormParam("id") String id,
			@FormParam("link") String link, 
			@FormParam("coverLetter") String coverLetter,
			@FormParam("status") String status,
			@FormParam("license") String license,
			@FormParam("address") String address,
			@FormParam("fullname") String fullname) throws IOException {

		applicationBean app = new applicationBean();
		if (link != null) {
			app.setLink(link);
		}
		if (coverLetter != null) {
			app.setCoverLetter(coverLetter);
		}
		if (status != null) {
			app.setStatus(status);
		}
		if (license != null) {
			app.setLicense(license);
		}
		if (address != null) {
			app.setAddress(address);
		}
		if (fullname != null) {
			app.setFullname(fullname);
		}

		//
		return applicationImpl.createApplication(app);
		// TODO: Fix here so that it returns the new book
	}

	// to be continued......
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createApplication(applicationBean app) throws Exception {

		return putAndGetResponse(app);

	}

	private Response putAndGetResponse(applicationBean app) {
		javax.ws.rs.core.Response res;
		boolean flag = applicationImpl.updateApplication(app);
		if (flag)
			res = javax.ws.rs.core.Response.created(uri.getAbsolutePath()).build();
		else {
			res = javax.ws.rs.core.Response.noContent().build();
		}
		return res;

	}

}
