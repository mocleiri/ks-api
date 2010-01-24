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
package org.kuali.student.lum.lu.ui.home.client.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;

import com.google.gwt.dom.client.HRElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateCreditCoursePanel extends ViewComposite{


    private VerticalPanel mainPanel = new VerticalPanel();
    
    private KSButton viewProcess = new KSButton("View Process Overview");
    private KSButton startBlank = new KSButton("Start Blank Proposal");
    private KSButton selectTemplate = new KSButton("Select Proposal Template");
    private KSButton copyProposal = new KSButton("Copy Course Proposal");
    private KSButton copyCourse = new KSButton("Copy Existing Course");
    
    private ButtonEventHandler handler = new ButtonEventHandler();
    
    private class ButtonEventHandler implements ClickHandler{

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if(sender == startBlank){
                CreateCreditCoursePanel.this.getController().fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.CREATE_COURSE));
            } else {
                Window.alert("Function not yet implemented");
            }
        }
        
    }
    
    public static class ButtonRow extends Composite{
        private HorizontalPanel row = new HorizontalPanel();
        private KSLabel descLabel = new KSLabel();
        
        public ButtonRow(Button theButton, String description){
            row.addStyleName("Home-Button-Row");
            row.addStyleName("Content-Left-Margin");
            descLabel.addStyleName("Home-Description-Label");
            theButton.addStyleName("Home-Standard-Button");
            descLabel.setWordWrap(true);
            
            descLabel.setText(description);
            row.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            row.add(theButton); 
            row.add(descLabel);
            this.initWidget(row);
        }
        
        public ButtonRow(Button theButton, String description, Widget moreLink){
            this(theButton, description);
            descLabel.getElement().appendChild(moreLink.getElement());
        }
    }
    
    private class RowBreak extends Composite{
        private HorizontalPanel row = new HorizontalPanel();
        private HTML hr = new HTML("<HR />");
        public RowBreak(){
            row.addStyleName("Home-Horizontal-Break");
            row.add(hr);
            this.initWidget(row);
        }
    }
    
    public CreateCreditCoursePanel(Controller controller) {
        // TODO Bsmith - THIS CONSTRUCTOR NEEDS A JAVADOC
        super(controller, "Create Credit Course");
        Hyperlink more = new Hyperlink("More", "More");
        more.addStyleName("Home-Small-Hyperlink");
        mainPanel.add(new ButtonRow(viewProcess, ""));
        viewProcess.addClickHandler(handler);
        mainPanel.add(new RowBreak());
        mainPanel.add(new ButtonRow(startBlank, "Create a new blank course proposal."));
        startBlank.addClickHandler(handler);
        mainPanel.add(new ButtonRow(selectTemplate, "Create a proposal from a proposal template."));
        selectTemplate.addClickHandler(handler);
        mainPanel.add(new ButtonRow(copyProposal, "Create a proposal by copying an existing course proposal."));
        copyProposal.addClickHandler(handler);
        mainPanel.add(new ButtonRow(copyCourse, "Create a proposal by copying an existing course."));
        copyCourse.addClickHandler(handler);
        Hyperlink helpMeDecide = new Hyperlink("Help Me Decide", "HelpMe");
        helpMeDecide.addStyleName("Home-Small-Hyperlink");
        helpMeDecide.addStyleName("Content-Left-Margin");
        helpMeDecide.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Function not yet implemented.");                
            }
        });
        mainPanel.add(helpMeDecide);
        this.initWidget(mainPanel);
    }
}
