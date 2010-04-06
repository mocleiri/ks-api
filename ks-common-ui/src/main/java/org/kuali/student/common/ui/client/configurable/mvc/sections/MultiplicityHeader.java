package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.ButtonStyle;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class MultiplicityHeader extends Composite{
	
	private FlowPanel header = new FlowPanel();
	private FlowPanel actions = new FlowPanel();
	private FlowPanel clearDiv = new FlowPanel();
	private SectionTitle title;
	private KSLinkButton help;
	private KSLinkButton delete = null;
	
	public MultiplicityHeader(SectionTitle title, boolean readOnly){
		this.title = title;
		header.add(title);
		
		help = new KSLinkButton("?", ButtonStyle.HELP);
		actions.add(help);
		
		if(!readOnly){
			delete = new KSLinkButton("X", ButtonStyle.DELETE);
			actions.add(delete);
		}
		
		actions.setStyleName("ks-form-header-title-actions");
		
		header.add(actions);
		
		clearDiv.setStyleName("clear");
		header.add(clearDiv);
		this.initWidget(header);
	}

	public void addDeleteHandler(ClickHandler handler){
		if(delete!=null){
			delete.addClickHandler(handler);
		}
	}
	
	public void addHelpHandler(ClickHandler handler){
		help.addClickHandler(handler);
	}
	
}
