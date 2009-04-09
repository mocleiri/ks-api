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
package org.kuali.student.core.organization.ui.client.view;

import org.kuali.student.core.organization.dto.OrgInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgUpdatePanel extends Composite{
    VerticalPanel root = new VerticalPanel();
    SimplePanel editPanel = new SimplePanel();
    DeckPanel w = new DeckPanel();
    
    String orgId = null;
    
    boolean loaded = false;
    
    public OrgUpdatePanel(){
        root.setWidth("100%");
        super.initWidget(w);
        w.add(root);
        w.showWidget(0);
    }
    
    public void onLoad(){
        if (!loaded){
            if (orgId == null){
                OrgSearchWidget orgSearchWidget = new OrgSearchWidget(); 
                
                orgSearchWidget.addSelectionHandler(new SelectionHandler<OrgInfo>(){

                    public void onSelection(SelectionEvent<OrgInfo> event) {
                        final OrgCreatePanel orgCreatePanel = new OrgCreatePanel(OrgCreatePanel.CREATE_ORG_ALL, new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                w.remove(w.getWidgetCount() - 1);
                                w.showWidget(w.getWidgetCount() - 1);
                            }
                        });
                        w.add(orgCreatePanel);
                        w.showWidget(w.getWidgetCount() - 1);
                    }
                    
                });
                root.add(orgSearchWidget);
            } else {
                editPanel.setWidget(new OrgCreatePanel(OrgCreatePanel.CREATE_ORG_ALL));
            }
            loaded = true;
            root.add(editPanel); 
        }
    }
    
    
}
