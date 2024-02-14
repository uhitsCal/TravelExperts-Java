/**
 * Author: Calvin C
 * Date: November 6, 2023
 */

package com.example.workshop7;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.Customers;

import java.lang.reflect.Type;
import java.util.List;

//Methods to handle CRUD operations related to customers

@Path("/customers")
public class CustomerResource {
    public EntityManager createEntityManager() {
        EntityManagerFactory fac = Persistence.createEntityManagerFactory("default");
        EntityManager em = fac.createEntityManager();
        return em;
    }

    @GET //get all customers
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getallcustomers")
    public String getAllCustomers() {
        EntityManager em = createEntityManager();
        Query q = em.createQuery("SELECT c FROM Customers c");
        List<Customers> customersList = q.getResultList();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Customers>>() {}.getType();
        em.close();
        return gson.toJson(customersList, type);
    }

    @GET //get one customers
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getcustomer/{customerId}")
    public String getCustomer(@PathParam("customerId") int customerId) {
        EntityManager em = createEntityManager();
        Customers customer = em.find(Customers.class, customerId);
        Gson gson = new Gson();
        return gson.toJson(customer);
    }
    @POST //update customer
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updatecustomer")
    public String postCustomer(String jsonString) {
        EntityManager em = createEntityManager();
        Gson gson = new Gson();
        Customers customer = gson.fromJson(jsonString, Customers.class);
        // Start a transaction
        em.getTransaction().begin();
        // Merge the customer
        Customers newCustomer = em.merge(customer);
        // Declare a message
        String message;
        if (newCustomer != null) {
            // Transaction successful, commit it
            em.getTransaction().commit();
            message = "{ 'message': 'Customer updated successfully', 'customer': '" + newCustomer.toString() + "' }";
        } else {
            // Transaction failed, rollback
            em.getTransaction().rollback();
            message = "{ 'message': 'updating Customer failed', 'customer': '" + customer.toString() + "' }";
        }
        em.close();
        return message;
    }

    @PUT //add new customer
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addcustomer")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addCustomer(String jsonString) {
        EntityManager em = createEntityManager();
        Gson gson = new Gson();
        Customers customer = gson.fromJson(jsonString, Customers.class);
        // Start a transaction
        em.getTransaction().begin();
        // Insert the customer
        em.persist(customer);
        // Declare a message
        String message;
        if (em.contains(customer)) {
            // Transaction successful, commit it
            em.getTransaction().commit();
            message = "{ 'message': 'Customer added successfully', 'customer': '" + customer.toString() + "' }";
        } else {
            // Transaction failed, rollback
            em.getTransaction().rollback();
            message = "{ 'message': 'Customer addition failed', 'customer': '" + customer.toString() + "' }";
        }

        em.close();
        return message;
    }

    @DELETE //delete customer
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deletecustomer/{customerId}") // Updated path parameter to match "customerId"
    public String deleteCustomer(@PathParam("customerId") int customerId) {
        EntityManager em = createEntityManager();
        // Retrieve the customer entity
        Customers customer = em.find(Customers.class, customerId);

        // Start a transaction
        em.getTransaction().begin();

        // Declare a message
        String message;

        if (customer == null) {
            // Customer does not exist, rollback the transaction
            em.getTransaction().rollback();
            message = "{ 'message': 'Customer does not exist' }";
        } else {
            em.remove(customer);

            if (em.contains(customer)) {
                // Customer removal failed, rollback the transaction
                em.getTransaction().rollback();
                message = "{ 'message': 'Customer removal failed' }";
            } else {
                // Customer removed, commit the transaction
                em.getTransaction().commit();
                message = "{ 'message': 'Customer removed' }";
            }
        }

        em.close();
        return message;
    }

}