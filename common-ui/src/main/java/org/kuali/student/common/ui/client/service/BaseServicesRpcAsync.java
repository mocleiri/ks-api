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
package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public interface BaseServicesRpcAsync {

    public void getObjectTypes(AsyncCallback<List<String>> callback);

    public void getObjectStructure(String objectTypeKey, AsyncCallback<ObjectStructure> callback);

    void validateObject(String objectTypeKey, String stateKey, String info, AsyncCallback<Boolean> callback);
    
    void validateStructureData(String objectTypeKey, String stateKey, String info, AsyncCallback<Boolean> callback);

    public void searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues, AsyncCallback<List<Result>> callback);

    void getSearchCriteriaType(String searchCriteriaTypeKey, AsyncCallback<SearchCriteriaTypeInfo> callback);
    
    void getSearchCriteriaTypes(AsyncCallback<List<SearchCriteriaTypeInfo>> callback);
    
    void getSearchResultType(String searchResultTypeKey, AsyncCallback<SearchResultTypeInfo> callback);
    
    void getSearchResultTypes(AsyncCallback<List<SearchResultTypeInfo>> callback);
    
    void getSearchType(String searchTypeKey, AsyncCallback<SearchTypeInfo> callback);
    
    void getSearchTypes(AsyncCallback<List<SearchTypeInfo>> callback);
    
    void getSearchTypesByCriteria(String searchCriteriaTypeKey, AsyncCallback<List<SearchTypeInfo>> callback);
    
    void getSearchTypesByResult(String searchResultTypeKey, AsyncCallback<List<SearchTypeInfo>> callback);
    
}
