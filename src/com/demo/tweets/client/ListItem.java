package com.demo.tweets.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ListItem extends ComplexPanel implements HasHTML {
   
    public ListItem() {
        setElement(DOM.createElement("LI"));
        setStyleName("tweet-ordered-list-item");
    }

    public void add(Widget w) {
        super.add(w, getElement());
    }

    public void insert(Widget w, int beforeIndex) {
        super.insert(w, getElement(), beforeIndex, true);
    }


    @Override
    public String getHTML() {
        return DOM.getInnerHTML(getElement());
    }

    @Override
    public void setHTML(String html) {
        DOM.setInnerHTML(getElement(), (html == null) ? "" : html);
    }

    @Override
    public String getText() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setText(String text) {
        // TODO Auto-generated method stub
        
    }

}
