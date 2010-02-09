package com.nirvana.urlmap.resource;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.nirvana.urlmap.domain.UrlTweet;
import com.nirvana.urlmap.service.UrlTweetService;

@Path("urltweet")
public class UrlTweetResource {

    private final UrlTweetService urlTweetService;

    private static final Logger logger = Logger.getLogger(UrlTweetResource.class.getName());

    @Inject
    public UrlTweetResource(UrlTweetService urlTweetService) {
        super();
        this.urlTweetService = urlTweetService;
    }

    @POST
    public Response persistLatestTweets(@FormParam("category_name")String category_name, @FormParam("city")String _city){
        

        Set<UrlTweet> urlTweets = urlTweetService.fetchLatestUrlTweets(category_name, _city);
       
        if(urlTweets!=null&&urlTweets.size()>0) urlTweetService.persistUrlTweets(urlTweets);
         
        logger.log(Level.INFO, "Persisted this category : " + " category_name : " + category_name  + "  city : " + _city);

        return Response.status(Status.OK).build();
    }
    
    
}
