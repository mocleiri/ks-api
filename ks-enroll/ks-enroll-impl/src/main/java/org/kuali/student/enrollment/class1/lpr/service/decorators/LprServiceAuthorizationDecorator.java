package org.kuali.student.enrollment.class1.lpr.service.decorators;

import java.util.*;


import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.enrollment.lpr.service.LprServiceDecorator;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

/**
 * An example authorization decorator for the {@link org.kuali.student.enrollment.lpr.service.LprService}.
 * We would like to decorate the createBulkRelationshipsForPerson method here with authorization checks and custom logic
 * 
 * @author sambit
 *
 */

public class LprServiceAuthorizationDecorator extends LprServiceDecorator implements HoldsPermissionService {
 
	private PermissionService permissionService;
	 
	public static final String ENRLLMENT_NAMESPACE = "KS-Enrollment";
		
	@Override
	public PermissionService getPermissionService() {
		return permissionService;
	}

	@Override
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}


    @Override
    public List<LuiPersonRelationInfo> getLprsByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return getNextDecorator().getLprsByLui(luiId, context);
    }


	  /**
     * Fake authorization method.
     *
     * @param principal
     * @param permissionName  the authorization permission
     * @param qualifier an authorization qualifier
     * @return true if authorization successful
     */

	protected boolean isAuthorized(String principal, String permissionName, String qualifier) {
		Map<String, String> qualifierDetails = new HashMap<String,String>();
		qualifierDetails.put("qualifierKey", qualifier);
		return this.permissionService.isAuthorized(principal,
				ENRLLMENT_NAMESPACE,
				permissionName,
				qualifierDetails);
	}

}
