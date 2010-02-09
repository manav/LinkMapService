package com.demo.tweets.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 *
 */
public class TweetDeckContainer extends Composite implements SelectionHandler<Integer> {

    DecoratedTabPanel timeLineTabPanel = new DecoratedTabPanel();
    TweetDeck         todaysTweetList;

    //TODO implement Builder pattern
    public TweetDeckContainer(String categoryName, String screenName) {
        initWidget(timeLineTabPanel);

        // Instantiate tabs
        todaysTweetList = new TweetDeck(categoryName,screenName);
        
        DecoratorPanel decoratedTodaysTweetList = new DecoratorPanel();
        decoratedTodaysTweetList.setWidget(todaysTweetList);
        decoratedTodaysTweetList.addStyleName("tweet-deck-container");
        
        // add the tweet list to tab panel
        timeLineTabPanel.add(decoratedTodaysTweetList, "most recent");
        timeLineTabPanel.add(new HTML(), "most tweeted");
        timeLineTabPanel.add(new HTML(), "most visited");
        
        timeLineTabPanel.selectTab(0);

        // add selection handler
        timeLineTabPanel.addSelectionHandler(this);

        // initialize the widget
        todaysTweetList.setVisible(true);

        // set up style names
        timeLineTabPanel.addStyleName("tweet-tab-panel");


    }

    public void onSelection(SelectionEvent<Integer> event) {
        int selected = event.getSelectedItem();
        switch (selected) {
            case 0:
                if (!todaysTweetList.isVisible())
                    todaysTweetList.setVisible(true);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;

        }

    }

}
