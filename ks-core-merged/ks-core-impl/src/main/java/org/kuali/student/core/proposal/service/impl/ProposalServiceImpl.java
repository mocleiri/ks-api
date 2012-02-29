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

package org.kuali.student.core.proposal.service.impl;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dictionary.service.DictionaryService;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.ReferenceTypeInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.*;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.search.service.SearchManager;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.proposal.dao.ProposalDao;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalReferenceType;
import org.kuali.student.core.proposal.entity.ProposalType;
import org.kuali.student.core.proposal.service.ProposalService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the Proposal Service
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Proposal+Service">ProposalService</>
 */
@WebService(endpointInterface = "org.kuali.student.core.proposal.service.ProposalService", serviceName = "ProposalService", portName = "ProposalService", targetNamespace = "http://student.kuali.org/wsdl/proposal")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
// TODO KSCM-167
public class ProposalServiceImpl implements ProposalService {
    private ProposalDao proposalDao;

    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;
    
    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#createProposal(java.lang.String, org.kuali.student.core.proposal.dto.ProposalInfo,org.kuali.student.common.dto.ContextInfo)
     */
    @Override
    @Transactional(readOnly=false)
	public ProposalInfo createProposal(String proposalTypeKey, ProposalInfo proposalInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo    )  throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");
        checkForMissingParameter(proposalInfo, "proposalInfo");

