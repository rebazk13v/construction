package controller;

import model.Cart;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/cart")
public class cartServlet {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addToCart(
            @FormParam("productId") String productId,
            @FormParam("productPrice") String productPrice,
            @FormParam("productName") String productName
    ) {
        Cart cart = new Cart(productId, productPrice, productName);
        try {
            cart.insertIntoDatabase();
            return Response.ok("Item added to cart").build();
        } catch (SQLException ex) {
            Logger.getLogger(cartServlet.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getCartContents() {
        // Retrieve cart contents from the database or any other source
        String cartContents = "Product A, Product B, Product C";

        return Response.ok(cartContents).build();
    }
}
