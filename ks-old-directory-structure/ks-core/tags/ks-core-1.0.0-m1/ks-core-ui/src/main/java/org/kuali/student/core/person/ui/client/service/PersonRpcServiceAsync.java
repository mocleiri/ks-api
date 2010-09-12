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
package org.kuali.student.core.person.ui.client.service;

import java.util.List;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface PersonRpcServiceAsync {
    public void searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues, AsyncCallback<List<Result>> callback);
    public void fetchPerson(String personId, AsyncCallback<PersonInfo> callback);
}
