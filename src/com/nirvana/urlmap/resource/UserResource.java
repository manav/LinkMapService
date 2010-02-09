package com.nirvana.urlmap.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.nirvana.urlmap.service.UserService;

@Path("user")
public class UserResource {

    private final UserService userService;
    
    private static final Logger logger = Logger.getLogger(UserResource.class.getName());

    @Inject
    public UserResource(UserService userService) {
        super();
        this.userService = userService;
    }

    @POST
    public Response addUser(@FormParam("screen_name")String screen_name){

         userService.addUser(screen_name);        
         logger.log(Level.INFO, "Added this User : " + " screen_name " + screen_name );

         return Response.status(Status.OK).build();
    }
    

}
