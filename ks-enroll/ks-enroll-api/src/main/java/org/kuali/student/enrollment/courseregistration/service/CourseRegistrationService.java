/**
 */
package org.kuali.student.enrollment.courseregistration.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.grading.dto.LoadInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;

/**
 * The Course Registration Service is a Class II service supporting
 * the process of registering a student in course(s) for a term. The
 * service provides operations for creating and validating
 * registration requests , registering for a course, and dropping a
 * course. This service supports the concept of registration cart in
 * the application and all of the transactional requests for
 * registration are made through this service. As part of negotiating
 * the student's registration, operations are provided to manage
 * related exceptions and holds related to registration.
 * 
 * @author Kuali Student Team (sambit)
 */

@WebService(name = "CourseRegistrationService", targetNamespace = CourseRegistrationServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseRegistrationService  {

    /**
     * Checks if a student can register at all i.e., checks if the students
     * current academic status allows them to register. This is more generic
     * operation and doesn't take in the term information.
     * <p>
     * Implementation notes: Checks high-level conditions required for
     * registration e.g. student admitted,in good standing , alive etc.
     * 
     * @param studentId Identifier of the student
     * @param context Information Containing the principalId and locale information about the caller of the service operation.
     * @return list of errors, warnings or informational messages
     * @throws DoesNotExistException If student id does not exist student id not
     *             found
     * @throws InvalidParameterException Invalid student id in the input
     * @throws MissingParameterException Student id missing in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException
     */
    public  List<ValidationResultInfo> checkStudentEligibility(@WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks the eligibility of a student to register given the term. If the
     * student is eligible for a term, then they can build reg cart for the
     * term.
     * <p>
     * Implementation notes: Check term eligibility for the student e.g.
     * exemptions and no holds for that term on the student
     * 
     * @param studentId Identifier of the student
     * @param termId The unique key for the term
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return list of errors, warnings or informational messages
     * @throws InvalidParameterException Invalid student id or term id
     * @throws MissingParameterException Student id or term id missing in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks if the student is eligible to register for a particular course
     * offering.
     * <p>
     * Implementation notes: This operation does course requirements,
     * eligibility rules, prerequisite and corequisite checks for course
     * offering eligibility. Doesn't do any seat restriction checks.
     * 
     * @param studentId Identifier of the student
     * @param courseOfferingId Identifier of the course offering
     * @param context   Information Containing the principalId and locale information about the caller of the service operation.
     * @return list of errors, warnings or informational messages.
     * @throws InvalidParameterException Invalid student or course offering id
     * @throws MissingParameterException Missing student or course offering id
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(@WebParam(name = "studentId") String studentId, @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks if the student is eligible to register for a particular
     * registration group. Returns a {@link List} of
     * {@link ValidationResultInfo}. When a student is eligible the {@link List}
     * contains a single {@link ValidationResultInfo} with error level OK. Also
     * returns info on expiring restrictions as part of the message.
     * 
     * @param studentId Identifier of the student
     * @param regGroupId Identifier of the registration group
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return list of errors, warnings or informational messages
     * @throws InvalidParameterException Invalid student id or regGroupId
     * @throws MissingParameterException Missing student id or regGroupId
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public List<ValidationResultInfo> checkStudentEligibiltyForRegGroup(@WebParam(name = "studentId") String studentId, @WebParam(name = "regGroupId") String regGroupId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the registration groups for a course offering from the
     * CourseOfferingService and then filters out the registration groups the
     * student is not eligible for using checkEligibilityForRegGroup. It does
     * the eligibility checks without the seat pool availability.
     * 
     * @param studentId
     * @param courseOfferingId
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return  A List of RegistrationGroup's filtered for the student.
     * @throws InvalidParameterException Invalid studentId or courseOfferingId
     * @throws MissingParameterException Missing studentId or courseOfferingId
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public List<RegistrationGroupInfo> getEligibleRegGroupsForStudentInCourseOffering(@WebParam(name = "studentId") String studentId, @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculate the credit load for a student in a particular term. This
     * information can be used to display in the cart. this
     * 
     * @param studentId Identifier of the student
     * @param termId Unique key of the term
     * @param context   Information Containing the principalId and locale information about the caller of the service operation.
     * @return  The Load for a student in the specified term.
     * @throws InvalidParameterException Invalid termId or studentId in the
     *             input
     * @throws MissingParameterException Missing termId or studentId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this check
     */
    public LoadInfo calculateCreditLoadForTerm(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculate the credit load for a student in a particular registration
     * request. It also adds up the credits for courses the student has
     * registered for already in the given term to the registration request and
     * returns the total.
     * 
     * @param studentId Id of the student
     * @param regRequestInfo Registration request info
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return  The Load for a student in the current term and the specified Registration request.
     * @throws InvalidParameterException Invalid student id or
     *             {@link RegRequestInfo}
     * @throws MissingParameterException Missing student id or
     *             {@link RegRequestInfo}
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to calculate the credit
     *             load for the student
     */
    public LoadInfo calculateCreditLoadForRegRequest(@WebParam(name = "studentId") String studentId, @WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the available seat count for a particular course offering. It sums
     * up the available seats for individual registration groups under the same
     * course offering.
     * 
     * @param courseOfferingId
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The available seat count for the specified CourseOffering.
     * @throws InvalidParameterException Invalid courseOfferingId in the input
     * @throws MissingParameterException Missing courseOfferingId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get available seat count for the registration group.
     * 
     * @param regGroupId Identifier of the registration group
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The available seat count for the specified Registration Group
     * @throws InvalidParameterException Invalid regGroupId in the input
     * @throws MissingParameterException Missing regGroupId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsForRegGroup(@WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the number of seats available for a particular student in a
     * registration group.
     * <p>
     * Implementation notes : Seats available for a student taking seat pool (if
     * any) into consideration.
     * 
     * @param studentId Identifier of the student
     * @param regGroupId Identifier of the registration group
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The available seat count for the specified student in a specified RegistrationGroup.
     * @throws InvalidParameterException Invalid studentId or regGroupId in the
     *             input
     * @throws MissingParameterException Missing studentId or regGroupId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsForStudentInRegGroup(@WebParam(name = "studentId") String studentId, @WebParam(name = "regGroupId") String regGroupId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Returns the available seat count in a particular seat pool. This is an admin
     * support function to check the seat pool usage.
     * 
     * @param seatPoolId Identifier of the seatPool
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The available seat count for the specified seat pool.
     * @throws InvalidParameterException Invalid seatPool in the input
     * @throws MissingParameterException Missing parameter seatPoolId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsInSeatPool(@WebParam(name = "seatPoolId") String seatPoolId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a registration request for a student.
     * <p>
     * Implementation Notes : This operation does the following steps:
     * <ul>
     * <li>Validate the data in the regRequestInfo parameter. If invalid throw
     * an exception.
     * <li>Create an id and persist the registration request.
     * <li>Return the updated registration request.
     * <li>Throw an AlreadyExistsException when there is an existing request by
     * the same requesting person for a term in DRAFT state.
     *
     * @param  regRequestTypeKey The registration request type key.
     * @param regRequestInfo The registration request object to be created
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The modified RegRequest object after it has
     * @throws DoesNotExistException if the type key does not exist
     * @throws DataValidationErrorException Invalid data in the create request
     * @throws InvalidParameterException Invalid parameter
     *             {@link RegRequestInfo} in the input
     * @throws MissingParameterException Missing parameter
     *             {@link RegRequestInfo} in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     * @throws ReadOnlyException 	An attempt at supplying information designated as read only
     */
    public RegRequestInfo createRegRequest(@WebParam(name = "regRequestTypeKey") String regRequestTypeKey,
            @WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo, @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException;

    /**
     * Check the request state and if its in DRAFT, updates it with the input
     * {@link RegRequestInfo} values. The id is fetched from the
     * {@link RegRequestInfo} in the parameter. If the state is not valid, throw
     * a {@link DataValidationErrorException}.
     * <p>
     * Implementation notes:This method shouldn't update the state of a
     * registration request since that can be done as part of any transaction
     * only. This operation will be called to save a registration cart after
     * changes e.g addition or deletion of courses.
     *
     * @param  regRequestId The RegRequest identifier.
     * @param regRequestInfo The registration request object to be saved or
     *            updated
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The updated RegRequest object.
     * @throws DataValidationErrorException The {@link RegRequestInfo} is not a
     *             valid request
     * @throws InvalidParameterException Invalid regRequestId in the input
     * @throws MissingParameterException or {@link RegRequestInfo} in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     * @throws ReadOnlyException 	An attempt at supplying information designated as read only
     * @throws VersionMismatchException optimistic locking failure or the action was attempted on an out of date version.
     */
    public RegRequestInfo updateRegRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Delete the registration request from the database. There is permission
     * restriction and only administrative users should be allowed to delete
     * registration requests that are not in draft state.
     * 
     * @param regRequestId Identifier of registration request
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The Status of the delete operation.
     * @throws DoesNotExistException  The identified RegRequest does not exist.
     * @throws InvalidParameterException Invalid regRequestId in the input
     * @throws MissingParameterException Missing parameter regRequestId
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public StatusInfo deleteRegRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validate a registration request to see that there are no conflicting or
     * invalid/ in-eligible registration groups in the request after each
     * modification or when finally submitting or saving it.
     *
     * @param validationTypeKey The identifier for the validation Type
     * @param  regRequestTypeKey The identifier for the RegRequest Type to be validated.
     * @param regRequestInfo The registration request to be validated
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The list of ValidationResult's generated during the validation of the RegRequest.
     * @throws DataValidationErrorException Invalid {@link RegRequestInfo} in
     *             the input
     * @throws InvalidParameterException Invalid {@link RegRequestInfo} in the
     *             input
     * @throws InvalidParameterException Invalid fields e.g, regRequestId or
     *             regGroupId in the {@link RegRequestInfo}
     * @throws MissingParameterException Missing parameter
     *             {@link RegRequestInfo}
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public List<ValidationResultInfo> validateRegRequest(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                         @WebParam(name = "regRequestTypeKey") String regRequestTypeKey,
                                                         @WebParam(name = "regRequestInfo") RegRequestInfo regRequestInfo,
                                                         @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Verifies a registration request to make sure that the data in the
     * {@link RegRequestInfo} satisfies the rules to be a valid registration
     * request. This includes course pre-requisites or co-requisites and credit
     * load rules.
     * <p>
     * Implementation notes: Call the following methods as part of the
     * eligibility checks - checkStudentEligibilityForTerm,
     * checkStudentEligibiltyForRegGroup,
     * checkStudentEligibiltyForCourseOffering,
     * getAvailableSeatsForStudentInRegGroup.
     * 
     * @param regRequestId The identifier for the RegRequest to be verified
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The list of ValidationResult's generated from the verification of the Registration Request.
     * @throws DataValidationErrorException Invalid data in
     *             {@link RegRequestInfo}
     * @throws InvalidParameterException Invalid fields e.g, regRequestId or
     *             regGroupId in the {@link RegRequestInfo}
     * @throws MissingParameterException Missing parameter
     *             {@link RegRequestInfo}
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public List<ValidationResultInfo> verifyRegRequestForSubmission(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create a new registration request from an existing registration request.
     * Convenient when a transaction fails and a students wants to rebuild the
     * registration cart with earlier items. It can also be used in any such
     * other scenario where a registration request has passed one of the final
     * states (or is canceled) and a new registration request needs to be
     * created for re-initiating the transaction.
     * 
     * @param existingRegRequestId The exiting req request id from which the new
     *            one is created
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The new RegRequest object.
     * @throws DoesNotExistException The existingRegRequestId does not exist
     * @throws InvalidParameterException Invalid field existingRegRequestId
     * @throws MissingParameterException Missing parameter existingRegRequestId
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     */
    public RegRequestInfo createRegRequestFromExisting(@WebParam(name = "existingRegRequestId") String existingRegRequestId, 
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, 
            InvalidParameterException, MissingParameterException, 
            OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * Fetches the {@link RegRequestInfo}, validates and checks eligibility
     * against multiple rules and then submits the {@link RegRequestInfo}. The
     * latest {@link RegRequestInfo} should already be saved before this
     * operation is called. This operation also handles dropping courses or
     * waitlisting or putting a student in hold-until list if the requested
     * course is full and if the student is is okay to be put in these
     * lists.This method is transactional and for multiple registration request
     * items, each need to succeed or else the overall registration transaction
     * fails; the successful transactions are rolled back. A failure occurs when
     * for any reg group in the request registration, drop, swap, waitlisting,
     * hold until-listing or exception-listing cannot be completed successfully.
     * This operation calls verifyRegRequestForSubmission to make sure that the request is
     * valid before starting the transaction.
     * 
     * @param regRequestId The {@link RegRequestInfo} to be submitted for
     *            registration process.
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The RegResponse object that represents the result of the submitted RegRequest.
     * @throws DoesNotExistException The regRequestId does not exist
     * @throws InvalidParameterException Invalid id regRequestId
     * @throws MissingParameterException Missing regRequestId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this action
     * @throws AlreadyExistsException When the reg request is already submitted
     */
    public RegResponseInfo submitRegRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException;

    /**
     * Retrieves a registration request by id.
     * 
     * @param regRequestId The regRequestId to be retrieved
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The RegRequest object for the specified id.
     * @throws DoesNotExistException No {@link RegRequestInfo} found for the id.
     * @throws InvalidParameterException Invalid id regRequestId
     * @throws MissingParameterException Missing regRequestId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public RegRequestInfo getRegRequest(@WebParam(name = "regRequestId") String regRequestId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a registration requests by id list.
     * 
     * @param regRequestIds The list of RegRequest identifiers
     * @param context   Information Containing the principalId and locale information about the caller of the service operation.
     * @return The list of RegRequest objects for the specified regRequest identifier.
     * @throws DoesNotExistException No regRequestId found for one of the
     *             regRequestIds
     * @throws InvalidParameterException Invalid regRequestId in regRequestIds
     *             list
     * @throws MissingParameterException Missing regRequestIds in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public List<RegRequestInfo> getRegRequestsByIds(@WebParam(name = "regRequestIds") List<String> regRequestIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the registration requests for a student by term and student id.
     * Additionally the state of the registration request can also be passed so
     * that only requests in certain states are returned.
     * 
     * @param requestorId Id of the requestor
     * @param termId Id of the term
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The list of RegRequest objects for the spe
     * @throws InvalidParameterException Invalid studentId, termId or request
     *             state
     * @throws MissingParameterException Missing studentId or termId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public List<RegRequestInfo> getUnsubmittedRegRequestsByRequestorAndTerm(@WebParam(name = "requestorId") String requestorId, @WebParam(name = "termId") String termId,
             @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * 	Retrieves a single CourseRegistration by a CourseRegistration Id.
     * 
     * @param courseRegistrationId The identifier for the CourseRegistration to be retrieved.
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return  The CourseRegistration requested.
     * @throws DoesNotExistException The courseRegistrationId is not valid
     * @throws InvalidParameterException Invalid courseRegistrationId in the input
     * @throws MissingParameterException Missing courseRegistrationId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public CourseRegistrationInfo getCourseRegistration(@WebParam(name = "courseRegistrationId") String courseRegistrationId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     *
     * Retrieves a list of CourseRegistration`s from a list of CourseRegistration Ids. The returned list may be in any order and if duplicates Ids are supplied, a unique set may or may not be returned.
     *
     * @param courseRegistrationIds
     * @param context   Information Containing the principalId and locale information about the caller of the service operation.
     * @return A List of CourseRegistration's corresponding to the list of identifiers provided.
     * @throws DoesNotExistException One or more of the courseRegistrationIds is not valid
     * @throws InvalidParameterException Invalid courseRegistrationIds in the input
     * @throws MissingParameterException Missing courseRegistrationIds in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByIds(@WebParam(name = "courseRegistrationIds") List<String> courseRegistrationIds, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves a list of CourseRegistration Ids by CourseRegistration Type.
     *
     * @param courseRegistrationTypeKey  An identifier for a CourseRegistration Type.
     * @param context Information Containing the principalId and locale information about the caller of the service operation.
     * @return A List of CourseRegistration identifiers matching courseRegistrationTypeKey or an empty list if none is found.
     * @throws InvalidParameterException Invalid courseRegistrationTypeKey in the input
     * @throws MissingParameterException Missing courseRegistrationTypeKey in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public List<String> getCourseRegistrationIdsByType (@WebParam (name="courseRegistrationTypeKey") String courseRegistrationTypeKey, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;



    /**
     * Get the list of CourseRegistration objects for the identified student.
     * 
     * @param studentId  The student identifier.
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The list of CourseRegistration objects for the identified student.
     * @throws DoesNotExistException The student identified does not exist.
     * @throws InvalidParameterException Invalid studentId in the input
     * @throws MissingParameterException Missing studentId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudent(@WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
                                                                        InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the list of CourseRegistration objects for the identified student and the identified course offering.
     * 
     * @param studentId The student Identifier.
     * @param courseOfferingId The course Offering Id.
     * @param context   Information Containing the principalId and locale information about the caller of the service operation.
     * @return The list of CourseRegistration objects for the specified student and course offering.
     * @throws InvalidParameterException Invalid studentId or courseOfferingId in the input
     * @throws MissingParameterException Missing studentId or courseOfferingId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndCourseOffering(@WebParam(name = "studentId") String studentId, @WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course registrations for a student by term. Note: not clear if
     * gets the registrations in just the specified term or that term and all
     * included terms. For example: if you ask for the "fall term" do you get
     * registrations for the mini-mesters within that term.
     * 
     * @param studentId The student identifier
     * @param termId The term identifier.
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The list of Course Registration objects for the student and term identified.
     * @throws InvalidParameterException Invalid studentId or term in the input
     * @throws MissingParameterException Missing studentId or term in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public List<CourseRegistrationInfo> getCourseRegistrationsByStudentAndTerm(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get course registrations by course offering id.
     * 
     * @param courseOfferingId The courseOffering identifier
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return The list of CourseRegistration objects for the specified course offering.
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */

    public List<CourseRegistrationInfo> getCourseRegistrationsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Searches for course registrations based on the criteria, returns a list
     * of {@link CourseRegistrationInfo} object.
     *
     * @param criteria
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return
     */
    public List<CourseRegistrationInfo> searchForCourseRegistrations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get the request that resulted in this course registration.
     * 
     * @param courseRegistrationId
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegRequestItemInfo> getRegRequestItemsByCourseRegistration(@WebParam(name = "courseRegistrationId") String courseRegistrationId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get reg requests objects which are attached to this course offering for a
     * student.
     * 
     * @param courseOfferingId
     * @param studentId
     * @param context   Information Containing the principalId and locale information about the caller of the service operation.
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<RegRequestItemInfo> getRegRequestItemsByCourseOfferingAndStudent(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "studentId") String studentId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Searches for course registrations based on the criteria, returns a list
     * of {@link CourseRegistrationInfo} Ids.
     * 
     * @param criteria
     * @param  context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return
     */
    public List<String> searchForCourseRegistrationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return
     */
    public List<ActivityRegistrationInfo> searchForActivityRegistrations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context  Information Containing the principalId and locale information about the caller of the service operation.
     * @return
     */
    public List<String> searchForActivityRegistrationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;
}
