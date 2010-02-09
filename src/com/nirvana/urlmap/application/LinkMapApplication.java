package com.nirvana.urlmap.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.nirvana.urlmap.resource.LinkMapResource;

public class LinkMapApplication extends Application{

    
    public LinkMapApplication(){
        
    }
    
    /**
     * @see javax.ws.rs.core.ApplicationConfig#getResourceClasses()
     */
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> rrcs = new HashSet<Class<?>>();
        rrcs.add(LinkMapResource.class);
        return rrcs;
    }

}
