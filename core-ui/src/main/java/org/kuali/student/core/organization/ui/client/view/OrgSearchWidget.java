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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a search specific to the org service. We will definitely want to 
 * create a more generalized search widget if we ever determine how that will
 * behave 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgSearchWidget extends Composite implements HasSelectionHandlers<OrgInfo>{
    VerticalPanel root = new VerticalPanel();
       
    KSDropDown orgHierarchyDropDown = null;
    KSTextBox orgName = null;
    KSButton selectButton = new KSButton("Select");
    KSLabel noResults = new KSLabel("No organizations found.");
    
    KSSelectableTableList resultTable = null;
    SimplePanel resultPanel = new SimplePanel();
    OrgInfoList orgInfoList;
    
    public OrgSearchWidget(){
        this.initWidget(root);
        
        orgName = new KSTextBox();
        orgHierarchyDropDown = new KSDropDown();
        populateOrgHierarchy();
        
        FlexTable tb = new FlexTable();

        tb.setWidget(0,0, new KSLabel("Hierarchy"));
        tb.setWidget(0,1,orgHierarchyDropDown);        
        
        tb.setWidget(1,0, new KSLabel("Name"));
        tb.setWidget(1,1, orgName);
        
        tb.setWidget(1,2, new KSButton("Search", new ClickHandler(){
            public void onClick(ClickEvent event) {
                getSearchResults();
            }
            
            }            
        ));

        root.add(tb);
        selectButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                List<String> selectedItems = resultTable.getSelectedItems(); 
                if ( selectedItems.size() == 0){
                    Window.alert("No organization selected");
                } else {
                    String orgId ;
                    orgId = resultTable.getSelectedItems().get(0);

                    OrgRpcService.Util.getInstance().getOrganization(orgId, new AsyncCallback<OrgInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(OrgInfo orgInfo) {
                            fireSelectEvent(orgInfo);
                        }            
                    });                
                }
            }
        });
        selectButton.setVisible(false);
        root.add(resultPanel);
        root.add(selectButton);
    }    
            

    protected void populateOrgHierarchy(){
        OrgRpcService.Util.getInstance().getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){

                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(final List<OrgHierarchyInfo> result) {              
                    final ListItems list = new ListItems() {
                        Map<String,String> ids = null;

                        @Override
                        public List<String> getAttrKeys() {
                            return null; //apparently unused
                        }

                        @Override
                        public String getItemAttribute(String id, String attrkey) {
                            return null; //apparently unused
                        }

                        @Override
                        public int getItemCount() {
                            return result.size();
                        }

                        @Override
                        public List<String> getItemIds() {
                            lazyInit();
                            return new ArrayList<String>(ids.keySet());
                        }

                        private void lazyInit() {
                            if(ids == null) {
                                ids = new LinkedHashMap<String,String>();
                                for(OrgHierarchyInfo orgHierarchyInfo:result){
                                    ids.put(orgHierarchyInfo.getId(),orgHierarchyInfo.getName());
                                }
                            }
                        }

                        @Override
                        public String getItemText(String id) {
                            lazyInit();
                            return ids.get(id);
                        }};
                    orgHierarchyDropDown.setListItems(list);
                }
        });
    }

    private void fireSelectEvent(OrgInfo selectedOrg){               
        SelectionEvent.fire(this, selectedOrg);
    }
    
    protected void getSearchResults(){
        List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
        QueryParamValue qpv1 = new QueryParamValue();
        qpv1.setKey("org.queryParam.orgHierarchyId");
        qpv1.setValue(orgHierarchyDropDown.getSelectedItems().get(0));
        queryParamValues.add(qpv1);
        qpv1 = new QueryParamValue();
        qpv1.setKey("org.queryParam.orgShortName");
        qpv1.setValue(orgName.getText().replace('*', '%'));
        queryParamValues.add(qpv1);
        
        OrgRpcService.Util.getInstance().searchForResults("org.search.orgQuickViewByHierarchyShortName", 
                queryParamValues, new AsyncCallback<List<Result>>(){

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());                        
                    }

                    public void onSuccess(List<Result> result) {
                        if (result == null || result.size() <=0 ){
                            resultPanel.setWidget(noResults);
                        } else {
                            orgInfoList = new OrgInfoList(result);
                            resultTable = new KSSelectableTableList();
                            resultTable.setMultipleSelect(false);
                            resultTable.setListItems(orgInfoList);
                            resultPanel.setWidget(resultTable);
                            selectButton.setVisible(true);
                        }
                    }            
            }
        );        
    }
    
    public HandlerRegistration addSelectionHandler(SelectionHandler<OrgInfo> selectionHandler){
        return addHandler(selectionHandler,SelectionEvent.getType());
    }       
        
    public class OrgInfoList implements ListItems{
        Map<String, Result> orgInfoMap = new HashMap<String, Result>();
                
        public OrgInfoList(List<Result> results){
            for (Result r: results){
                orgInfoMap.put(r.getResultCells().get(0).getValue(), r);
            }
        }
        
        public List<String> getAttrKeys() {
            return Arrays.asList("Organization Name");
        }

        public String getItemAttribute(String id, String attrkey) {
            Result r = orgInfoMap.get(id);
            
            if (attrkey.equals("Organization Name")){
                return r.getResultCells().get(1).getValue(); 
            }
            
            return null;
        }

        public int getItemCount() {
            return orgInfoMap.size();
        }

        public List<String> getItemIds() {
            List<String> keys = new ArrayList<String>();

            for (String s:orgInfoMap.keySet()){
                keys.add(s);
            }
            
            return keys;
        }

        public String getItemText(String id) {
            return ((Result)orgInfoMap.get(id)).getResultCells().get(1).getValue();
        }
                
    }
}
