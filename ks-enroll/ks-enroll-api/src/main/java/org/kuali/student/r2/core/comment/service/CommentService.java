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

package org.kuali.student.r2.core.comment.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.util.constants.CommentServiceConstants;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * The Comment Service allows for the creation and management of user comments and tags associated with other objects across the system.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

@WebService(name = "CommentService", targetNamespace = CommentServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CommentService extends DataDictionaryService, TypeService {

    /**
     * Retrieves information about a comment.
     *
     * @param commentId   comment identifier
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return information about a comment
     * @throws DoesNotExistException     specified commentId not found
     * @throws InvalidParameterException invalid commentId
     * @throws MissingParameterException commentId not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CommentInfo getComment(@WebParam(name = "commentId") String commentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves comment information for a reference. The expected behavior is that if the caller is not authorized to invoke the getCommentsByReferenceAndType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getCommentsByReferenceAndType, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return Comment information
     * @throws DoesNotExistException     specified referenceId, referenceTypeKey not found
     * @throws InvalidParameterException invalid referenceId, referenceTypeKey
     * @throws MissingParameterException referenceId, referenceTypeKey not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CommentInfo> getCommentsByReferenceAndType(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves comment information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getCommentsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getCommentsByType, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @param commentTypeKey   comment type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return list of Comment information
     * @throws DoesNotExistException     specified referenceId, referenceTypeKey, commentTypeKey not found
     * @throws InvalidParameterException invalid referenceId, referenceTypeKey,commentTypeKey
     * @throws MissingParameterException referenceId, referenceTypeKey, commentTypeKey not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CommentInfo> getCommentsByType(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "commentTypeKey") String commentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves a list of Comments corresponding to the
     * given list of Comment Ids
     *
     * @param commentIds  list of Comments to be retrieved
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Comment information
     * @throws DoesNotExistException     an commentKey in list not found
     * @throws InvalidParameterException invalid commentKey
     * @throws MissingParameterException missing commentKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CommentInfo> getCommentsByIds(@WebParam(name = "commentIds") List<String> commentIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Comments of the specified type.
     *
     * @param commentTypeKey type to be retrieved
     * @param contextInfo    Context information containing the principalId and locale
     *                       information about the caller of service operation
     * @return a list of Comment keys
     * @throws InvalidParameterException invalid commentTypeKey
     * @throws MissingParameterException missing commentTypeKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCommentIdsByType(@WebParam(name = "commentTypeKey") String commentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Comments based on the criteria and returns a list
     * of Comment identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Comment Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForCommentIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Comments based on the criteria and returns a list of
     * Comments which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Comment information
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CommentInfo> searchForComments(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds a comment to a reference.
     *
     * @param referenceId      identifier of reference
     * @param referenceTypeKey reference type
     * @param commentTypeKey   comment type
     * @param commentInfo      detailed information about the comment
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return detailed information about the comment
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException        Id or Key does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            attempted update of readonly data
     */
    public CommentInfo createComment(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "commentTypeKey") String commentTypeKey, @WebParam(name = "commentInfo") CommentInfo commentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a comment for a reference.
     *
     * @param commentId   comment identifier
     * @param commentInfo detailed information about the comment
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return detailed information about the comment
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws DoesNotExistException        comment does not exist
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     * @throws ReadOnlyException            attempted update of readonly data    id or key is readonly
     */
    public CommentInfo updateComment(@WebParam(name = "commentId") String commentId, @WebParam(name = "commentInfo") CommentInfo commentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException, ReadOnlyException;

    /**
     * Removes a comment from a reference.
     *
     * @param commentId   identifier of the comment
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     commentId, referenceId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteComment(@WebParam(name = "commentId") String commentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Removes all comments associated with a single reference
     *
     * @param referenceId      identifier of the reference
     * @param referenceTypeKey reference type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     referenceId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCommentsByReference(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a comment. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the comment (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the comment can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param commentInfo       comment information to be tested
     * @param contextInfo       Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, commentInfo
     * @throws MissingParameterException missing validationTypeKey, commentInfo
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateComment(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "commentInfo") CommentInfo commentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves tag information for a reference. The expected behavior is that if the caller is not authorized to invoke the getTags operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getTags, only tags that the caller is authorized to view are included in the returned tagInfoList; tags that the caller is unauthorized to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return list of tag information for the given object ref id and type
     * @throws DoesNotExistException     specified referenceId, referenceTypeKey not found
     * @throws InvalidParameterException invalid referenceId, referenceTypeKey
     * @throws MissingParameterException referenceId, referenceTypeKey not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TagInfo> getTagsByReferenceAndType(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves tag information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getTagsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getTagsByType, only tags that the caller is authorized to view are included in the returned tagInfoList; tags that the caller is unauthorized to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @param tagTypeKey       tag type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return list of tag information for the given type
     * @throws DoesNotExistException     specified referenceId, referenceTypeKey, tagTypeKey not found
     * @throws InvalidParameterException invalid referenceId, referenceTypeKey,tagTypeKey
     * @throws MissingParameterException referenceId, referenceTypeKey, tagTypeKey not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TagInfo> getTagsByType(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "tagTypeKey") String tagTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a tag.
     *
     * @param tagId       tag identifier
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return information about a tag
     * @throws DoesNotExistException     specified tagId not found
     * @throws InvalidParameterException invalid tagId
     * @throws MissingParameterException tagId not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TagInfo getTag(@WebParam(name = "tagId") String tagId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves a list of Tags corresponding to the
     * given list of Tag keys.
     *
     * @param tagIds      list of Tags to be retrieved
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Tag information for the given list of Tag ids
     * @throws DoesNotExistException     an tagKey in list not found
     * @throws InvalidParameterException invalid tagKey
     * @throws MissingParameterException missing tagKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TagInfo> getTagsByIds(@WebParam(name = "tagIds") List<String> tagIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Tags of the specified type.
     *
     * @param tagTypeKey  type to be retrieved
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Tag ids
     * @throws InvalidParameterException invalid tagTypeKey
     * @throws MissingParameterException missing tagTypeKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getTagIdsByType(@WebParam(name = "tagTypeKey") String tagTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Tags based on the criteria and returns a list
     * of Tag identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Tag Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForTagIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Tags based on the criteria and returns a list of
     * Tags which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Tag information
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TagInfo> searchForTags(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds a tag to a reference.
     *
     * @param referenceId      identifier of reference
     * @param referenceTypeKey reference type
     * @param tagInfo          detailed information about the tag
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return detailed information about the tag
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException        Id or Key does not exist
     * @throws AlreadyExistsException       tag of that namespace, predicate and value already exists for that reference id
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            attempted update of readonly data
     */
    public TagInfo createTag(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "tagInfo") TagInfo tagInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Removes all tags associated with a single reference
     *
     * @param referenceId      identifier of the reference
     * @param referenceTypeKey reference type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     tagId, referenceId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteTagsByReference(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Removes a tag from all references to which it is linked.
     *
     * @param tagId       identifier of the tag
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     tagId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteTag(@WebParam(name = "tagId") String tagId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
