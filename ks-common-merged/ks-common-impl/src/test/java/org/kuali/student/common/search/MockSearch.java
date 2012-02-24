package org.kuali.student.common.search;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.oldsearch.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.oldsearch.dto.SearchRequest;
import org.kuali.student.common.oldsearch.dto.SearchResult;
import org.kuali.student.common.oldsearch.dto.SearchResultCell;
import org.kuali.student.common.oldsearch.dto.SearchResultRow;
import org.kuali.student.common.oldsearch.dto.SearchResultTypeInfo;
import org.kuali.student.common.oldsearch.dto.SearchTypeInfo;
import org.kuali.student.common.oldsearch.service.SearchService;
import org.kuali.student.common.search.service.impl.CrossSearchManager;
import org.kuali.student.common.search.service.impl.SearchDispatcherImpl;
import org.kuali.student.common.search.service.impl.SearchManagerImpl;
//import org.kuali.student.common.test.util.ContextInfoTestUtility;

public class MockSearch implements SearchService {
	private SearchManagerImpl sm;
	private ContextInfo testContext;
	
	
	public MockSearch(){
		sm = new SearchManagerImpl("classpath:test-cross-search.xml");
		CrossSearchManager csm = new CrossSearchManager();
		sm.setCrossSearchManager(csm);
		SearchDispatcherImpl sd = new SearchDispatcherImpl();
		List<SearchService> services = new ArrayList<SearchService>();
		services.add(this);
		sd.setServices(services);
		csm.setSearchDispatcher(sd);
		//testContext = ContextInfoTestUtility.getEnglishContextInfo();
	}
	
	@Override
	public List<SearchTypeInfo> getSearchTypes(ContextInfo context)
			throws OperationFailedException {
		return sm.getSearchTypes(testContext);
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return sm.getSearchType(searchTypeKey, testContext);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return sm.getSearchTypesByResult(searchResultTypeKey, testContext);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return sm.getSearchTypesByCriteria(searchCriteriaTypeKey, testContext);
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes(ContextInfo context)
			throws OperationFailedException {
		return sm.getSearchResultTypes(testContext);
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return sm.getSearchResultType(searchResultTypeKey, testContext);
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes(ContextInfo context)
			throws OperationFailedException {
		return sm.getSearchCriteriaTypes(testContext);
	}

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return sm.getSearchCriteriaType(searchCriteriaTypeKey, testContext);
	}

	@Override
	public SearchResult search(SearchRequest searchRequest, ContextInfo context)
			throws MissingParameterException {
		if("test.search1".equals(searchRequest.getSearchKey())){
			SearchResult searchResult = new SearchResult();
			SearchResultRow row = new SearchResultRow();
			SearchResultCell cell = new SearchResultCell();
			cell.setKey("col1");
			cell.setValue("value1-1");
			row.getCells().add(cell);
			
			cell = new SearchResultCell();
			cell.setKey("col2");
			cell.setValue("value2-1");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
		
			row = new SearchResultRow();
			cell = new SearchResultCell();
			cell.setKey("col1");
			cell.setValue("value1-2");
			row.getCells().add(cell);
			
			cell = new SearchResultCell();
			cell.setKey("col2");
			cell.setValue("value2-2");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
			return searchResult;
		}
		
		if("test.search2".equals(searchRequest.getSearchKey())){
			SearchResult searchResult = new SearchResult();
			SearchResultRow row = new SearchResultRow();
			SearchResultCell cell = new SearchResultCell();
			cell.setKey("C-A");
			cell.setValue("Avalue1-1");
			row.getCells().add(cell);
			
			cell = new SearchResultCell();
			cell.setKey("C-B");
			cell.setValue("Avalue2-1");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
		
			row = new SearchResultRow();
			cell = new SearchResultCell();
			cell.setKey("C-A");
			cell.setValue("Avalue1-2");
			row.getCells().add(cell);
			
			cell = new SearchResultCell();
			cell.setKey("C-B");
			cell.setValue("Avalue2-2");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
			return searchResult;
		}else if("test.crossSearch".equals(searchRequest.getSearchKey())){
			return sm.search(searchRequest, testContext);
		}
		return null;
	}

}
