package com.nirvana.urlmap.resource;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

@Path("cron/queue/user")
public class UserQueueResource {

    private static final Logger logger = Logger.getLogger(UserQueueResource.class.getName());

    @GET
    public Response addUsers(){
     
           Queue queue = QueueFactory.getQueue("addUserThree");

           queue.add(url("/user").param("screen_name", "usPoliticsFeed").method(Method.POST));
           queue.add(url("/user").param("screen_name", "usBizRobot").method(Method.POST));
           queue.add(url("/user").param("screen_name", "usSportsFeed").method(Method.POST));
           queue.add(url("/user").param("screen_name", "usEtFeed").method(Method.POST));
           
           return Response.status(Status.OK).build();
    }
    
}