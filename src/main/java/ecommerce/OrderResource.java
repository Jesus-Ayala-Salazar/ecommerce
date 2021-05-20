


import com.uci.todorestservice.model.Orders;

import com.uci.todorestservice.service.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/orders")
public class OrderResource {


    @Path("{id}")
    @GET
    @Produces( { MediaType.APPLICATION_JSON }) //This provides only JSON responses
    public Response getOrderById(@PathParam("id") int id/* The {id} placeholder parameter is resolved */) {
        //invokes the DB method which will fetch a todo_list item object by id
        Orders order = OrderService.getOrderById(id);

        //Respond with a 404 if there is no such todo_list item for the id provided
        if(order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Respond with a 200 OK if you have a todo_list_item object to return as response
        return Response.ok(order).build();
    }


    @GET
    @Produces( { MediaType.APPLICATION_JSON })
    public Response getAllOrders() {
        List<Orders> orderList = OrderService.getAllOrders();

        if(orderList == null || orderList.isEmpty()) {

        }

        return Response.ok(orderList).build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON}) //This method accepts a request of the JSON type
    public Response addOrder(Orders order) {

        //The todo object here is automatically constructed from the json request. Jersey is so cool!
        if(OrderService.AddOrder(order)) {
            return Response.ok().entity("Order Added Successfully").build();
        }

        // Return an Internal Server error because something wrong happened. This should never be executed
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    }

// We add order with the value from cart so i dont think we need a form submission version    
//Similar to the method above.
//    @POST
//    @Consumes({MediaType.APPLICATION_FORM_URLENCODED}) //This method accepts form parameters.
//    //If you were to send a POST through a form submit, this method would be called.
//    public Response addTodo(@FormParam("summary") String summary,
//                            @FormParam("description") String description) {
//        Todo todo = new Todo();
//        todo.setSummary(summary);
//        todo.setDescription(description);
//
//        System.out.println(todo);
//
//        if(TodoService.AddTodo(todo)) {
//            return Response.ok().entity("TODO Added Successfully").build();
//        }
//
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//
//
//    }

    //This method represents a PUT request where the id is provided as a path parameter and the request body is provided in JSON
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateOrder(@PathParam("id") int id, Orders order) {

        // Retrieve the todo that you will need to change
        Orders current = OrderService.getOrderById(id);

        if(current == null) {
            //If not found then respond with a 404 response.
            return Response.status(Response.Status.NOT_FOUND).
                    entity("We could not find the requested resource").build();
        }

 
        order.setOrderId(id);

        // if the user has provided null, then we set the retrieved values.
        // This is done so that a null value is not passed to the todo object when updating it.
        if(order.getCustomerId() == 0) {
            order.setCustomerId(current.getCustomerId());
        }
        
        if(order.getProductId() == 0) {
            order.setProductId(current.getProductId());
        }  
        
        if(order.getQuantity() == 0) {
            order.setQuantity(current.getQuantity());
        }
        


        //This calls the JDBC method which in turn calls the the UPDATE SQL command
        if(OrderService.updateOrders(order)) {

            return Response.ok().entity(order).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    }

    //This method represents a DELETE request where the id is provided as a path parameter and the request body is provided in JSON
    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    public Response deleteOrder(@PathParam("id") int id) {

        //Retrieve the todo_object that you want to delete.
        Orders current = OrderService.getOrderById(id);

        if(current == null) {
            //If not found throw a 404
            return Response.status(Response.Status.NOT_FOUND).
                    entity("We could not find the requested resource").build();
        }

        // This calls the JDBC method which in turn calls the DELETE SQL command.
        if(OrderService.deleteOrder(current)) {
            return Response.ok().entity("Order Deleted Successfully").build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    }

}
