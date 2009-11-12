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
package org.kuali.student.core.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

public class AbstractSearchableCrudDaoImpl extends AbstractCrudDaoImpl
		implements SearchableDao {



	@Override
	public List<Result> searchForResults(String searchTypeKey,
			Map<String, String> queryMap, SearchTypeInfo searchTypeInfo,
			List<QueryParamValue> queryParamValues) {

		String queryString = queryMap.get(searchTypeKey);
		String optionalQueryString = "";
		
		//add in optional
		for(QueryParamValue queryParamValue : queryParamValues){
			for(QueryParamInfo queryParamInfo:searchTypeInfo.getSearchCriteriaTypeInfo().getQueryParams()){
				if(queryParamInfo.isOptional()&&queryParamInfo.getKey().equals(queryParamValue.getKey())){
					if(!optionalQueryString.isEmpty()){
						optionalQueryString += " AND ";
					}
					optionalQueryString += queryMap.get(queryParamValue.getKey());
				}
			}
		}
		if(!optionalQueryString.isEmpty()){
			if(!queryString.toUpperCase().contains(" WHERE ")){
				queryString += " WHERE ";
			}
			queryString += optionalQueryString;
		}
		
		Query query = em.createQuery(queryString);
        List<QueryParamInfo> queryParameterInfos = 
            (searchTypeInfo == null || searchTypeInfo.getSearchCriteriaTypeInfo() == null)? null :
            searchTypeInfo.getSearchCriteriaTypeInfo().getQueryParams();
		
		//replace all the "." notation with "_" since the "."s in the ids of the queries will cause problems with the jpql  
		if(queryParamValues!=null){
            int parameterCount = 0;
			for (QueryParamValue queryParamValue : queryParamValues) {
                String parameterKey = queryParamValue.getKey().replace(".", "_");
                Object parameterValue = null;
                String parameterDataType = (queryParameterInfos == null || 
                        queryParameterInfos.get(parameterCount) == null ||
                        queryParameterInfos.get(parameterCount).getFieldDescriptor() == null)? null :
                            queryParameterInfos.get(parameterCount).getFieldDescriptor().getDataType();
                if (parameterDataType != null && parameterDataType.equals("date")) {
                    Calendar cal = null;
                    String dateString = (String) queryParamValue.getValue();
                    if (dateString != null) {
                        int mo = Integer.parseInt(dateString.substring(0, 2)) -1;
                        int dt = Integer.parseInt(dateString.substring(3, 5));
                        int yr = Integer.parseInt(dateString.substring(6, 10));
                        cal = new GregorianCalendar(yr, mo, dt);
                        parameterValue = new java.sql.Date(cal.getTime().getTime());
                    } else {
                        parameterValue = null;
                    }
                } else {
                    parameterValue = queryParamValue.getValue();
                }
                query.setParameter(parameterKey, parameterValue);
                
                parameterCount++;
			}
		}

		// Turn into results
		List<Result> results = new ArrayList<Result>();
		
		List<?> queryResults = query.getResultList();
		
		if(queryResults!=null){
			//Copy the query results to a Result object
			for(Object queryResult:queryResults){
				Result result = new Result();
				int i=0;
				for (ResultColumnInfo resultColumn : searchTypeInfo.getSearchResultTypeInfo().getResultColumns()) {
			
					ResultCell resultCell = new ResultCell();
					resultCell.setKey(resultColumn.getKey());
					
					if(queryResult.getClass().isArray()){
						resultCell.setValue(((Object[])queryResult)[i].toString());
					}else{
						resultCell.setValue(queryResult.toString());
					}
					
					result.getResultCells().add(resultCell);
					i++;
				}
				results.add(result);
			}
		
		}
		return results;
	}



}
