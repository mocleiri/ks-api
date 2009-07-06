package org.kuali.student.lum.lu.ui.course.client.widgets;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Frame;

public class KRUserSearchIFrame extends Frame implements HasSelectionHandlers<String>{
	
	public KRUserSearchIFrame(String string) {
		super();
		this.setResolveFunction(this);
		setUrl(string);
	}
	
	public native void setResolveFunction(KRUserSearchIFrame x)/*-{
		$wnd.sendParams = function (location) {
			x.@org.kuali.student.lum.lu.ui.course.client.widgets.KRUserSearchIFrame::sendParams(Ljava/lang/String;)(location);
		};
	}-*/;

	public void sendParams(String location){
		String userId =
				location.substring(location.indexOf("&principalId=")+"&principalId=".length(),location.indexOf("&", location.indexOf("&principalId=")+"&principalId=".length()));
        SelectionEvent.fire(this, userId);
	}

	@Override
	public HandlerRegistration addSelectionHandler(
			SelectionHandler<String> handler) {
        return addHandler(handler, SelectionEvent.getType());
	}
}