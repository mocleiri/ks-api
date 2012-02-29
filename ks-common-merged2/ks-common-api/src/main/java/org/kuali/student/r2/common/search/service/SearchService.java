/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.common.search.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.search.dto.SearchRequestInfo;
import org.kuali.student.r2.common.search.dto.SearchResultInfo;

import org.kuali.student.r2.common.dto.ContextInfo;


import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

@WebService(name = "SearchService", targetNamespace = "http://student.kuali.org/wsdl/search")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface SearchService {

    /** 
     * Retrieves the list of search types known by this service.
     *
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of search type information
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws OperationFailedException;

    /** 
     * Retrieves information about a particular search type.
     *
     * @param searchTypeKey identifier of the search type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return information on the search type
     * @throws DoesNotExistException specified searchTypeKey not found
     * @throws InvalidParameterException invalid searchTypeKey
     * @throws MissingParameterException searchTypeKey not specified
     * @throws OperationFailedException unable to complete request
     */
    public TypeInfo getSearchType(@WebParam(name="searchTypeKey")String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search types which return results in the
     * specified format.
     *
     * @param searchResultTypeKey identifier of the search result type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of search type information
     * @throws DoesNotExistException specified searchResultTypeKey not found
     * @throws InvalidParameterException invalid searchResultTypeKey
     * @throws MissingParameterException searchResultTypeKey not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getSearchTypesByResult(@WebParam(name="searchResultTypeKey") String searchResultTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search types which use criteria in the
     * specified format.
     *
     * @param searchCriteriaTypeKey identifier of the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of search type information
     * @throws DoesNotExistException specified searchCriteriaTypeKey not found
     * @throws InvalidParameterException invalid searchCriteriaTypeKey
     * @throws MissingParameterException searchCriteriaTypeKey not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getSearchTypesByCriteria(@WebParam(name="searchCriteriaTypeKey")String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search result types known by this
     * service. Search result types describe the return structure for
     * a search.
     *
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of search result type information
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getSearchResultTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws OperationFailedException;

    /** 
     * Retrieves the list of search criteria types known by this
     * service.
     *
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of search criteria type information
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getSearchCriteriaTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException;
}
