package org.kuali.student.common.ui.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * An accordion panel is used for displaying categories of content ONE at a time.  When one category's titlebar is
 * selected, a panel with its content is shown, and if there exists any currently open category, it is closed.  
 * You may also close an open category by clicking on it's titlebar again.  This widget's behavior closely mimics
 * the gwt implementation of StackPanel, but it is more customizable.
 * 
 * @author Kuali Student Team
 *
 */
public class KSAccordionPanel extends KSAccordionPanelAbstract{ 
    
    private KSAccordionPanelAbstract accordionPanel = GWT.create(KSAccordionPanelImpl.class);

    /**
     * This constructs an Accordion panel which can be used to display different content categories
     * ONE at a time.
     * 
     */
    public KSAccordionPanel() {
        initWidget(accordionPanel);
    }
    
    /**
     * Adds a handler that will be used for ANY accordion panel title clicks.
     * 
     * @param handler a ClickHandler that will handle clicks on any/all of the titlebars
     * 
     */
    public void addGlobalTitleBarHandler(ClickHandler handler){
        accordionPanel.addGlobalTitleBarHandler(handler);
    }

    /**
     * Adds a panel with the category name of title and 
     * panel content defined by the widget subContent.
     * 
     * @param title the title of the category titlebar
     * @param subContent the category's content - what will be shown below the titlebar when it is clicked
     */
    public void addPanel(String title, Widget subContent){
        accordionPanel.addPanel(title, subContent);
    }
    

    /**
     * Adds a panel with the titleWidget used as the title bar content and 
     * panel content defined by the widget subContent.
     * 
     * @param titleWidget the widget used in the titlebar
     * @param subContent the category's content - what will be shown below the titlebar when it is clicked
     */
    public void addPanel(Widget titleWidget, Widget subContent){
        accordionPanel.addPanel(titleWidget);
    }

	/**
     * Adds a panel with titleWidget used as the title bar content and 
     * panel content defined by the widget subContent.  In addition, this panel's title bar will
     * handle clicks using the handler passed in.
     * 
     * @param titleWidget the widget used in the titlebar
     * @param clickHandler the handler to handle clicks on the titlebar itself
     * @param subContent the category's content - what will be shown below the titlebar when it is clicked
     * 
     */
	public void addPanel(Widget titleWidget, ClickHandler clickHandler, Widget subContent) {
    accordionPanel.addPanel(titleWidget, clickHandler, subContent);
		
	}
	
	/**
	 * Adds ONLY a titlebar (no panel content) which contains the titleWidget and handles clicks using the handler
	 * passed in.
	 * 
     * @param titleWidget the widget used in the titlebar
     * @param clickHandler the handler to handle clicks on the titlebar itself
     * 
	 */
	public void addPanel(Widget titleWidget, ClickHandler clickHandler) {
	    accordionPanel.addPanel(titleWidget, clickHandler);
	}
   
    /**
     * Adds ONLY a titlebar (no panel content) which contains the titleWidget.
     * 
     * @param titleWidget the widget used in the titlebar
     */
    public void addPanel(Widget titleWidget){
        accordionPanel.addPanel(titleWidget);
    }
     
    /**
     * Resets all titlebars to their "closed" state.  All open panels will be closed.
     * 
     */
    public void resetTitleBars(){
        accordionPanel.resetTitleBars();
    }
    
    /**
     * Gets a list of all content widgets in the accordion panel.  This list can contain null
     * values.
     * 
     * @return a List of Widgets used in this accordion panel as category content
     * 
     */
    public List<Widget> getWidgetList(){
        return accordionPanel.getWidgetList();
    }

    /**
     * Gets this AccordionPanel's instance.
     * 
     * @return this KSAccordionPanelAbstract instance
     */
    public KSAccordionPanelAbstract getAccordionPanel() {
        return accordionPanel;
    }
        


}
