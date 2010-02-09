package com.nirvana.urlmap.cache;

import java.util.HashMap;
import java.util.Map;

import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.cache.Cache;

import com.google.appengine.api.memcache.stdimpl.GCacheFactory;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public final class CMF implements Provider<Cache> {
    
    private static Cache cache;

    public Cache get() {

        Map props = new HashMap();
        props.put(GCacheFactory.EXPIRATION_DELTA, 3600);

        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(props);
           
        } catch (CacheException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cache;
      
    }

}

