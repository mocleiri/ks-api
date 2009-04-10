package org.kuali.student.common.ui.client.widgets.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.ListBox;

public class KSDropDownImpl extends KSSelectItemWidgetAbstract{ 

	private ListBox listBox;
	
	public KSDropDownImpl() {
	    init();
	}
	
    protected void init() {
		listBox = new ListBox(false);
        this.initWidget(listBox);
		setupDefaultStyle();
		
		listBox.addChangeHandler(new ChangeHandler(){
            public void onChange(ChangeEvent event) {
                fireChangeEvent();                
            }	    
		});
	}

	private void setupDefaultStyle() {
	    listBox.addStyleName(KSStyles.KS_DROPDOWN_STYLE);
		
		listBox.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
			    listBox.removeStyleName(KSStyles.KS_DROPDOWN_FOCUS_STYLE);
				
			}	
		});	

		listBox.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
			    listBox.addStyleName(KSStyles.KS_DROPDOWN_FOCUS_STYLE);

			}		
		});
		
		listBox.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
			    listBox.addStyleName(KSStyles.KS_DROPDOWN_HOVER_STYLE);
				
			}		
		});
		
		listBox.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
			    listBox.removeStyleName(KSStyles.KS_DROPDOWN_HOVER_STYLE);
				
			}
			
		});
		
		listBox.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(listBox.getSelectedIndex() != -1){
				    listBox.addStyleName(KSStyles.KS_DROPDOWN_SELECTED_STYLE);
				}
				else{
				    listBox.removeStyleName(KSStyles.KS_DROPDOWN_SELECTED_STYLE);
				}
				
			}
			
		});
		
	}
	
	
	public void selectItem(String id){
		for(int i = 0; i < listBox.getItemCount(); i++){
			if(id.equals(listBox.getValue(i))){
				listBox.setSelectedIndex(i);
			}
		}
	}

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    @Override
    public void deSelectItem(String id) {        
        for(int i = 0; i < listBox.getItemCount(); i++){
            if(id.equals(listBox.getValue(i))){
                listBox.setItemSelected(i, false);
                listBox.setItemSelected(0, true);
            }
        }        
    }

    @Override
    public void setListItems(ListItems listItems) {
        super.setListItems(listItems);
        
        listBox.clear();
        listBox.addItem("Select");
        for (String id:listItems.getItemIds()){
            listBox.addItem(listItems.getItemText(id),id);            
        }        
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
     */
    @Override
    public List<String> getSelectedItems() {
        List<String> result = new ArrayList<String>();
        if (listBox.getSelectedIndex() > 0){
            String id = listBox.getValue(listBox.getSelectedIndex());
            result.add(id);
        }
        return result;
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onLoad()
     */
    @Override
    public void onLoad() {
                
    }

    @Override
    public void setEnabled(boolean b) {
        listBox.setEnabled(b);
    }

    @Override
    public boolean isEnabled() {
        return listBox.isEnabled();
    }

}
