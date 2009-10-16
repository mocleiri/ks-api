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

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.core.proposal.ui.client.service.ProposalRpcService;
import org.kuali.student.core.proposal.ui.client.service.ProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;
import org.kuali.student.lum.lu.ui.home.client.view.CreateCreditCoursePanel.ButtonRow;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FindPanel extends ViewComposite{
    public static final String SEARCH_TYPE_PROPOSALS = "Proposals";
    public static final String SEARCH_TYPE_COURSES = "Courses";
    
    LuRpcServiceAsync luServiceAsync = GWT.create(LuRpcService.class);
    ProposalRpcServiceAsync proposalServiceAsync = GWT.create(ProposalRpcService.class);
    
    KSAdvancedSearchWindow courseSearchWindow = new KSAdvancedSearchWindow(luServiceAsync, "lu.search.clus","lu.resultColumn.cluId", "Find Course");
    KSAdvancedSearchWindow proposalSearchWindow = new KSAdvancedSearchWindow(proposalServiceAsync, "proposal.search.courses", "proposal.resultColumn.proposalId", "Find Proposal");
    
    private VerticalPanel mainPanel = new VerticalPanel();
    
    private FlexTable findLayout = new FlexTable();
    private KSLabel findLabel = new KSLabel("Find");
    private KSDropDown searchFor = new KSDropDown();
    private KSButton findButton = new KSButton("Find");
    
       
    public FindPanel(Controller controller) {
        // TODO Bsmith - THIS CONSTRUCTOR NEEDS A JAVADOC
        super(controller, "Find Course or Proposal");

        courseSearchWindow.addSelectionHandler(new SelectionHandler<List<String>>(){
            //FIXME: This should take user to the course view screens
            public void onSelection(SelectionEvent<List<String>> event) {
                final List<String> selected = event.getSelectedItem();
                if (selected.size() > 0){
                    FindPanel.this.getController().fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.VIEW_COURSE, event));
                    courseSearchWindow.hide();
                }                
            }            
        });
        
        proposalSearchWindow.addSelectionHandler(new SelectionHandler<List<String>>(){
            //FIXME: This should take user to the course view screens
            public void onSelection(SelectionEvent<List<String>> event) {
                final List<String> selected = event.getSelectedItem();
                if (selected.size() > 0){
                    FindPanel.this.getController().fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.EDIT_COURSE_PROPOSAL, event));
                    proposalSearchWindow.hide();
                }                
            }            
        });
        
        /* Replaced drop down list with buttons
        SimpleListItems searchTypes = new SimpleListItems();
        searchTypes.addItem(SEARCH_TYPE_PROPOSALS, SEARCH_TYPE_PROPOSALS);
        searchTypes.addItem(SEARCH_TYPE_COURSES, SEARCH_TYPE_COURSES);
        
        searchFor.setListItems(searchTypes);
        searchFor.addSelectionChangeHandler(new SelectionChangeHandler(){
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                if (w.getSelectedItem().equals(SEARCH_TYPE_COURSES)){
                    courseSearchWindow.show();
                } else {
                    proposalSearchWindow.show();
                }
            }            
        });                  
        
        
        findLayout.setCellSpacing(25);        
        findLabel.addStyleName("Home-Category-Label");
        findButton.addStyleName("Home-Blue-Button");
        findLayout.setWidget(0, 0, findLabel);
        findLayout.setWidget(0, 1, searchFor);
                
        findLayout.setStyleName("Content-Left-Margin");
        
        mainPanel.add(findLayout);
         */
     
        //FIXME: This is a quick fix
        KSButton findCourseButton = new KSButton("Find Course", new ClickHandler(){
            public void onClick(ClickEvent event) {
                courseSearchWindow.show();
            }            
        });
        
        KSButton findProposalButton = new KSButton("Find Proposal", new ClickHandler(){
            public void onClick(ClickEvent event) {
                proposalSearchWindow.show();
            }            
        });
        
        mainPanel.add(new ButtonRow(findCourseButton, "Find an existing course."));
        mainPanel.add(new ButtonRow(findProposalButton, "Find an existing proposal."));
        
        this.initWidget(mainPanel);
    }
    
}
