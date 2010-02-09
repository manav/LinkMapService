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

@Path("cron/cache/urltweet")
public class UrlTweetCacheQueueResource {

    private static final Logger logger = Logger.getLogger(UrlTweetCacheQueueResource.class.getName());

    @GET
    public Response fetchLinkTweets(){
     
           Queue queue = QueueFactory.getQueue("updateCacheFour");

           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=1&my_host=us").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=2&my_host=us").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=3&my_host=us").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=1&my_host=us").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=2&my_host=us").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=3&my_host=us").method(Method.GET));

           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=1&my_host=us").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=2&my_host=us").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=3&my_host=us").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=1&my_host=us").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=2&my_host=us").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=3&my_host=us").method(Method.GET));

/*           //chennai
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=1&my_host=chennai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=2&my_host=chennai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=3&my_host=chennai").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=1&my_host=chennai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=2&my_host=chennai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=3&my_host=chennai").method(Method.GET));

           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=1&my_host=chennai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=2&my_host=chennai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=3&my_host=chennai").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=1&my_host=chennai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=2&my_host=chennai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=3&my_host=chennai").method(Method.GET));

           //bangalore
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=1&my_host=bangalore").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=2&my_host=bangalore").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=3&my_host=bangalore").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=1&my_host=bangalore").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=2&my_host=bangalore").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=3&my_host=bangalore").method(Method.GET));

           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=1&my_host=bangalore").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=2&my_host=bangalore").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=3&my_host=bangalore").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=1&my_host=bangalore").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=2&my_host=bangalore").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=3&my_host=bangalore").method(Method.GET));

           
           //delhi
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=1&my_host=delhi").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=2&my_host=delhi").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=3&my_host=delhi").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=1&my_host=delhi").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=2&my_host=delhi").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=3&my_host=delhi").method(Method.GET));

           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=1&my_host=delhi").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=2&my_host=delhi").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=3&my_host=delhi").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=1&my_host=delhi").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=2&my_host=delhi").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=3&my_host=delhi").method(Method.GET));

           
           //mumbai
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=1&my_host=mumbai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=2&my_host=mumbai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=b&rpp=6&days_ago=3&my_host=mumbai").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=1&my_host=mumbai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=2&my_host=mumbai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=p&rpp=6&days_ago=3&my_host=mumbai").method(Method.GET));

           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=1&my_host=mumbai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=2&my_host=mumbai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=s&rpp=6&days_ago=3&my_host=mumbai").method(Method.GET));
           
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=1&my_host=mumbai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=2&my_host=mumbai").method(Method.GET));
           queue.add(url("/linktweets.json?category_name=e&rpp=6&days_ago=3&my_host=mumbai").method(Method.GET));
*/

           return Response.status(Status.OK).build();
    }

}
