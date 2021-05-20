package ecommerce;

import ecommerce.Product;
import ecommerce.ProductService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This class contains the methods that will respond to the various endpoints that you define for your RESTful API Service.
 *
 */
//products will be the pathsegment that precedes any path segment specified by @Path on a method.
@Path("/products")
public class ProductResource {


    @Path("{id}")
    @GET
    @Produces( { MediaType.APPLICATION_JSON }) //This provides only JSON responses
    public Response getProductById(@PathParam("id") int id/* The {id} placeholder parameter is resolved */) {
        //invokes the DB method which will fetch a product item object by id
        Product p = ProductService.getProductById(id);


        if(p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Respond with a 200 OK if you have a product object to return as response
        return Response.ok(p).build();
    }

    //This method represents an endpoint with the URL /products and a GET request.
    // Since there is no @PathParam mentioned, the /products as a relative path and a GET request will invoke this method.
    @GET
    @Produces( { MediaType.APPLICATION_JSON })
    public Response getAllProducts() {
        List<Product> productList = ProductService.getAllProducts();

        if(productList == null || productList.isEmpty()) {

        }

        return Response.ok(productList).build();
    }

    //This method represents an endpoint with the URL /todos and a POST request.
    // Since there is no @PathParam mentioned, the /todos as a relative path and a POST request will invoke this method.
    @POST
    @Consumes({MediaType.APPLICATION_JSON}) //This method accepts a request of the JSON type
    public Response addProduct(Product p) {

        //The todo object here is automatically constructed from the json request. Jersey is so cool!
        if(ProductService.AddProduct(p)) {
            return Response.ok().entity("Product Added Successfully").build();
        }

        // Return an Internal Server error because something wrong happened. This should never be executed
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    }



    //This method represents a PUT request where the id is provided as a path parameter and the request body is provided in JSON
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateProduct(@PathParam("id") int id, Product p) {

        // Retrieve the todo that you will need to change
        Product current = ProductService.getProductById(id);

        if(current == null) {
            //If not found then respond with a 404 response.
            return Response.status(Response.Status.NOT_FOUND).
                    entity("We could not find the requested resource").build();
        }

        // This is the todo_object retrieved from the json request sent.
        p.setId(id);

        // if the user has provided null, then we set the retrieved values.
        // This is done so that a null value is not passed to the todo object when updating it.
        if(p.getDescription() == null) {
            p.setDescription(current.getDescription());
        }

        if(p.getPrice() == 0) {
            p.setPrice(current.getPrice());
        }
        
        if(p.getName() == null) {
            p.setName(current.getName());
        }
        
        //Same as above. We only change fields in the todo_resource when the user has entered something in a request.
        if (p.getUrl() == null) {
            p.setUrl(current.getUrl());
        }

        //This calls the JDBC method which in turn calls the the UPDATE SQL command
        if(ProductService.updateProduct(p)) {

            return Response.ok().entity(p).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    }

    //This method represents a DELETE request where the id is provided as a path parameter and the request body is provided in JSON
    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    public Response deleteProduct(@PathParam("id") int id) {

        //Retrieve the todo_object that you want to delete.
        Product current = ProductService.getProductById(id);

        if(current == null) {
            //If not found throw a 404
            return Response.status(Response.Status.NOT_FOUND).
                    entity("We could not find the requested resource").build();
        }

        // This calls the JDBC method which in turn calls the DELETE SQL command.
        if(ProductService.deleteProduct(current)) {
            return Response.ok().entity("Product Deleted Successfully").build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    }

}
