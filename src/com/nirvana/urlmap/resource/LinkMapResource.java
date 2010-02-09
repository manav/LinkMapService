package com.nirvana.urlmap.resource;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.nirvana.urlmap.dto.UrlTweetDTO;
import com.nirvana.urlmap.service.UrlTweetService;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("linktweets.json")
public class LinkMapResource {

    private final UrlTweetService urlTweetService;
 
    private static final Logger logger = Logger.getLogger(LinkMapResource.class.getName());
    
    @Inject
    public LinkMapResource(UrlTweetService urlTweetService) {
        super();
        this.urlTweetService = urlTweetService;
    }

    @GET
    @Produces( { APPLICATION_JSON })
    public String  getUrlTweetsPostedByFriends(@HeaderParam("host") String _host,@QueryParam("my_host") String my_host, @QueryParam("category_name") String category_name ,@QueryParam("screen_name") String screen_name,@QueryParam("rpp") int _rpp,@QueryParam("before_id")  @DefaultValue("9223372036854775807") long before_id){

        String host=null;
        String[] hosts = _host.split("\\.");
        // get the  substring until the first occurrence of "." (quotes to be ignored)

        if(my_host!=null&&my_host.length()>0){
            host=my_host;
        }
        
        else {
            host=hosts[0];
        
            //make an exception for local host
            if(host.startsWith("localhost")) host="us";
        
        }
            
            
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<UrlTweetDTO>>() {}.getType();
       
        if(category_name!=null && category_name.length()>0 && screen_name==null)
            return gson.toJson(( urlTweetService).getTweetsWithUrlInThisSocialGraph(category_name,host,new Long(before_id),_rpp,0), listType);

        else if(screen_name!=null && screen_name.length()>0 && category_name==null){
            return gson.toJson(( urlTweetService).getTweetsWithUrlInThisSocialGraph(screen_name,host,new Long(before_id),_rpp,0), listType);
        }
        
        return null;
    }

       
    
}
