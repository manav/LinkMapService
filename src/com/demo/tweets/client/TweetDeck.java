package com.demo.tweets.client;

import java.util.LinkedList;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TweetDeck extends LazyPanel implements ClickHandler{

    //state variables
    private String screenName=null;
    private String categoryName;
    
    //parent container
    private DockPanel tweetDockPanel = null;

    //horz panel with prev next buttons
    private HorizontalPanel paginationContainer = null;
    private HTML prevButton = null;
    private HTML nextButton = null;

    //decorated panel with ordered list
    private VerticalPanel tweetListPanel = null;
    private OrderedList orderedList =null;
    private LinkedList<ListItem> listItems= null;

    private Long nextStatusId=null;
    private static final int  VISIBLE_COUNT=4;
    private int rpp=VISIBLE_COUNT+1; //results per page 
    private int displayPageNo=1;
    private HTML loadingImageElement = new HTML();
    private HTML errorMessageElement = new HTML("Something went wrong. Please refresh or try again later");
    
    private static final int STATUS_CODE_OK = 200;
    private static final int MAX_RETRY_COUNT = 3;

    private int retryAttempt=0;
    
    /**
     * The static loading image displayed when loading CSS or source code.
     */
    private static String loadingImage;

    public TweetDeck(String categoryName,String screnName){
        //set up state variables
        this.screenName = screnName;
        this.categoryName = categoryName;
    }


    private void displayTweets(){

        orderedList.clear();
        
        int endCount = ((displayPageNo *VISIBLE_COUNT)>listItems.size())?listItems.size():(displayPageNo *VISIBLE_COUNT);

        //if there are more tweets show next button
        if(nextStatusId!=null || (endCount< listItems.size())) nextButton.setVisible(true); else{nextButton.setVisible(false);};
        
        if(displayPageNo>1)prevButton.setVisible(true); else{prevButton.setVisible(false);};
        
        int k=0;
        for(int i =((displayPageNo *(VISIBLE_COUNT)) -(VISIBLE_COUNT)) ;i< endCount; i++ ){
           orderedList.insert(listItems.get(i),k++);
        }
    }

    public void doFetchURL(){
        //
        String querParamOne = "";
        if(screenName!=null)querParamOne="screen_name="+screenName.trim();
        else if(categoryName!=null)querParamOne="category_name="+categoryName.trim();
        
        RequestBuilder requestBuilder = null;
        if(nextStatusId!=null){
            requestBuilder = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() +"linktweets.json?"+querParamOne+"&before_id="+nextStatusId+"&rpp="+rpp);
        }else{
            requestBuilder = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() +"linktweets.json?"+querParamOne+"&rpp="+rpp); 
        }
        requestBuilder.setTimeoutMillis(3000);
                
                try {
                    paginationContainer.add(loadingImageElement);
                    requestBuilder.sendRequest(null, new JSONResponseTextHandler());
                
                } catch (RequestException e) {
                    errorMessageElement.setHTML("Failed...  " +e.getMessage());
                    paginationContainer.add(errorMessageElement);
                }
                

    }

    public void onClick(ClickEvent event) {
        Object sender = event.getSource();
        
        if (sender == nextButton) {
            displayPageNo=displayPageNo+1 ;
            if(nextStatusId!=null && listItems.size()<((displayPageNo)*VISIBLE_COUNT) )  {doFetchURL();}
            else{ displayTweets();}

        } else if (sender == prevButton) {
            displayPageNo=displayPageNo-1;
            displayTweets();
        } 
    }

    /**
     * Class for handling the response text associated with a request for a JSON
     * object.
     */
    private class JSONResponseTextHandler implements RequestCallback {

    public void onError(Request request, Throwable exception) {
        
        paginationContainer.remove(loadingImageElement);
        if (exception instanceof RequestTimeoutException) {
              
              // handle a request timeout
              
              if(retryAttempt >= MAX_RETRY_COUNT){
                  
                  //reset the retry attempts
                  retryAttempt=0;
                  
                  //permanent failure
                  errorMessageElement.setHTML("Failed after Max allowed attempts..Try Refreshing the page");

              } else{
                  //try again
                  retryAttempt++;
                  errorMessageElement.setHTML("Timed Out .. Retrying attempt no : "+ retryAttempt );
                  doFetchURL();
              }
              
            } else {
             // handle other request errors
                errorMessageElement.setHTML("Failed...  " +exception.getMessage());
              
            }
        
        paginationContainer.add(errorMessageElement);

      }

      @SuppressWarnings("unchecked")
    public void onResponseReceived(Request request, Response response) {
            
          paginationContainer.remove(loadingImageElement);
          paginationContainer.remove(errorMessageElement);
          
            if (response.getStatusCode() != STATUS_CODE_OK ) {
                paginationContainer.add(new HTML("Failed.." + response.getStatusCode()));
            }
            
            else if(response.getText()==null || response.getText().length()==0){
                paginationContainer.add(new HTML("Empty.." + response.getStatusCode()));   
            }

                JSONValue jsonValue = JSONParser.parse(response.getText());
                JSONArray jsonArray        = jsonValue.isArray();
                JsArray<UrlTweet> tweets = (JsArray<UrlTweet>) jsonArray.getJavaScriptObject();
                
                if(tweets.length()==rpp){
                    nextStatusId =  tweets.get(tweets.length()-1).getStatusId();
                }else{
                    nextStatusId=null;
                }
                
                updateListItems(tweets);
                displayTweets();

            
        }
     }

    
    private void updateListItems(JsArray<UrlTweet> tweets) {
        
        ListItem listItem = null;
        
        // tweets.length()-1 because the last tweet is used to show 'next button'
        for(int i =0  ;i< tweets.length(); i++ ){
           listItem =   new ListItem();
           listItem.setHTML("<span class=tweet-status>" +tweets.get(i).getStatus() +"<br></br>" +"</span>"+ "<span class=tweet-name>" + tweets.get(i).getName() + "</span>" + "("+"<span class=tweet-id>" + tweets.get(i).getScreenName() + "</span>"+ ")" + "&nbsp;&nbsp;&nbsp;&nbsp;<span class=tweet-date>" + tweets.get(i).getCreatedAt() +"</span>");
           
           //add the list item to the end of the list
           listItems.add(listItem);
        }
        
    }


    
    @Override
    protected Widget createWidget() {
        tweetDockPanel = new DockPanel();
        paginationContainer = new HorizontalPanel();
        tweetListPanel = new VerticalPanel();

        if (loadingImage == null) {
            loadingImage = "<img src=\"" + GWT.getModuleBaseURL()
                + "images/loading.gif\">";
          }
        
        prevButton = new HTML("<a href='javascript:;'>&lt;&lt; prev</a>",true);
        nextButton = new HTML("<a href='javascript:;'>next &gt;&gt;</a>",true);
        loadingImageElement.setHTML("&nbsp;&nbsp;" + loadingImage + "&nbsp;&nbsp;" + "Loading...");
        
        
        //hide the display 
        orderedList= new OrderedList();
        listItems = new LinkedList<ListItem>();

        tweetListPanel.add(orderedList);

        //set up the alignments layout
        prevButton.setWidth("70px");
        nextButton.setWidth("70px");
        
        prevButton.setVisible(false);
        nextButton.setVisible(false);
        
        paginationContainer.add(prevButton);
        paginationContainer.add(nextButton);
        
        paginationContainer.setCellHorizontalAlignment(prevButton, HasAlignment.ALIGN_RIGHT);
        paginationContainer.setCellHorizontalAlignment(nextButton, HasAlignment.ALIGN_LEFT);
        paginationContainer.setCellVerticalAlignment(prevButton, HasAlignment.ALIGN_MIDDLE);
        paginationContainer.setCellVerticalAlignment(nextButton, HasAlignment.ALIGN_MIDDLE);
        

        //add the buttons to the pagination container
        paginationContainer.setSpacing(6);
        
        paginationContainer.addStyleName("pagination-row");
        //add click handlers
        prevButton.addClickHandler(this);
        nextButton.addClickHandler(this);
        //add the button container to the tweetDeckContainer
        tweetDockPanel.add(paginationContainer, DockPanel.NORTH);

        //add the tweetList to the tweetDeckContainer
        tweetDockPanel.add(tweetListPanel, DockPanel.CENTER);
        
        

        doFetchURL();

        return tweetDockPanel;
    }


}
