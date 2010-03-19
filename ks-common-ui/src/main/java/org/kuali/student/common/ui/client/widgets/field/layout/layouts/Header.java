package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class Header extends Composite{
	private FlowPanel header = new FlowPanel();
	private FlowPanel actions = new FlowPanel();
	private FlowPanel clearDiv = new FlowPanel();
	private SectionTitle title;
	private AbbrButton help = new AbbrButton(AbbrButtonType.HELP);
	private AbbrButton delete = new AbbrButton(AbbrButtonType.DELETE);
	
	public Header(SectionTitle title){
		this.title = title;
		header.add(title);
		
		actions.add(help);
		actions.add(delete);
		actions.setStyleName("ks-form-header-title-actions");
		
		header.add(actions);
		
		clearDiv.setStyleName("clearboth");
		header.add(clearDiv);
		this.initWidget(header);
	}

	public void addDeleteHandler(ClickHandler handler){
		delete.addClickHandler(handler);
	}
	
	public void addHelpHandler(ClickHandler handler){
		help.addClickHandler(handler);
	}
	
	public void setHeaderTitle(String title){
		this.title.setText(title);
	}
	
	public void setHeaderTitle(SectionTitle title){
		header.remove(this.title);
		this.title = title;
		header.insert(title, 0);
	}
}
