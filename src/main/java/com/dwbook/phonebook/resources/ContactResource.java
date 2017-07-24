package com.dwbook.phonebook.resources;


import com.dwbook.phonebook.dao.ContactDAO;
import com.dwbook.phonebook.representations.Contact;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Set;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import javax.validation.Validator;
import javax.ws.rs.core.Response.Status;

import static javax.ws.rs.core.Response.ok;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDAO contactDAO;
    private final Validator validator;

    public ContactResource(DBI jdbi, Validator validator){
        contactDAO = jdbi.onDemand((ContactDAO.class));
        this.validator =  validator;
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id){
        Contact contact = contactDAO.getContactById(id);
        return ok(contact).build();
    }

    @POST
    public Response createContact(Contact contact) throws URISyntaxException {

        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        if(violations.size() > 0){
            ArrayList<String> validationMessages = new ArrayList<>();
            for(ConstraintViolation<Contact> violation : violations){
                validationMessages.add(violation.getPropertyPath().toString() + ":" + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        int newContactId = contactDAO.createContact(contact.getFirstName(),
                                                    contact.getLastName(),
                                                    contact.getPhone());

        return Response.created(new URI(String.valueOf(newContactId))).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id, Contact contact){

        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        if(violations.size() > 0){
            ArrayList<String> validationMessages = new ArrayList<>();
            for(ConstraintViolation<Contact> violation : violations){
                validationMessages.add(violation.getPropertyPath().toString() + ":" + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        contactDAO.updateContact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());
        return ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone())).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id){
        contactDAO.deleteContact(id);
        return Response.noContent().build();
    }

}
