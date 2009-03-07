package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanel;
import org.kuali.student.common.ui.client.widgets.KSConfirmationDialogAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSConfirmationDialogImpl extends KSConfirmationDialogAbstract{ 
	
	private boolean canceled = false;
	private String messageTitle;
	private final KSLabel titleLabel = new KSLabel();
	private final SimplePanel widgetWrapper = new SimplePanel();
	private final VerticalPanel content = new VerticalPanel();
	private final HorizontalPanel titlePanel = new HorizontalPanel();
	private final HorizontalPanel messagePanel = new HorizontalPanel();
	private final KSConfirmButtonPanel buttonPanel = new KSConfirmButtonPanel();
	//private final KSImages images = (KSImages)GWT.create(KSImages.class);
	private final Image confirmImage = new Image("images/confirm.png");//images.defaultIcon().createImage();
	
	
	public KSConfirmationDialogImpl(){
		super();
		setupDefaultStyle();
		titlePanel.add(confirmImage);
		titlePanel.add(titleLabel);
		content.add(titlePanel);
		messagePanel.add(widgetWrapper);
		content.add(messagePanel);
		buttonPanel.addCancelHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				KSConfirmationDialogImpl.this.hide();
				canceled = true;
			}
		});
		content.add(buttonPanel);
		super.setWidget(content);
	}
	
	@Override
    public void show(){
		super.show();
		canceled = false;
	}
	
	@Override
    public void center(){
		super.show();
		canceled = false;
	}
	
	@Override
    public void setWidget(Widget w){
		widgetWrapper.setWidget(w);
	}
	
	@Override
    public boolean isCanceled() {
		return canceled;
	}
	
	@Override
    public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	@Override
    public String getMessageTitle() {
		return messageTitle;
	}

	@Override
    public void setMessageTitle(String messageTitle) {
		titleLabel.setText(messageTitle);
		this.messageTitle = messageTitle;
	}	
	
	private void setupDefaultStyle(){
		widgetWrapper.addStyleName(KSStyles.KS_CONFIRM_DIALOG_CONTENT);
		titleLabel.addStyleName(KSStyles.KS_CONFIRM_DIALOG_TITLE);
		titlePanel.addStyleName(KSStyles.KS_CONFIRM_DIALOG_TITLE_PANEL);
		this.addStyleName(KSStyles.KS_CONFIRM_DIALOG);
		confirmImage.addStyleName(KSStyles.KS_CONFIRM_DIALOG_IMAGE);
	}
	
}