        // Validate
        List<org.kuali.student.common.validation.dto.ValidationResultInfo> validationResults = validateProposal("OBJECT", proposalInfo, contextInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults );
        }
        
        
        if (proposalInfo.getProposerPerson() != null && !proposalInfo.getProposerPerson().isEmpty() && proposalInfo.getProposerOrg() != null && !proposalInfo.getProposerOrg().isEmpty()) {
            throw new InvalidParameterException("Not allowed to have both Person and Organization propsers");
        }
       try {
           Proposal proposal = ProposalAssembler.toProposal(proposalTypeKey, proposalInfo, proposalDao);
           proposalDao.create(proposal);

           return ProposalAssembler.toProposalInfo(proposal);
       } catch (VersionMismatchException e) {
           throw new InvalidParameterException(e.getMessage());
       }

    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#deleteProposal(java.lang.String,org.kuali.student.common.dto.ContextInfo)
     */
    @Override
    @Transactional(readOnly=false)
	public StatusInfo deleteProposal(@WebParam(name = "proposalId") String proposalId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException{
        checkForMissingParameter(proposalId, "proposalId");

        StatusInfo status = new StatusInfo();
        try {
            proposalDao.delete(Proposal.class, proposalId);
        } catch (DoesNotExistException e) {
            status.setSuccess(false);
        }

        return status;
    }


    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposal(java.lang.String,org.kuali.student.common.dto.ContextInfo)
     */
    @Override
    public ProposalInfo getProposal(@WebParam(name = "proposalId") String proposalId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException{
        checkForMissingParameter(proposalId, "proposalId");
        Proposal entity = proposalDao.fetch(Proposal.class, proposalId);
        return ProposalAssembler.toProposalInfo(entity);
    }

    /**
     * This overridden method ...
     *
     * @param proposalTypeKey
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalType(String)
     * @return
     */
    @Override
    public ProposalTypeInfo getProposalType(@WebParam(name = "proposalTypeKey") String proposalTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        ProposalType proposalType = proposalDao.fetch(ProposalType.class, proposalTypeKey);
        return ProposalAssembler.toProposalTypeInfo(proposalType);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#
     */
    @Override
    public List<ProposalTypeInfo> getProposalTypes() throws OperationFailedException {
        List<ProposalType> proposalTypes = proposalDao.find(ProposalType.class);
        return ProposalAssembler.toProposalTypeInfos(proposalTypes);
    }

    /**
     */
    @Override
    public List<ProposalTypeInfo> getProposalTypesForReferenceType(String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(referenceTypeKey, "referenceTypeKey");

        List<ProposalType> proposalTypes = proposalDao.getProposalTypesForReferenceType(referenceTypeKey);
        return ProposalAssembler.toProposalTypeInfos(proposalTypes);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByIdList(java.util.List,org.kuali.student.common.dto.ContextInfo)
     */
    @Override
    public List<ProposalInfo> getProposalsByIdList(@WebParam(name = "proposalIdList") List<String> proposalIdList, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalIdList, "proposalIdList");
        checkForEmptyList(proposalIdList, "proposalIdList");

        List<Proposal> proposals = proposalDao.getProposalsByIdList(proposalIdList);
        if (proposals.size() != proposalIdList.size()) {
            throw new DoesNotExistException();
        }
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByProposalType(java.lang.String,org.kuali.student.common.dto.ContextInfo)
     */
    @Override
    public List<ProposalInfo> getProposalsByProposalType(@WebParam(name = "proposalTypeKey") String proposalTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        List<Proposal> proposals = proposalDao.getProposalsByProposalType(proposalTypeKey);
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByReference(java.lang.String, java.lang.String,ContextInfo)
     */
    @Override
    public List<ProposalInfo> getProposalsByReference(@WebParam(name = "referenceTypeKey") String referenceTypeKey,
                                                      @WebParam(name = "referenceId") String referenceId,
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(referenceTypeKey, "referenceTypeKey");
        checkForMissingParameter(referenceId, "referenceId");

        List<Proposal> proposals = proposalDao.getProposalsByReference(referenceTypeKey, referenceId);
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalsByState(java.lang.String, java.lang.String,ContextInfo)
     */
    @Override
    public List<ProposalInfo> getProposalsByState(@WebParam(name = "proposalState") String proposalState,
                                                  @WebParam(name = "proposalTypeKey") String proposalTypeKey,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(proposalState, "proposalState");
        checkForMissingParameter(proposalTypeKey, "proposalTypeKey");

        List<Proposal> proposals = proposalDao.getProposalsByState(proposalState, proposalTypeKey);
        return ProposalAssembler.toProposalInfos(proposals);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#getProposalByWorkflowId(java.lang.String,org.kuali.student.common.dto.ContextInfo)
     */
	@Override
	public ProposalInfo getProposalByWorkflowId(@WebParam(name = "workflowId") String workflowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(workflowId, "workflowId");
		
        Proposal entity = proposalDao.getProposalByWorkflowId(workflowId);
        return ProposalAssembler.toProposalInfo(entity);
	}

    /**
     */
    //@Override
    public List<ReferenceTypeInfo> getReferenceTypes() throws OperationFailedException {
        List<ProposalReferenceType> referenceTypes = proposalDao.find(ProposalReferenceType.class);
        return ProposalAssembler.toReferenceTypeInfos(referenceTypes);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#updateProposal(java.lang.String, org.kuali.student.core.proposal.dto.ProposalInfo,org.kuali.student.common.dto.ContextInfo)
     */
    @Override
    @Transactional(readOnly=false)
	public ProposalInfo  updateProposal(@WebParam(name = "proposalId") String proposalId, @WebParam(name = "proposalInfo") ProposalInfo proposalInfo,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException{
        checkForMissingParameter(proposalId, "proposalId");
        checkForMissingParameter(proposalInfo, "proposalInfo");

        // Validate
        List<org.kuali.student.common.validation.dto.ValidationResultInfo> validationResults = validateProposal("OBJECT", proposalInfo, contextInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
        
        proposalInfo.setId(proposalId);
        if (proposalInfo.getProposerPerson() != null && !proposalInfo.getProposerPerson().isEmpty() && proposalInfo.getProposerOrg() != null && !proposalInfo.getProposerOrg().isEmpty()) {
            throw new InvalidParameterException("Not allowed to have both Person and Organization propsers");
        }

        Proposal proposal = ProposalAssembler.toProposal(proposalInfo.getType(), proposalInfo, proposalDao);
        Proposal updated = proposalDao .update(proposal);

        return ProposalAssembler.toProposalInfo(updated);
    }

    /**
     * @see org.kuali.student.core.proposal.service.ProposalService#validateProposal(String, org.kuali.student.core.proposal.dto.ProposalInfo,org.kuali.student.common.dto.ContextInfo)
     */
    @Override
    public List<org.kuali.student.common.validation.dto.ValidationResultInfo> validateProposal(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                                               @WebParam(name = "proposalInfo") ProposalInfo proposalInfo,
                                                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationTypeKey, "validationType");
        checkForMissingParameter(proposalInfo, "proposalInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(ProposalInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<org.kuali.student.common.validation.dto.ValidationResultInfo> validationResults = defaultValidator.validateObject(proposalInfo, objStructure, contextInfo);
        return validationResults;         
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    /**
     * Check for an empty list
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForEmptyList(Object param, String paramName)
            throws MissingParameterException {
        if (param != null && param instanceof List<?> && ((List<?>)param).size() == 0) {
            throw new MissingParameterException(paramName + " can not be an empty list");
        }
    }

    public ProposalDao getProposalDao() {
        return proposalDao;
    }

    public void setProposalDao(ProposalDao dao) {
        this.proposalDao = dao;
    }

    /**
     * @see org.kuali.student.common.dictionary.service.old.DictionaryService#getObjectStructure(java.lang.String)
     */
    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    /**
     * @see org.kuali.student.common.dictionary.service.old.DictionaryService#getObjectTypes()
     */
    @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchCriteriaType(java.lang.String)
     */
    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey,null);
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchCriteriaTypes()
     */
    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes(null);
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchResultType(java.lang.String)
     */
    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey,null);
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchResultTypes()
     */
    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        return searchManager.getSearchResultTypes(null);
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchType(java.lang.String)
     */
    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey,null);    
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchTypes()
     */
    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        return searchManager.getSearchTypes(null);
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchTypesByCriteria(java.lang.String)
     */
    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey,null);
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchTypesByResult(java.lang.String)
     */
    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchTypesByResult(searchResultTypeKey,null);
    }

	@Override
	public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
		return searchManager.search(searchRequest, proposalDao,null);
	}

    /**
     * @return the validatorFactory
     */
    public ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

    /**
     * @param validatorFactory the validatorFactory to set
     */
    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    /**
     * @return the searchManager
     */
    public SearchManager getSearchManager() {
        return searchManager;
    }

    /**
     * @return the dictionaryServiceDelegate
     */
    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }



}
