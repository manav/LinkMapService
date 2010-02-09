package com.nirvana.urlmap.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public final class PMF implements Provider<PersistenceManagerFactory> {
    
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    public PersistenceManagerFactory get() {
        return pmfInstance;
    }
}

