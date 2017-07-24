package com.dwbook.phonebook.resources;

import com.dwbook.phonebook.representations.Contact;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Produces(MediaType.TEXT_PLAIN)
@Path("/client/")
public class ClientResource {

    private Client client;

    public ClientResource(Client client){
        this.client = client;
    }

    @GET
    @Path("showContact")
    public String showContact(@QueryParam("id") int id){
        WebResource contactResource = client.resource("Http://localhost:8080/contact/"+id);
        Contact c = contactResource.get(Contact.class);
        String output = "ID: " + id
                        + "\nFirst Name: " + c.getFirstName()
                        + "\nLast Name: " + c.getLastName()
                        + "\nPhone: " + c.getPhone();
        return output;
    }


    @GET
    @Path("newContact")
    public Response newContact(@QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName, @QueryParam("phone") String phone){
        WebResource contactResource = client.resource("http://localhost:8080/contact");
        ClientResponse response = contactResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,
                                new Contact(0, firstName, lastName, phone));
        if(response.getStatus() == 201){
            return Response.status(302).entity("The contact was created successfully!").build();
        } else {
            return Response.status(422).entity(response.getEntity(String.class)).build();
        }
    }

    @GET
    @Path("updateContact")
    public Response updateContact(@QueryParam("id") int id,
                                  @QueryParam("firstName") String firstName,
                                  @QueryParam("lastName") String lastName,
                                  @QueryParam("phone") String phone){
        WebResource contactResource = client.resource("http://localhost:8080/contact/" + id);
        ClientResponse response = contactResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class,
                                                        new Contact(id, firstName, lastName, phone));
        if(response.getStatus() == 200){
            return Response.status(302).entity("The contact was successfully updated!").build();
        } else {
            return Response.status(422).entity(response.getEntity(String.class)).build();
        }
    }



    @GET
    @Path("deleteContact")
    public Response deleteContact(@QueryParam("id") int id){
        WebResource contactResource = client.resource("http://localhost:8080/contact/" + id);
        contactResource.delete();
        return Response.noContent().entity("Contact was deleted").build();
    }


}
