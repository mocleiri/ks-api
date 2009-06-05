package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTabPanel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.SearchResultListItems;
import org.kuali.student.core.dictionary.dto.Enum;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * This a search widget that builds a query form using parameters defined in a 
 * search type defined in a search service and displays results of said query
 * by calling the search service methods. Adding a select click handler to this
 * widget will result in a select button being displayed in the result tab.
 * 
 * @author Kuali Student Team
 *
 */
public class KSAdvancedSearchRpc extends Composite implements HasSelectionHandlers<List<String>>{
    private KSTabPanel tabPanel = new KSTabPanel();
    private VerticalPanel searchLayout = new VerticalPanel();
    private VerticalPanel resultLayout = new VerticalPanel();
    private KSSelectableTableList searchResults = new KSSelectableTableList();
    private KSLabel resultLabel = new KSLabel("No Search Results");
    private KSButton selectButton = new KSButton("Select");
    private boolean hasSelectionHandlers = false;
    private SearchResultListItems searchResultList = new SearchResultListItems();
    
    private FlexTable searchParamTable = new FlexTable();
    
    private BaseRpcServiceAsync searchService;
    private String searchTypeKey;
    
    /** 
     * This constructs a search widget.
     * 
     * @param searchService - instance of rpc service implementing the base search/dictionary service methods
     * @param searchTypeKey - the predefined search to use. The search results must return an id as first column.
     */
    public KSAdvancedSearchRpc(BaseRpcServiceAsync searchService, String searchTypeKey){
        this.searchService = searchService;
        this.searchTypeKey = searchTypeKey;
        
        generateSearchLayout();
                        
        tabPanel.addTab(searchLayout, "Search");
        tabPanel.addTab(resultLayout, "Results");
        tabPanel.selectTab(0);
        
        searchResults.setPageSize(10);
        searchResults.setListItems(searchResultList);
        
        resultLayout.addStyleName(KSStyles.KS_ADVANCED_SEARCH_RESULTS_PANEL);
        searchLayout.addStyleName(KSStyles.KS_ADVANCED_SEARCH_PANEL);
        searchLayout.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        tabPanel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_TAB_PANEL);
        
        selectButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                List<String> selectedItems = searchResults.getSelectedItems();
                if (selectedItems != null && selectedItems.size() > 0){
                    fireSelectEvent(selectedItems);
                }                
            }            
        });
        initWidget(tabPanel);
    }
    
    
    private void generateSearchLayout() {
        searchService.getSearchType(searchTypeKey, new AsyncCallback<SearchTypeInfo>(){

            public void onFailure(Throwable caught) {
                //TODO: How to handle this?
            }

            public void onSuccess(SearchTypeInfo searchTypeInfo) {
                int row = 0;
                int column = 0;

                SearchCriteriaTypeInfo criteriaInfo = searchTypeInfo.getSearchCriteriaTypeInfo();
                List<QueryParamInfo> queryParams = criteriaInfo.getQueryParams();
                for (QueryParamInfo q:queryParams){
                    FieldDescriptor fd = q.getFieldDescriptor();
                    
                    KSLabel paramLabel = new KSLabel(fd.getName());
                    paramLabel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_PARAM_LABELS);
                    searchParamTable.getColumnFormatter().addStyleName(0, KSStyles.KS_ADVANCED_SEARCH_PARAM_LABEL_COLUMN);
                    searchParamTable.getCellFormatter().addStyleName(row, column, KSStyles.KS_ADVANCED_SEARCH_PARAM_LABEL_CELLS);
                    searchParamTable.setWidget(row, column, paramLabel);
                    column++;
                    
                    if (fd.getSearchTypeId() != null || fd.getEnum() != null){                                                
                        KSSelectItemWidgetAbstract dropDown = new KSDropDown();
                        
                        if (fd.getSearchTypeId() != null){
                            populateSearchEnumeration(dropDown, fd.getSearchTypeId());
                        } else {
                            populateSearchEnumeration(dropDown, fd.getEnum());
                        }
                        
                        dropDown.setName(q.getKey());
                        searchParamTable.setWidget(row, column, dropDown);
                    } else{
                        KSTextBox tb = new KSTextBox();
                        tb.setName(q.getKey());
                        searchParamTable.setWidget(row, column, tb);
                     }
                     column = 0;
                     row++;
                }
                column++;
                KSButton searchButton = new KSButton("Search");
                searchButton.addClickHandler(new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        executeSearch();
                    }            
                });
                searchParamTable.setWidget(row, column, searchButton);
                searchLayout.add(searchParamTable);     
                
                //Initialze Results Layout
                SearchResultTypeInfo resultTypeInfo = searchTypeInfo.getSearchResultTypeInfo();
                searchResultList.setResultColumns(resultTypeInfo.getResultColumns());

                FlexTable resultTable = new FlexTable();
                resultLabel.addStyleName(KSStyles.KS_ADVANCED_SEARCH_RESULTS_LABEL);
                resultTable.setWidget(0, 0, resultLabel);
                resultTable.setWidget(1, 0, searchResults);
                resultLayout.add(resultTable);

                //Add select button, this will only be visible if a select handler is directly added
                //to this widget.
                resultLayout.add(selectButton);
                selectButton.setVisible(hasSelectionHandlers);
            }            
        });       
    }
    
    /** 
     * Use this to allow multiple select of items from search results
     *
     */
    public void setMultipleSelect(boolean enable){
       searchResults.setMultipleSelect(enable); 
    }
    
    /**
     * Id of selected item.  If multiple items are selected, this will return the
     * first selected item.
     * 
     * @return
     */
    public String getSelectedId(){
        return searchResults.getSelectedItem();
    }
    
    /** 
     * Use to get a list of items selected from the results of search displayed to the user.
     * 
     * @return
     */
    public List<String> getSelectedIds(){
        return searchResults.getSelectedItems();
    }
           
    private void executeSearch(){
        //Build query paramaters
        List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
        for (int row=0; row < searchParamTable.getRowCount()-1; row++ ){
            Widget w = searchParamTable.getWidget(row, 1);
            
            QueryParamValue queryParamValue = new QueryParamValue();
            queryParamValue.setKey(((HasName)w).getName());
            if (w instanceof KSSelectItemWidgetAbstract){
                queryParamValue.setValue(((KSSelectItemWidgetAbstract)w).getSelectedItem());
            } else {
                String value = ((HasText)w).getText();
                value = value.replace('*','%');
                queryParamValue.setValue(value);
            }
            queryParamValues.add(queryParamValue);                
        }
       
        //Call the search service
        searchService.searchForResults(searchTypeKey, queryParamValues, new AsyncCallback<List<Result>>(){

            @Override
            public void onFailure(Throwable caught) {
                //TODO: Handle this failure
            }

            @Override
            public void onSuccess(List<Result> result) {
                if (result != null){
                    resultLabel.setVisible(false);
                    searchResults.setVisible(true);
                    searchResultList.setResults(result);
                    searchResults.redraw();
                    selectButton.setVisible(hasSelectionHandlers);
                } else{
                    resultLabel.setVisible(true);
                    searchResults.setVisible(false);
                    selectButton.setVisible(false);
                }
                tabPanel.selectTab(1);
            }               
        });
    }

    private void populateSearchEnumeration(final KSSelectItemWidgetAbstract selectField, String searchType){               
        searchService.searchForResults(searchType, null, new AsyncCallback<List<Result>>(){

            @Override
            public void onFailure(Throwable caught) {
                // TODO: Handle this exception                
            }

            @Override
            public void onSuccess(List<Result> results) {
                selectField.setListItems(new SearchResultListItems(results));
                selectField.redraw();
            }
            
        });
    } 
    
    private void populateSearchEnumeration(final KSSelectItemWidgetAbstract selectField, Enum enumInfo){
        //TODO: Call the enumeration managment service to get enum
    }


    /**
     * Use to add a selection handler to this widget. Adding a selection handler will display a select button
     * in results tab.
     * 
     * @see com.google.gwt.event.logical.shared.HasSelectionHandlers#addSelectionHandler(com.google.gwt.event.logical.shared.SelectionHandler)
     */
    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<String>> handler) {
        this.hasSelectionHandlers = true;
        return addHandler(handler,SelectionEvent.getType());
    }
    
    private void fireSelectEvent(List<String> selectedItems){
        SelectionEvent.fire(this, selectedItems);
    }
}
