package com.nirvana.urlmap.guice.modules;

import javax.cache.Cache;
import javax.jdo.PersistenceManagerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.nirvana.urlmap.cache.CMF;
import com.nirvana.urlmap.persistence.PMF;
import com.nirvana.urlmap.resource.LinkMapResource;
import com.nirvana.urlmap.resource.UrlTweetLatestCacheQueueResource;
import com.nirvana.urlmap.resource.UrlTweetResource;
import com.nirvana.urlmap.resource.UrlTweetCacheQueueResource;
import com.nirvana.urlmap.resource.UrlTweetQueueResource;
import com.nirvana.urlmap.resource.UserQueueResource;
import com.nirvana.urlmap.resource.UserResource;
import com.nirvana.urlmap.service.TwitterRestService;
import com.nirvana.urlmap.service.TwitterSearchService;
import com.nirvana.urlmap.service.TwitterServiceImpl;
import com.nirvana.urlmap.service.UrlTweetService;
import com.nirvana.urlmap.service.UrlTweetServiceImpl;
import com.nirvana.urlmap.service.UserService;
import com.nirvana.urlmap.service.UserServiceImpl;

public class LinkMapModule extends AbstractModule{

    @Override
    protected void configure() {
        
        bind(LinkMapResource.class); 
        bind(UrlTweetResource.class);
        bind(UserResource.class);

        bind(UserQueueResource.class);
        bind(UrlTweetQueueResource.class);
        
        bind(UrlTweetCacheQueueResource.class);
        bind(UrlTweetLatestCacheQueueResource.class);
        
        bind(TwitterRestService.class).to(TwitterServiceImpl.class).in(Singleton.class);
        bind(TwitterSearchService.class).to(TwitterServiceImpl.class).in(Singleton.class);
        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
        bind(UrlTweetService.class).to(UrlTweetServiceImpl.class).in(Singleton.class);
        bind(PersistenceManagerFactory.class).toProvider(PMF.class);
        bind(Cache.class).toProvider(CMF.class);
    }

}
