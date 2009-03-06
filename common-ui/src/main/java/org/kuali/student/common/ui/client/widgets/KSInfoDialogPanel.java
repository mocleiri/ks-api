package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSInfoDialogPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Widget;


public class KSInfoDialogPanel extends KSDialogPanelAbstract{ 
    private final KSDialogPanelAbstract dialogPanel = GWT.create(KSInfoDialogPanelImpl.class);
		
	public KSInfoDialogPanel(){

	}


    public void addCloseHandler(CloseHandler handler) {
        dialogPanel.addCloseHandler(handler);
    }


    public void addStyleName(String style) {
        dialogPanel.addStyleName(style);
    }


    public void center() {
        dialogPanel.center();
    }


    public boolean equals(Object obj) {
        return dialogPanel.equals(obj);
    }


    public int getX() {
        return dialogPanel.getX();
    }


    public int getY() {
        return dialogPanel.getY();
    }


    public int hashCode() {
        return dialogPanel.hashCode();
    }


    public void hide() {
        dialogPanel.hide();
    }


    public boolean isShowing() {
        return dialogPanel.isShowing();
    }


    public boolean removeHeader() {
        return dialogPanel.removeHeader();
    }


    public void setAutoHide(boolean autoHide) {
        dialogPanel.setAutoHide(autoHide);
    }


    public void setHeader(String headerText) {
        dialogPanel.setHeader(headerText);
    }


    public void setLocation(int x, int y) {
        dialogPanel.setLocation(x, y);
    }


    public void setModal(boolean modal) {
        dialogPanel.setModal(modal);
    }


    public void setPixelSize(int w, int h) {
        dialogPanel.setPixelSize(w, h);
    }

 
    public void setResizable(boolean resizable) {
        dialogPanel.setResizable(resizable);
    }


    public void setWidget(Widget w) {
        dialogPanel.setWidget(w);
    }


    public void show() {
        dialogPanel.show();
    }


    public String toString() {
        return dialogPanel.toString();
    }


}
