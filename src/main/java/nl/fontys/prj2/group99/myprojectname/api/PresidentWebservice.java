package nl.fontys.prj2.group99.myprojectname.api;

import nl.fontys.prj2.group99.myprojectname.dataservices.DAOlite;
import nl.fontys.prj2.group99.myprojectname.dataservices.PostgresDAOlite;
import nl.fontys.prj2.group99.myprojectname.model.SimplePresident;
import nl.fontys.prj2.group99.myprojectname.services.PresidentService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * The type President webservice.
 */
@Path("myresource")
public class PresidentWebservice {

    @Context
    UriInfo info;

    private DAOlite<SimplePresident> db = new PostgresDAOlite("localhost");
    private PresidentService presidentService = new PresidentService();


    /**
     * Read all list. Implemention hardcoded! Think of how you can improve that for your PRJ2 case!
     *
     * reach it via http://localhost:8080/webapi/myresource/presidents
     *
     * @return the list
     */
    @GET
    @Path("presidents")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SimplePresident> readAll() {
        List<SimplePresident> all = db.getAll();
//        List<SimplePresident> result = new ArrayList<SimplePresident>();
//        all.add(new SimplePresident("Geert Monsieur", 1983));
        return all;
    }


    /**
     * Find response.
     *
     * http://localhost:8080/webapi/myresource/presidents/1
     *
     * @param id the id
     * @return the response
     */
    @GET
    @Path("presidents/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Integer id) {
        SimplePresident president = presidentService.getPresidentById(id);
        if (president==null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.status(Response.Status.OK).entity(president).build();
    }
}