package com.example.workshop7;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.*;

import java.awt.print.Book;
import java.lang.reflect.Type;
import java.util.List;

// This resource file handles all CRUD operations associated with bookings.
// Necessary secondary data like classes, packages, customers, trip types
// are fetched as well as they're needed for creating bookings.

@Path("/booking")
public class BookingResource {
    public EntityManager createEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = factory.createEntityManager();
        return entityManager;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getallcustomers")
    public String getAllCustomers() {
        EntityManager entityManager = createEntityManager();
        Query query = entityManager.createQuery("SELECT c FROM Customers c");
        List<Customers> customersList = query.getResultList();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Customers>>(){}.getType();
        entityManager.close();
        return gson.toJson(customersList, type);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getlastbooking")
    public String getLastBooking() {
        EntityManager entityManager = createEntityManager();
        Query query = entityManager.createQuery("SELECT b FROM Bookings b ORDER BY b.bookingId DESC");
        List<Bookings> bookingsList = query.setMaxResults(1).getResultList();
        Gson gson = new Gson();
        entityManager.close();
        return gson.toJson(bookingsList.get(0));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getbookings/{ customerid }")
    public String getBookings(@PathParam("customerid") int customerId) {
        EntityManager entityManager = createEntityManager();
        Query query = entityManager.createQuery("SELECT b FROM Bookings b WHERE b.customerId = :customerId")
                .setParameter("customerId", customerId);
        List<Bookings> bookingsList = query.getResultList();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Bookings>>(){}.getType();
        entityManager.close();
        return gson.toJson(bookingsList, type);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getbooking/{ bookingNo }")
    public String getBookingByNo(@PathParam("bookingNo") String bookingNo) {
        EntityManager entityManager = createEntityManager();
        Query query = entityManager.createQuery("SELECT b FROM Bookings b WHERE b.bookingNo = :bookingNo")
                .setParameter("bookingNo", bookingNo);
        List<Bookings> bookingsList = query.getResultList();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Bookings>>(){}.getType();
        entityManager.close();
        return gson.toJson(bookingsList, type);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getbookingdetail/{ bookingid }")
    public String getBookingDetail(@PathParam("bookingid") int bookingId) {
        EntityManager entityManager = createEntityManager();
        Query query = entityManager.createQuery("SELECT d FROM Bookingdetails d WHERE d.bookingId = :bookingId")
                .setParameter("bookingId", bookingId);
        List<Bookingdetails> detailsList = query.getResultList();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Bookingdetails>>(){}.getType();
        entityManager.close();
        return gson.toJson(detailsList, type);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getalltriptypes")
    public String getAllTripTypes() {
        EntityManager entityManager = createEntityManager();
        Query query = entityManager.createQuery("SELECT t FROM Triptypes t");
        List<Triptypes> triptypesList = query.getResultList();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Triptypes>>(){}.getType();
        entityManager.close();
        return gson.toJson(triptypesList, type);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getallregions")
    public String getAllRegions() {
        EntityManager entityManager = createEntityManager();
        Query query = entityManager.createQuery("SELECT r FROM Regions r");
        List<Regions> regionsList = query.getResultList();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Regions>>(){}.getType();
        entityManager.close();
        return gson.toJson(regionsList, type);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getallclasses")
    public String getAllClasses() {
        EntityManager entityManager = createEntityManager();
        Query query = entityManager.createQuery("SELECT c FROM Classes c");
        List<Regions> classesList = query.getResultList();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Classes>>(){}.getType();
        entityManager.close();
        return gson.toJson(classesList, type);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getallpackages")
    public String getAllPackages() {
        EntityManager entityManager = createEntityManager();
        Query query = entityManager.createQuery("SELECT p FROM Packages p");
        List<Packages> packagesList = query.getResultList();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Packages>>(){}.getType();
        entityManager.close();
        return gson.toJson(packagesList, type);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getpackage/{ packageId }")
    public String getPackageById(@PathParam("packageId") int packageId) {
        EntityManager entityManager = createEntityManager();
        Packages packages = entityManager.find(Packages.class, packageId);
        Gson gson = new Gson();
        return gson.toJson(packages);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/postbooking")
    public String postBooking(String jsonString) {
        EntityManager entityManager = createEntityManager();
        Gson gson = new Gson();
        Bookings bookings = gson.fromJson(jsonString, Bookings.class);
        entityManager.getTransaction().begin();
        Bookings newBookings = entityManager.merge(bookings);
        String successMessage = "Booking created successfully.";
        if (newBookings == null) {
            entityManager.getTransaction().rollback();
            successMessage = "Booking failed, please try again.";
            return successMessage;
        }
        entityManager.getTransaction().commit();
        return successMessage;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/postbookingdetail")
    public String postBookingDetail(String jsonString) {
        EntityManager entityManager = createEntityManager();
        Gson gson = new Gson();
        Bookingdetails bookingDetails = gson.fromJson(jsonString, Bookingdetails.class);
        entityManager.getTransaction().begin();
        Bookingdetails newBookingDetails = entityManager.merge(bookingDetails);
        String successMessage = "Booking details created successfully.";
        if (newBookingDetails == null) {
            entityManager.getTransaction().rollback();
            successMessage = "Booking details creation failed, please try again.";
            return successMessage;
        }
        entityManager.getTransaction().commit();
        return successMessage;
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deletebooking/{bookingId}")
    public String deleteBookingDetail(@PathParam("bookingId") int bookingId) {
        EntityManager entityManager = createEntityManager();
        Query bookingDetailQuery = entityManager.createQuery("SELECT d FROM Bookingdetails d WHERE d.bookingId = :bookingId")
                .setParameter("bookingId", bookingId);
        Query bookingQuery = entityManager.createQuery("SELECT b FROM Bookings b WHERE b.bookingId = :bookingId")
                .setParameter("bookingId", bookingId);
        List<Bookingdetails> detailsList = bookingDetailQuery.getResultList();
        List<Bookings> bookingsList = bookingQuery.getResultList();
        entityManager.getTransaction().begin();
        String message = "Something went wrong.";
        for (Bookingdetails details : detailsList) {
            entityManager.remove(details);
            message = "Delete successful";
        }
        for (Bookings booking : bookingsList) {
                entityManager.remove(booking);
                message = "Delete successful";
        }
        entityManager.getTransaction().commit();
        return message;
    }
}