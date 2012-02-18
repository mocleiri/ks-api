package org.kuali.student.r1.common.assembly;

import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.exceptions.AlreadyExistsException;
import org.kuali.student.r1.common.exceptions.CircularReferenceException;
import org.kuali.student.r1.common.exceptions.CircularRelationshipException;
import org.kuali.student.r1.common.exceptions.DataValidationErrorException;
import org.kuali.student.r1.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r1.common.exceptions.DoesNotExistException;
import org.kuali.student.r1.common.exceptions.InvalidParameterException;
import org.kuali.student.r1.common.exceptions.MissingParameterException;
import org.kuali.student.r1.common.exceptions.OperationFailedException;
import org.kuali.student.r1.common.exceptions.PermissionDeniedException;
import org.kuali.student.r1.common.exceptions.UnsupportedActionException;
import org.kuali.student.r1.common.exceptions.VersionMismatchException;

public interface BusinessServiceMethodInvoker {
	@SuppressWarnings("unchecked")
	public void invokeServiceCalls(BaseDTOAssemblyNode results) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, CircularRelationshipException, AssemblyException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException;
}
