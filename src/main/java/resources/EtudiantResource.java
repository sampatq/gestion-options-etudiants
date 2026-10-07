package resources;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
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

@Path("etudiants")
@Produces(MediaType.APPLICATION_JSON)
public class EtudiantResource {
    private final EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    private final OptionBusiness optionBusiness = new OptionBusiness();

    @GET
    public List<Etudiant> getAllEtudiants() {
        return etudiantBusiness.getAllEtudiants();
    }

    @GET
    @Path("/{identifiant}")
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    @GET
    @Path("/option/{codeOption}")
    public Response getEtudiantsByOption(@PathParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiantBusiness.getEtudiantsByOption(option)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (!etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(etudiant).build();
    }

    @PUT
    @Path("/{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant,
                                   Etudiant updatedEtudiant) {
        if (updatedEtudiant == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (updatedEtudiant.getOption() != null) {
            Option option = optionBusiness.getOptionByCode(
                    updatedEtudiant.getOption().getCodeOption());
            if (option == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            updatedEtudiant.setOption(option);
        }
        if (!etudiantBusiness.updateEtudiant(identifiant, updatedEtudiant)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedEtudiant).build();
    }

    @DELETE
    @Path("/{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (!etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}

