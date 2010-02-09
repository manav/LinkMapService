package com.demo.tweets.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This application demonstrates how to construct a relatively complex user
 * interface, similar to many common email readers. It has no back-end,
 * populating its components with hard-coded data.
 */
public class MustSeeTweetsWidget implements EntryPoint {

    private static MustSeeTweetsWidget singleton;

    /**
     * Gets the singleton Mail instance.
     */
    public static MustSeeTweetsWidget get() {
        return singleton;
    }


    /**
     * This method constructs the application user interface by instantiating
     * controls and hooking up event handler.
     */
    public void onModuleLoad() {
        singleton = this;

        // create a timeLine Widget
        TweetDeckContainer urlTweetWidgetPolitics = new TweetDeckContainer("p",null);
        TweetDeckContainer urlTweetWidgetBusiness = new TweetDeckContainer("b",null);

        HorizontalPanel horizontalPanelOne = new HorizontalPanel();
        horizontalPanelOne.addStyleName("w-row");
        horizontalPanelOne.setSpacing(0);
        horizontalPanelOne.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        horizontalPanelOne.add(urlTweetWidgetPolitics);
        horizontalPanelOne.add(urlTweetWidgetBusiness);
        
        
        RootPanel.get("widgets-one").add(horizontalPanelOne);
        
        
        // Create a new timer that calls Window.alert().
        Timer t = new Timer() {
            
          public void run() {
              
              //the code below will download asynchronously at a later point in future
              GWT.runAsync(new RunAsyncCallback() {
                  public void onFailure(Throwable caught) {
                    Window.alert("Code download failed");
                  }

                  public void onSuccess() {
                      TweetDeckContainer urlTweetWidgetSports = new TweetDeckContainer("s",null);
                      TweetDeckContainer urlTweetWidgetEntertainment = new TweetDeckContainer("e",null);

                      HorizontalPanel horizontalPanelTwo = new HorizontalPanel();
                      horizontalPanelTwo.addStyleName("w-row");
                      horizontalPanelTwo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
                      horizontalPanelTwo.setSpacing(0);
                      horizontalPanelTwo.add(urlTweetWidgetEntertainment);
                      horizontalPanelTwo.add(urlTweetWidgetSports);

                      RootPanel.get("widgets-two").add(horizontalPanelTwo);

                  }
                });

          }
        };

        // Schedule the timer to run once in 5 seconds.
        t.schedule(2000);

        // Hook the window resize event, so that we can adjust the UI.

        // Get rid of scrollbars, and clear out the window's built-in margin,
        // because we want to take advantage of the entire client area.
        Window.enableScrolling(true);
        Window.setMargin("0px");

        
        // Finally, add the outer panel to the RootPanel, so that it will be displayed.

    }


}
