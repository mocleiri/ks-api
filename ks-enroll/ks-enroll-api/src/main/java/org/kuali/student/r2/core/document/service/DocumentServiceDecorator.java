/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.document.service;

import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;

import javax.jws.WebParam;
import java.util.List;

/**
 * Refer to service contract javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public class DocumentServiceDecorator implements DocumentService {


    private DocumentService nextDecorator;

    public DocumentService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(DocumentService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }


    @Override
    public TypeInfo getType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getType(typeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(@WebParam(name = "refObjectURI") String refObjectURI, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getTypesByRefObjectURI(refObjectURI, contextInfo);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relatedRefObjectURI") String relatedRefObjectURI, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, contextInfo);
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relationTypeKey") String relationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, contextInfo);
    }

    @Override
    public DocumentInfo createDocument(@WebParam(name = "documentTypeKey") String documentTypeKey, @WebParam(name = "documentCategoryKey") String documentCategoryKey, @WebParam(name = "documentInfo") DocumentInfo documentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createDocument(documentTypeKey, documentCategoryKey, documentInfo, contextInfo);
    }

    @Override
    public DocumentInfo getDocument(@WebParam(name = "documentId") String documentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getDocument(documentId, contextInfo);
    }

    @Override
    public DocumentCategoryInfo getDocumentCategory(@WebParam(name = "documentCategoryKey") String documentCategoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getDocumentCategory(documentCategoryKey, contextInfo);
    }

    @Override
    public List<DocumentInfo> getDocumentsByIds(@WebParam(name = "documentIds") List<String> documentIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getDocumentsByIds(documentIds, contextInfo);
    }

    @Override
    public DocumentInfo updateDocument(@WebParam(name = "documentId") String documentId, @WebParam(name = "documentInfo") DocumentInfo documentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateDocument(documentId, documentInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteDocument(@WebParam(name = "documentId") String documentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteDocument(documentId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateDocument(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "documentInfo") DocumentInfo documentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateDocument(validationTypeKey, documentInfo, contextInfo);
    }

    @Override
    public List<DocumentCategoryInfo> getCategoriesByDocumentId(@WebParam(name = "documentId") String documentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCategoriesByDocumentId(documentId, contextInfo);
    }

    @Override
    public List<DocumentCategoryInfo> getDocumentCategories(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws OperationFailedException {
        return getNextDecorator().getDocumentCategories(contextInfo);
    }

    @Override
    public StatusInfo addDocumentCategoryToDocument(@WebParam(name = "documentId") String documentId, @WebParam(name = "documentCategoryKey") String documentCategoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().addDocumentCategoryToDocument(documentId, documentCategoryKey, contextInfo);
    }

    @Override
    public StatusInfo removeDocumentCategoryFromDocument(@WebParam(name = "documentId") String documentId, @WebParam(name = "documentCategoryKey") String documentCategoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeDocumentCategoryFromDocument(documentId, documentCategoryKey, contextInfo);
    }

    @Override
    public List<String> getRefObjectSubTypes(@WebParam(name = "refObjectTypeKey") String refObjectTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException {
        return getNextDecorator().getRefObjectSubTypes(refObjectTypeKey, contextInfo);
    }

    @Override
    public RefDocRelationInfo createRefDocRelation(@WebParam(name = "refObjectTypeKey") String refObjectTypeKey, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "documentId") String documentId, @WebParam(name = "refDocRelationTypeKey") String refDocRelationTypeKey, @WebParam(name = "refDocRelationInfo") RefDocRelationInfo refDocRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createRefDocRelation(refObjectTypeKey, refObjectId, documentId, refDocRelationTypeKey, refDocRelationInfo, contextInfo);
    }

    @Override
    public RefDocRelationInfo getRefDocRelation(@WebParam(name = "refDocRelationId") String refDocRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRefDocRelation(refDocRelationId, contextInfo);
    }

    @Override
    public List<RefDocRelationInfo> getRefDocRelationsByDoc(@WebParam(name = "documentId") String documentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRefDocRelationsByDoc(documentId, contextInfo);
    }

    @Override
    public List<RefDocRelationInfo> getRefDocRelationsByRef(@WebParam(name = "refObjectTypeKey") String refObjectTypeKey, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRefDocRelationsByRef(refObjectTypeKey, refObjectId, contextInfo);
    }

    @Override
    public RefDocRelationInfo updateRefDocRelation(@WebParam(name = "refDocRelationId") String refDocRelationId, @WebParam(name = "refDocRelationInfo") RefDocRelationInfo refDocRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateRefDocRelation(refDocRelationId, refDocRelationInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteRefDocRelation(@WebParam(name = "refDocRelationId") String refDocRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteRefDocRelation(refDocRelationId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateRefDocRelation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "refDocRelationInfo") RefDocRelationInfo refDocRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateRefDocRelation(validationTypeKey, refDocRelationInfo, contextInfo);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return getNextDecorator().getDataDictionaryEntryKeys(contextInfo);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(@WebParam(name = "entryKey") String entryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        return getNextDecorator().getDataDictionaryEntry(entryKey, contextInfo);
    }

    @Override
    public StateProcessInfo getProcessByKey(@WebParam(name = "processKey") String processKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getProcessByKey(processKey, contextInfo);
    }

    @Override
    public List<String> getProcessByObjectType(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getProcessByObjectType(refObjectUri, contextInfo);
    }

    @Override
    public StateInfo getState(@WebParam(name = "processKey") String processKey, @WebParam(name = "stateKey") String stateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getState(processKey, stateKey, contextInfo);
    }

    @Override
    public List<StateInfo> getStatesByProcess(@WebParam(name = "processKey") String processKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getStatesByProcess(processKey, contextInfo);
    }

    @Override
    public List<StateInfo> getInitialValidStates(@WebParam(name = "processKey") String processKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getInitialValidStates(processKey, contextInfo);
    }

    @Override
    public StateInfo getNextHappyState(@WebParam(name = "processKey") String processKey, @WebParam(name = "currentStateKey") String currentStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getNextHappyState(processKey, currentStateKey, contextInfo);
    }
}
