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
package org.kuali.student.core.search.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Loads all search info for a service into memory
 *
 */
public class SearchManagerImpl implements SearchManager{

	private String searchContextFile;
	private Map<String, SearchTypeInfo> searchInfoTypeMap;
	private Map<String, SearchCriteriaTypeInfo> searchCriteriaTypeMap;
	private Map<String, SearchResultTypeInfo> searchResultTypeInfoMap;
	private Map<String, String> queryMap;

	@SuppressWarnings("unchecked")
	private void init() {
		ApplicationContext ac = new FileSystemXmlApplicationContext(searchContextFile);
		searchInfoTypeMap = ac.getBeansOfType(SearchTypeInfo.class);
		searchCriteriaTypeMap = ac.getBeansOfType(SearchCriteriaTypeInfo.class);
		searchResultTypeInfoMap = ac.getBeansOfType(SearchResultTypeInfo.class);
		queryMap = (Map<String, String>) ac.getBean("queryMap");
	}

	public SearchManagerImpl(String searchContextFile) {
		super();
		this.searchContextFile = searchContextFile;
		init();
	}

	public List<Result> searchForResults(String searchTypeKey,
			List<QueryParamValue> queryParamValues, SearchableDao dao)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		String queryString = queryMap.get(searchTypeKey);
		SearchTypeInfo searchTypeInfo = searchInfoTypeMap.get(searchTypeKey);
		if (searchTypeInfo == null) {
			throw new InvalidParameterException("No such searchTypeKey found: " + searchTypeKey);
		}
		return dao.searchForResults(queryString, searchTypeInfo, queryParamValues);
	}

	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return searchCriteriaTypeMap.get(searchCriteriaTypeKey);
	}

	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		return new ArrayList<SearchCriteriaTypeInfo>(searchCriteriaTypeMap
				.values());
	}

	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return searchResultTypeInfoMap.get(searchResultTypeKey);
	}

	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		return new ArrayList<SearchResultTypeInfo>(searchResultTypeInfoMap
				.values());
	}

	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return searchInfoTypeMap.get(searchTypeKey);
	}

	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		return new ArrayList<SearchTypeInfo>(searchInfoTypeMap.values());
	}

	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<SearchTypeInfo> searchTypesByCriteria = new ArrayList<SearchTypeInfo>();
		for (SearchTypeInfo searchTypeInfo : searchInfoTypeMap.values()) {
			if (searchCriteriaTypeKey.equals(searchTypeInfo
					.getSearchCriteriaTypeInfo().getKey())) {
				searchTypesByCriteria.add(searchTypeInfo);
			}
		}
		return searchTypesByCriteria;
	}

	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<SearchTypeInfo> searchTypesByResult = new ArrayList<SearchTypeInfo>();
		for (SearchTypeInfo searchTypeInfo : searchInfoTypeMap.values()) {
			if (searchResultTypeKey.equals(searchTypeInfo
					.getSearchResultTypeInfo().getKey())) {
				searchTypesByResult.add(searchTypeInfo);
			}
		}
		return searchTypesByResult;
	}

	public String getSearchContextFile() {
		return searchContextFile;
	}

	public void setSearchContext(String searchContextFile) {
		this.searchContextFile = searchContextFile;
	}
}
