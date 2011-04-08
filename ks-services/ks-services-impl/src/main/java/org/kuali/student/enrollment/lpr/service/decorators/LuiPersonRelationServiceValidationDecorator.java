/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.decorators;


import org.kuali.student.common.exceptions.*;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;

import java.util.*;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.infc.HoldsValidator;
import org.kuali.student.datadictionary.DataDictionaryValidatorInfc;

/**
 * @author sambit
 */
public class LuiPersonRelationServiceValidationDecorator extends LuiPersonRelationServiceDecorator implements HoldsValidator{

	private DataDictionaryValidatorInfc validator;

    @Override
    public DataDictionaryValidatorInfc getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidatorInfc validator) {
        this.validator = validator;
    }

	@Override
	public List<ValidationResultInfo> validateLuiPersonRelation(String validationType,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		
	    this.validator.validate(DataDictionaryValidatorInfc.ValidationType.fromString(validationType), luiPersonRelationInfo, context);
		
	    return super.validateLuiPersonRelation(validationType, luiPersonRelationInfo, context);
		
	}

	@Override
	public String createLuiPersonRelation(String personId, String luiId,
			String luiPersonRelationType,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context) throws
			AlreadyExistsException,
			DoesNotExistException,
			DisabledIdentifierException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			DataValidationErrorException,
			ReadOnlyException,
			PermissionDeniedException {
		List<ValidationResultInfo> vris = this.validateLuiPersonRelation(DataDictionaryValidatorInfc.ValidationType.FULL_VALIDATION.name(),
				luiPersonRelationInfo, context);
		if (luiPersonRelationInfo.getId() != null) {
			throw new ReadOnlyException("Id is not allowed to be supplied on a create");
		}
		if (luiPersonRelationInfo.getMetaInfo() != null) {
			throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
		}
		if (!vris.isEmpty()) {
			throw new DataValidationErrorException("Failed validation", vris);
		}
		return super.createLuiPersonRelation(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context);
	}

	@Override
	public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context)
	throws DataValidationErrorException,
	DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	ReadOnlyException,
	OperationFailedException,
	PermissionDeniedException,
	VersionMismatchException {
		List<ValidationResultInfo> vris = this.validateLuiPersonRelation(DataDictionaryValidatorInfc.ValidationType.FULL_VALIDATION.name(),
				luiPersonRelationInfo, context);
		LuiPersonRelationInfo orig = this.fetchLuiPersonRelation(luiPersonRelationId, context);
		// once created these fields are never updatable directly by the application
		checkReadOnly("id", orig.getId(), luiPersonRelationInfo.getId());
		checkReadOnly("type", orig.getType(), luiPersonRelationInfo.getType());
		checkReadOnly("createId", orig.getMetaInfo().getCreateId(), luiPersonRelationInfo.getMetaInfo().getCreateId());
		checkReadOnly("createTime", orig.getMetaInfo().getCreateTime(), luiPersonRelationInfo.getMetaInfo().getCreateTime());
		// if nothing has changed since fetching then cannot update update info either
		// TODO: consider throwing the optimistic lock exception (VersionMismatchException) if version ids do not match
		if (orig.getMetaInfo().getVersionInd().equals(luiPersonRelationInfo.getMetaInfo().getVersionInd())) {
			checkReadOnly("updateId", orig.getMetaInfo().getUpdateId(), luiPersonRelationInfo.getMetaInfo().getUpdateId());
			checkReadOnly("updateTime", orig.getMetaInfo().getUpdateTime(), luiPersonRelationInfo.getMetaInfo().getUpdateTime());
		}

		if (!vris.isEmpty()) {
			throw new DataValidationErrorException("Failed validation", vris);
		}
		
		return super.updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context);
	}

    private void checkReadOnly(String field, Object orig, Object supplied)
    throws ReadOnlyException {
        checkReadOnly(field, orig, supplied, "" + orig, "" + supplied);
    }

    private void checkReadOnly(String field, Object orig, Object supplied, String origStr, String suppliedStr)
    throws ReadOnlyException {
        if (orig != null) {
            if (orig.equals(supplied)) {
                return;
            }
        }
        throw new ReadOnlyException(field + " is read only but the original value " + origStr + " and the supplied new=" + suppliedStr);
    }
}

