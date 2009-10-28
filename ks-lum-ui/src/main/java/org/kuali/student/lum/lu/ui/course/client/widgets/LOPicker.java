/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;
import org.kuali.student.lum.lu.ui.home.client.view.FindPanel;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LOPicker extends Composite { //implements SuggestPicker {

    private LoRpcServiceAsync loRpcServiceAsync = GWT.create(LoRpcService.class);
    
    final KSAdvancedSearchWindow loSearchWindow = new KSAdvancedSearchWindow(loRpcServiceAsync, "lo.search.loByDesc","lo.resultColumn.loDescId", "Find Learning Objectives");   
    
    VerticalPanel root = new VerticalPanel();
    
    KSTextArea loText = new KSTextArea();
    
//    private final FocusGroup focus = new FocusGroup(this);
    
    public LOPicker() {
        super();

        HorizontalPanel searchPanel = new HorizontalPanel();
        searchPanel.addStyleName("KS-LO-Picker-Link-Panel");
        
        KSLabel searchLink = new KSLabel("Search for LO");
        searchLink.addStyleName("KS-LO-Picker-Link");
        searchLink.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                    loSearchWindow.show();
                
            }
            
        });
               
        loSearchWindow.addSelectionHandler(new SelectionHandler<List<String>>(){
            //FIXME: This should take user to the course view screens
            public void onSelection(SelectionEvent<List<String>> event) {
                final List<String> selected = event.getSelectedItem();
                if (selected.size() > 0){
                    loText.setValue(selected.get(0));                    
                    loSearchWindow.hide();
                }                
            }            
        });
        searchPanel.add(loText);
        searchPanel.add(searchLink);
               
        initWidget(root);

        root.add(searchPanel);

    }


}
