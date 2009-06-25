package org.kuali.student.common.ui.client.widgets.menus;

import java.util.List;


import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSAccordionMenuImpl;

import com.google.gwt.core.client.GWT;


/**
 * The KSAccordionMenu is a class that uses the KSAccordionPanel to create a menu of items.  This menu can have
 * multiple categories and sub categories.  In addition, each category title can have a click handler attached to 
 * it to peform an action or display some content, etc. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSAccordionMenu extends KSAccordionMenuAbstract{ 
	//TODO make this work with KSAccordionPanel
	private KSAccordionMenuAbstract accordionMenu = GWT.create(KSAccordionMenuImpl.class);
	
	/**
	 * Creates a new empty KSAccordionMenu.
	 * 
	 */
	public KSAccordionMenu(){
	    initWidget(accordionMenu);
	}
	
	/**
	 * Populates the menu with the items set through setItems.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.menus.KSMenu#populateMenu()
	 */
	@Override
	protected void populateMenu() {
	    accordionMenu.populateMenu();
	}
	
	/**
	 * Gets the accorion panel implementation instance.
	 * 
	 * @return the KSAccordionPanelImpl instance used, which is this KSAccordionMenu's menu
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenuAbstract#getMenu()
	 */
	public KSAccordionPanelImpl getMenu(){
		return accordionMenu.getMenu();
	}
	
	/**
	 * Retain the history of all sub menus when a top level menu is closed.  This must be called
	 * BEFORE the super class method setItems.
	 * @param retain true if you are retaining history, false if you are clearing it.
	 * @precondition This must be called BEFORE the super class method - setItems.
	 */
	public void setRetainHistory(boolean retain){
	    accordionMenu.setRetainHistory(retain);
	}

	/**
	 * Returns true if this menu is retaining history, otherwise returns false.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenuAbstract#isRetainingHistory()
	 */
	public boolean isRetainingHistory() {
		return accordionMenu.isRetainingHistory();
	}

    /**
     * Get the list of KSMenuItemData used in this accordion menu.
     * 
     * @return the List of KSMenuItemData used in this accordion menu.
     * 
     * @see org.kuali.student.common.ui.client.widgets.menus.KSMenu#getItems()
     */
    @Override
    public List<KSMenuItemData> getItems() {
        return accordionMenu.getItems();
    }

    /**
     * Sets the KSMenuItemData used in this accordion menu.
     * 
     * @param items the list of KSMenuItemData used in this accordion menu
     *
     */
    @Override
    public void setItems(List<KSMenuItemData> items) {
        accordionMenu.setItems(items);
    }

    @Override
    public boolean selectMenuItem(String[] hierarchy) {
        return accordionMenu.selectMenuItem(hierarchy);
    }
	
}
