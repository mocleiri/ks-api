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
package org.kuali.student.core.organization.ui.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultRow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
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
 * @deprecated Use the generic KSAdvancedSearchRpc widget instead
 */
@Deprecated
public class OrgSearchWidget extends Composite implements HasSelectionHandlers<OrgInfo>{
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    VerticalPanel root = new VerticalPanel();
       
    KSDropDown orgHierarchyDropDown = null;
    KSTextBox orgName = null;
    KSButton selectButton = new KSButton("Select");
    KSLabel noResults = new KSLabel("No organizations found.");
    
    KSSelectableTableList resultTable = null;
    SimplePanel resultPanel = new SimplePanel();
    OrgInfoList orgInfoList;
    
    String selection, resultSelection;
    
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
        
        final KSButton submit = new KSButton("Search", new ClickHandler(){
            public void onClick(ClickEvent event) {
                if(!orgHierarchyDropDown.getSelectedItems().isEmpty())
                    getSearchResults();
                else
                    Window.alert("No Hierarchy Selected");
            }
            
        });
        tb.setWidget(1,2, submit);

        orgName.addKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                if(event.getCharCode() == KeyCodes.KEY_ENTER){
                	if(!orgHierarchyDropDown.getSelectedItems().isEmpty())
                        getSearchResults();
                    else
                        Window.alert("No Hierarchy Selected");
                }
            }
        
        });
        
        //tb.addStyleName("KS-Org-Search-Widget");
        
        root.add(tb);
        selectButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                List<String> selectedItems = resultTable.getSelectedItems(); 
                if ( selectedItems.size() == 0){
                    Window.alert("No organization selected");
                } else {
                    String orgId ;
                    orgId = resultTable.getSelectedItems().get(0);

                    orgRpcServiceAsync.getOrganization(orgId, new AsyncCallback<OrgInfo>(){
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
        //resultPanel.addStyleName("KS-Org-Search-Widget-Results");
        resultPanel.setVisible(false);
        root.add(resultPanel);
        root.add(selectButton);
    }    
            

    protected void populateOrgHierarchy(){
        orgRpcServiceAsync.getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){

                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(final List<OrgHierarchyInfo> result) {              
                    final Map<String,String> ids = new LinkedHashMap<String,String>();
                    for(OrgHierarchyInfo orgHierarchyInfo:result){
                        ids.put(orgHierarchyInfo.getId(),orgHierarchyInfo.getName());
                    }
                    final ListItems list = new ListItems() {

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
                            return new ArrayList<String>(ids.keySet());
                        }

                        @Override
                        public String getItemText(String id) {
                            return ids.get(id);
                        }

                    };
                    orgHierarchyDropDown.setListItems(list);
                    if(selection != null) {
                        orgHierarchyDropDown.selectItem(selection);
                        if(resultSelection != null) {
                            getSearchResults();
                        }
                        selection = null;
                    }
                }
        });
    }

    private void fireSelectEvent(OrgInfo selectedOrg){               
        SelectionEvent.fire(this, selectedOrg);
    }
    
    protected void getSearchResults(){
        KSImage image = new KSImage("images/loading.gif");
        resultPanel.setWidget(image);
        resultPanel.setVisible(true);
        
        List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
        SearchParam qpv1 = new SearchParam();
        qpv1.setKey("org.queryParam.orgHierarchyId");
        qpv1.setValue(orgHierarchyDropDown.getSelectedItems().get(0));
        queryParamValues.add(qpv1);
        qpv1 = new SearchParam();
        qpv1.setKey("org.queryParam.orgShortName");
        qpv1.setValue(orgName.getText().replace('*', '%'));
        queryParamValues.add(qpv1);
        
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setParams(queryParamValues);
        searchRequest.setSearchKey("org.search.orgQuickViewByHierarchyShortName");
        
        orgRpcServiceAsync.search(searchRequest, new AsyncCallback<SearchResult>(){

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());                        
                    }

                    public void onSuccess(SearchResult result) {
                        if (result == null || result.getRows().size() <=0 ){
                            resultPanel.setWidget(noResults);
                        } else {
                            orgInfoList = new OrgInfoList(result.getRows());
                            resultTable = new KSSelectableTableList();
                            resultTable.setMultipleSelect(false);
                            resultTable.setListItems(orgInfoList);
                            resultPanel.setWidget(resultTable);
                            selectButton.setVisible(true);
                            if(resultSelection != null) {
                                resultTable.selectItem(resultSelection);
                                resultSelection = null;
                            }
                        }
                    }            
            }
        );        
    }
    
    public HandlerRegistration addSelectionHandler(SelectionHandler<OrgInfo> selectionHandler){
        return addHandler(selectionHandler,SelectionEvent.getType());
    }       
        
    public static class OrgInfoList implements ListItems{
        Map<String, SearchResultRow> orgInfoMap = new HashMap<String, SearchResultRow>();
                
        public OrgInfoList(List<SearchResultRow> results){
            for (SearchResultRow r: results){
                orgInfoMap.put(r.getCells().get(0).getValue(), r);
            }
        }
        
        public List<String> getAttrKeys() {
            return Arrays.asList("Organization Name");
        }

        public String getItemAttribute(String id, String attrkey) {
        	SearchResultRow r = orgInfoMap.get(id);
            
            if (attrkey.equals("Organization Name")){
                return r.getCells().get(1).getValue(); 
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
            return orgInfoMap.get(id).getCells().get(1).getValue();
        }
                
    }
    
}
