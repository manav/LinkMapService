package com.demo.tweets.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * The top panel, which contains the 'welcome' message and various links.
 */
public  class LogInWidget extends Composite implements ClickHandler ,HasLogInEventHandlers<String> {

	final private  HorizontalPanel signInPanel = new HorizontalPanel();
	final private  Button sendButton = new Button("Explore");
	final private  TextBox nameField = new TextBox();
	final private  Label textToServerLabel = new Label();
	final private  HTML serverResponseLabel = new HTML();
	final private  DialogBox dialogBox = new DialogBox();
	final private  Button closeButton = new Button("Close");

	private static final String SERVER_ERROR = "Oops, Please try again.";
	

  public LogInWidget() {
	  
      //set up alignment
      signInPanel.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
      signInPanel.setVerticalAlignment(HasAlignment.ALIGN_MIDDLE);
      
      signInPanel.add(nameField);
      signInPanel.add(sendButton);
      //add the text box and button to sign in horizontal panel
      signInPanel.setSpacing(10);
      signInPanel.setCellVerticalAlignment(nameField, HasAlignment.ALIGN_MIDDLE);
      signInPanel.setCellVerticalAlignment(sendButton, HasAlignment.ALIGN_MIDDLE);
      signInPanel.setCellHorizontalAlignment(nameField, HasAlignment.ALIGN_CENTER);
      signInPanel.setCellHorizontalAlignment(sendButton, HasAlignment.ALIGN_CENTER);
      
	  nameField.setText("Twitter Username");

      // We can add style names to widgets
	  sendButton.addStyleName("explore-button");
	  signInPanel.addStyleName("sign-in-panel");
	  nameField.addStyleName("sign-in-text-box");
	  
		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box

		dialogBox.setText("Loading Tweets with URL");
		dialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
  
		// Add a handler to send the name to the server
		sendButton.addClickHandler(this);
	
		nameField.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event) {
                nameField.setText("");                
            }
		    
		});
		
      initWidget(signInPanel);
  
  }



	/**
	 * Fired when the user clicks on the sendButton.
	 */
	  public void onClick(ClickEvent event) {
		sendNameToServer();
	}

	/**
	 * Fired when the user types in the nameField.
	 */
	public void onKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			sendNameToServer();
		}
	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	private void sendNameToServer() {

	    final String textToServer = nameField.getText();
        doFire(textToServer);

	}


	  public void doFire(String screenName){
			LogInEvent.fire(this, screenName);

	  }
	  
	public HandlerRegistration addLogInEventHandler(
			LogInEventHandler<String> handler) {
	    return addHandler(handler, LogInEvent.getType());
	}
	

}

