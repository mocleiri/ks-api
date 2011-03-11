/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.adapter.checker;

import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.core.exceptions.*;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationServiceInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationStateInfc;
import org.kuali.student.enrollment.lpr.service.adapter.LuiPersonRelationAdapter;

import java.util.List;

/**
 * Checks if updating read only fields.
 *
 * @Author Norm
 */
public class LuiPersonRelationReadOnlyFieldUpdatesCheckerAdapter
		extends LuiPersonRelationAdapter
		implements LuiPersonRelationServiceInfc {

	 
	@Override
	public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState, String luiPersonRelationType, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
			throws AlreadyExistsException,
			DoesNotExistException,
			DisabledIdentifierException,
			ReadOnlyException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		if (luiPersonRelationInfo.getId() != null) {
			throw new ReadOnlyException("Id is not allowed to be supplied on a create");
		}
		if (luiPersonRelationInfo.getMetaInfo() != null) {
			throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
		}
		return this.getProvider().createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationType, luiPersonRelationInfo, context);
	}

	@Override
	public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		if (luiPersonRelationInfo.getId() != null) {
			throw new ReadOnlyException("Id is not allowed to be supplied on a create");
		}
		if (luiPersonRelationInfo.getMetaInfo() != null) {
			throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
		}
		return this.getProvider().createLuiPersonRelation(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context);
	}

	@Override
	public LuiPersonRelationInfc updateLuiPersonRelation(String luiPersonRelationId, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		LuiPersonRelationInfc orig = this.fetchLUIPersonRelation(luiPersonRelationId, context);
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
		return this.getProvider().updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context);
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
