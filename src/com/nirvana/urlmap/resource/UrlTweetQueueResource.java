package com.nirvana.urlmap.resource;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.*;

@Path("cron/queue/urltweet")
public class UrlTweetQueueResource {

    private static final Logger logger = Logger.getLogger(UrlTweetQueueResource.class.getName());
    
    @GET
    public Response fetchLinkTweets(){
     
           Queue queue = QueueFactory.getQueue("updateTweetsThree");
           
           queue.add(url("/urltweet").param("category_name", "e").param("city", "us").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "p").param("city", "us").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "b").param("city", "us").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "s").param("city", "us").method(Method.POST));
           
           queue.add(url("/urltweet").param("category_name", "e").param("city", "chennai").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "p").param("city", "chennai").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "b").param("city", "chennai").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "s").param("city", "chennai").method(Method.POST));

           queue.add(url("/urltweet").param("category_name", "e").param("city", "mumbai").method(Method.POST));
           /*           queue.add(url("/urltweet").param("category_name", "p").param("city", "mumbai").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "b").param("city", "mumbai").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "s").param("city", "mumbai").method(Method.POST));

           queue.add(url("/urltweet").param("category_name", "e").param("city", "delhi").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "p").param("city", "delhi").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "b").param("city", "delhi").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "s").param("city", "delhi").method(Method.POST));

           queue.add(url("/urltweet").param("category_name", "e").param("city", "bangalore").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "p").param("city", "bangalore").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "b").param("city", "bangalore").method(Method.POST));
           queue.add(url("/urltweet").param("category_name", "s").param("city", "bangalore").method(Method.POST));
*/
           
           return Response.status(Status.OK).build();
    }

}
