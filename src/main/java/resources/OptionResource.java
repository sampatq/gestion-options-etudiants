package resources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
@Produces(MediaType.APPLICATION_JSON)
public class OptionResource {
    private final OptionBusiness optionBusiness = new OptionBusiness();

    @GET
    public List<Option> getAllOptions() {
        return optionBusiness.getListeOptions();
    }

    @GET
    @Path("/{code}")
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    @GET
    @Path("/domaine/{domaine}")
    public List<Option> getOptionsByDomaine(@PathParam("domaine") String domaine) {
        return optionBusiness.getOptionsByDomaine(domaine);
    }

    @GET
    @Path("/semestre/{semestre}")
    public List<Option> getOptionsBySemestre(@PathParam("semestre") int semestre) {
        return optionBusiness.getOptionsBySemestre(semestre);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        if (option == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        optionBusiness.addOption(option);
        return Response.status(Response.Status.CREATED).entity(option).build();
    }

    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option updatedOption) {
        if (updatedOption == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (!optionBusiness.updateOption(code, updatedOption)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedOption).build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (!optionBusiness.deleteOption(code)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}

