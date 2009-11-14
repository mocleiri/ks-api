/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.selectors;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

//TODO comments
public class KSSearchComponent extends Composite implements SuggestPicker {
    private VerticalPanel layout = new VerticalPanel();
    final Hyperlink searchLink = new Hyperlink("Search", "Search");
    final Hyperlink browseLink = new Hyperlink("Browse", "Browse");
    final HorizontalPanel searchOrBrowserLink = new HorizontalPanel();
    
    //data
    final KSSuggestBox suggestBox;
    final KSAdvancedSearchWindowComponent advSearchWindow; 
	
	private final FocusGroup focus = new FocusGroup(this);
    
    public KSSearchComponent(SearchComponentConfiguration searchConfig, SearchSuggestOracle orgSearchOracle) {    	    	
    	
    	suggestBox = new KSSuggestBox(orgSearchOracle); 
    	suggestBox.setAutoSelectEnabled(false);      	          	
        layout.add(suggestBox);
		orgSearchOracle.setTextWidget(suggestBox.getTextBox());
                
		// FIXME when search window is displayed, call focus.setSuppressed(true), and set it to false afterwards
		focus.addWidget(suggestBox);
		
        searchOrBrowserLink.add(searchLink);
     //   searchOrBrowserLink.add(new KSLabel("  or  "));
     //   searchOrBrowserLink.add(browseLink);
        layout.add(searchOrBrowserLink);
        
        //define advanced search link and window
    	advSearchWindow = new KSAdvancedSearchWindowComponent(searchConfig);  
    	advSearchWindow.addSelectionHandler(selectionHandler);
        searchLink.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
               if(advSearchWindow != null){
                   advSearchWindow.show();
               }
            }
        });  
        this.initWidget(layout);
    }
    
    
    SelectionHandler<List<String>> selectionHandler = new SelectionHandler<List<String>>() {
        @Override
        public void onSelection(SelectionEvent<List<String>> event) {

            final List<String> selected = event.getSelectedItem();
            if (selected.size() > 0){
                suggestBox.setText(event.getSelectedItem().get(0));                    
            }                  
        }
    };
    
    public String getSelectedValue() {
    	return suggestBox.getText();
    }

	@Override
	public String getValue() {
		return suggestBox.getSelectedId();
	}

	@Override
	public void setValue(String value) {
		setValue(value, true);
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		suggestBox.reset();
	    suggestBox.setValue(value, fireEvents);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return suggestBox.addValueChangeHandler(handler);
	}


	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return focus.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return focus.addBlurHandler(handler);
	}
}
