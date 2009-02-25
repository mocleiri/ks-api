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




import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgMain extends Composite {
    VerticalPanel vPanel = new VerticalPanel();
    SimplePanel workPanel = new SimplePanel();

    boolean loaded = false;
    
    public OrgMain() {
        super.initWidget(vPanel);
    }
    
    public void onLoad(){
        vPanel.setStyleName("ks-main");
        
        Label pageTitle = new Label("Organization Management");
        pageTitle.setStyleName("page-title");
        vPanel.add(pageTitle);
        vPanel.add(new OrgMenu(workPanel));
        vPanel.add(workPanel);        
    }
}
