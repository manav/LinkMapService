package com.demo.tweets.client;

import com.google.gwt.core.client.JavaScriptObject;

// An overlay type
public class UrlTweet extends JavaScriptObject  {

      // Overlay types always have protected, zero-arg ctors
      protected UrlTweet() { } 
        

      // Typically, methods on overlay types are JSNI
      public final native String getScreenName() /*-{ return this.screenName; }-*/;
      public final native String getName() /*-{ return this.name; }-*/;
      public final native String getUserId() /*-{ return this.UserId; }-*/;
      public final native String getStatus() /*-{ return this.urlTweet; }-*/;
      public final native double getTweetId() /*-{ return this.statusId; }-*/;
      public final native String getCreatedAt() /*-{ return this.createdAt; }-*/;
      
      // Note, though, that methods aren't required to be JSNI
      public final String getStatusInDisplayFormat() {
        return getStatus() + " " + getScreenName()+ " " + getCreatedAt(); 
      }
      
      public final Long getStatusId(){
       return new Long(new Double(getTweetId()).longValue());   
      }

}
