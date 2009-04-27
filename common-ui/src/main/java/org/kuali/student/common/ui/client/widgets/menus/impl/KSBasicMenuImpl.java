package org.kuali.student.common.ui.client.widgets.menus.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenuAbstract;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSBasicMenuImpl extends KSBasicMenuAbstract{
    private VerticalPanel menuPanel = new VerticalPanel();
    private VerticalPanel menuTitlePanel = new VerticalPanel();
    private KSLabel menuTitle = new KSLabel();
    private KSLabel menuDescription = new KSLabel();
    private VerticalPanel menuContainer = new VerticalPanel();
    private List<MenuItemPanel> menuItems = new ArrayList<MenuItemPanel>();
    private boolean numberAllItems = false;
    
    private EventHandler handler = new EventHandler();
    
    public KSBasicMenuImpl(){
        menuTitlePanel.add(menuTitle);
        menuTitlePanel.add(menuDescription);
        menuContainer.add(menuTitlePanel);
        menuContainer.add(menuPanel);
        
        menuContainer.addStyleName(KSStyles.KS_BASIC_MENU_PARENT_CONTAINER);
        menuTitlePanel.addStyleName(KSStyles.KS_BASIC_MENU_TITLE_PANEL);
        menuPanel.addStyleName(KSStyles.KS_BASIC_MENU_PANEL);
        
        menuTitle.addStyleName(KSStyles.KS_BASIC_MENU_TITLE_LABEL);
        menuTitle.addStyleName(KSStyles.KS_INDENT + "-1");
        
        menuDescription.addStyleName(KSStyles.KS_BASIC_MENU_DESC_LABEL);
        menuDescription.addStyleName(KSStyles.KS_INDENT + "-1");
        
        this.initWidget(menuContainer);
    }
    
    public void setTitle(String title){
        menuTitle.setText(title);
        menuTitle.setWordWrap(true);
        //add style
    }
    
    public void setDescription(String description){
        menuDescription.setText(description);
        menuDescription.setWordWrap(true);
        //add style
    }
    
    private class EventHandler implements ClickHandler, MouseOverHandler, MouseOutHandler, FocusHandler, BlurHandler{

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            if(sender instanceof MenuItemPanel){
                selectMenuItemPanel((MenuItemPanel)sender);
            }
        }



        @Override
        public void onMouseOver(MouseOverEvent event) {
            Widget sender = (Widget) event.getSource();   
            if(sender instanceof MenuItemPanel){
                if(((MenuItemPanel) sender).isSelectable()){
                    sender.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL_HOVER);
                    ((MenuItemPanel) sender).getItemLabel().addStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL_HOVER);
                }
            } 
        }

        @Override
        public void onMouseOut(MouseOutEvent event) {
            Widget sender = (Widget) event.getSource();
            if(sender instanceof MenuItemPanel){
                if(((MenuItemPanel) sender).isSelectable()){
                    sender.removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL_HOVER);
                    ((MenuItemPanel) sender).getItemLabel().removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL_HOVER);
                }
            } 
        }

        @Override
        public void onFocus(FocusEvent event) {
            // no function yet
            
        }

        @Override
        public void onBlur(BlurEvent event) {
            // no function yet
            
        }
        
    }
    
    private void selectMenuItemPanel(MenuItemPanel toBeSelected) {
        if(toBeSelected.isSelectable()){
            
            //deselect menu items
            for(MenuItemPanel m : menuItems){
                m.deSelect();
            }
            
            toBeSelected.select();
        }
        
    }
    
    private class MenuItemPanel extends FocusPanel{
        KSLabel itemLabel = new KSLabel();
        boolean selected = false;
        boolean selectable = false;
        KSMenuItemData item;
        int indent;
        int itemNum;
        
        public MenuItemPanel(KSMenuItemData item, int indent, int itemNum){
            this.item = item;
            this.indent = indent;
            this.itemNum = itemNum;
            
            if(numberAllItems){
                // logic for indent maybe
                itemLabel.setText(itemNum + ". " + item.getLabel());
            }
            else{
                if(indent == 1 && !(item.getSubItems().isEmpty())){
                    itemLabel.setText(itemNum + ". " + item.getLabel());
                    itemLabel.addStyleName(KSStyles.KS_BASIC_MENU_TOPLEVEL_ITEM_LABEL);
                    this.addStyleName(KSStyles.KS_BASIC_MENU_TOPLEVEL_ITEM_PANEL);
                }
                else{
                    itemLabel.setText(item.getLabel());
                }
            }
            
            if(indent > 0 && indent <= 7){
                itemLabel.addStyleName(KSStyles.KS_INDENT + "-" + indent);
            }
            
            itemLabel.setWordWrap(true);
            this.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL);
            itemLabel.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL);
            if(item.getClickHandler() != null)
            {
                this.addClickHandler(item.getClickHandler());
                itemLabel.addStyleName(KSStyles.KS_BASIC_MENU_CLICKABLE_ITEM_LABEL);
                selectable = true;
            }
            this.addClickHandler(handler);
            this.addMouseOverHandler(handler);
            this.addMouseOutHandler(handler);
            this.addFocusHandler(handler);
            this.addBlurHandler(handler);
            this.add(itemLabel);
        }
        
        public void deSelect(){
            selected = false;
            this.removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL_SELECTED);
            itemLabel.removeStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL_SELECTED);
        }
        
        public void select(){
            selected = true;
            this.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_PANEL_SELECTED);
            itemLabel.addStyleName(KSStyles.KS_BASIC_MENU_ITEM_LABEL_SELECTED);
        }

        public KSLabel getItemLabel() {
            return itemLabel;
        }

        public boolean isSelectable() {
            return selectable;
        }

        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }
        
        public KSMenuItemData getItem() {
            return item;
        }
    }
    
    @Override
    protected void populateMenu() {
        createMenuItems(items, 1);  
    }
    
    private void createMenuItems(List<KSMenuItemData> theItems, int currentDepth){
        int itemNum = 0;
        for(KSMenuItemData i: theItems){
            itemNum++;
            addMenuItem(new MenuItemPanel(i, currentDepth, itemNum));
            if(!(i.getSubItems().isEmpty())){
                createMenuItems(i.getSubItems(), currentDepth + 1);
            }
        }
    }
    
    private void addMenuItem(MenuItemPanel panel){
        menuPanel.add(panel);
        menuItems.add(panel);
    }
    
    public boolean isNumberAllItems() {
        return numberAllItems;
    }

    public void setNumberAllItems(boolean numberAllItems) {
        this.numberAllItems = numberAllItems;
    }

    @Override
    public boolean selectMenuItem(String[] hierarchy) {
        List<KSMenuItemData> currentItems = items;
        KSMenuItemData itemToSelect = null;
        for(String s: hierarchy){
            s = s.trim();
            for(KSMenuItemData i: currentItems){
                if(s.equalsIgnoreCase(i.getLabel().trim())){
                    itemToSelect = i;
                    currentItems = i.getSubItems();
                    break;
                }
            }
        }
        
        if(itemToSelect != null){
        
            for(MenuItemPanel p: menuItems){
                if(itemToSelect.equals(p.getItem())){
                    p.fireEvent(new ClickEvent(){});
                    return true;
                }
            }
            
        }
        
        return false;
    }
    
}
