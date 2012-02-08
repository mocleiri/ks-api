/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.lum.program.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.exceptions.*;

import org.kuali.student.common.util.constants.ProgramServiceConstants;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.program.dto.*;

import org.springframework.transaction.annotation.Transactional;

/**
 * The Program Service allows for the creation and management of programs.
 * 
 * @Version 2.0
 * @Author sambitpa@usc.edu
 */
@WebService(name = "ProgramService", targetNamespace = ProgramServiceConstants.PROGRAM_NAMESPACE)
// TODO CHECK THESE VALUES
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ProgramService {

    /**
     * Retrieves a CredentialProgram
     * 
     * @param credentialProgramId Unique Id of the CredentialProgram. Maps to
     *            cluId
     * @return the created Credential Program
     * @throws DoesNotExistException CredentialProgram does not exist
     * @throws InvalidParameterException invalid Credential Program
     * @throws MissingParameterException missing Credential Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     */
    public CredentialProgramInfo getCredentialProgram(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of CredentialPrograms corresponding to the given list of
     * CredentialProgram Ids
     * 
     * @param credentialProgramIds Ids list of CredentialPrograms to be retrieved
     * @param contextInfo Context information containing the principalId and
     *            locale information about the caller of service operation
     * @return list of CredentialProgram
     * @throws DoesNotExistException an commentKey in list not found
     * @throws InvalidParameterException invalid commentKey
     * @throws MissingParameterException commentIds, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CredentialProgramInfo> getCredentialProgramsByIds(@WebParam(name = "credentialProgramIds") List<String> credentialProgramIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Credential Program against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param credentialProgramInfo Credential Program information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateCredentialProgram(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "credentialProgramInfo") CredentialProgramInfo credentialProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Creates a Credential Program
     * 
     * @param credentialProgramInfo credentialProgramInfo
     * @return the created Credential Program
     * @throws AlreadyExistsException The Credential Program already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Credential Program
     * @throws MissingParameterException missing Credential Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CredentialProgramInfo createCredentialProgram(@WebParam(name = "credentialProgramTypeKey") String credentialProgramTypeKey,
            @WebParam(name = "credentialProgramInfo") CredentialProgramInfo credentialProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Credential Program version based on the current Credential
     * Program
     * 
     * @param credentialProgramId identifier for the Credential Program to be
     *            versioned
     * @param versionComment comment for the current version
     * @return the new versioned Credential Program information
     * @throws DoesNotExistException Credential Program does not exist
     * @throws InvalidParameterException invalid credentialProgramId
     * @throws MissingParameterException invalid credentialProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws DataValidationErrorException
     */
    public CredentialProgramInfo createNewCredentialProgramVersion(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "versionComment") String versionComment,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException,ReadOnlyException;

    /**
     * Sets a specific version of the Credential Program as current. The
     * sequence number must be greater than the existing current Credential
     * Program version. This will truncate the current version's end date to the
     * currentVersionStart param. If a Major exists which is set to become
     * current in the future, that Major's currentVersionStart and
     * CurrentVersionEnd will be nullified. The currentVersionStart must be in
     * the future to prevent changing historic data.
     * 
     * @param credentialProgramId Version Specific Id of the Credential Program
     * @param currentVersionStart Date when this Credential Program becomes
     *            current. Must be in the future and be after the most current
     *            Credential Program's start date.
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Credential Program for credentialProgramId
     *             does not exist
     * @throws InvalidParameterException invalid credentialProgramId,
     *             currentVersionStart
     * @throws MissingParameterException invalid credentialProgramId
     * @throws IllegalVersionSequencingException a Credential Program with
     *             higher sequence number from the one provided is marked
     *             current
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo setCurrentCredentialProgramVersion(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "currentVersionStart") Date currentVersionStart,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Credential Program
     * 
     * @param credentialProgramInfo credentialProgramInfo
     * @return updated Credential Program
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Credential Program not found
     * @throws InvalidParameterException invalid Credential Program
     * @throws MissingParameterException missing Credential Program
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CredentialProgramInfo updateCredentialProgram(@WebParam(name = "credentialProgramId") String credentialProgramId,
            @WebParam(name = "credentialProgramInfo") CredentialProgramInfo credentialProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Credential Program
     * 
     * @param credentialProgramId identifier for credentialProgramId.Maps to
     *            cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Credential Program does not exist
     * @throws InvalidParameterException invalid credentialProgramId
     * @throws MissingParameterException invalid credentialProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCredentialProgram(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a MajorDiscipline
     * 
     * @param majorDisciplineId Unique Id of the MajorDiscipline. Maps to cluId
     * @return the created MajorDiscipline
     * @throws DoesNotExistException MajorDiscipline does not exist
     * @throws InvalidParameterException invalid MajorDiscipline
     * @throws MissingParameterException missing MajorDiscipline
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MajorDisciplineInfo getMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Retrieves a list of MajorDiscipline corresponding to the given list of
     * major discipline Ids
     * 
     * @param majorDisciplineIds Ids list of MajorDisciplines to be retrieved
     * @param contextInfo Context information containing the principalId and
     *            locale information about the caller of service operation
     * @return list of MajorDiscipline
     * @throws DoesNotExistException an commentKey in list not found
     * @throws InvalidParameterException invalid commentKey
     * @throws MissingParameterException commentIds, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MajorDisciplineInfo> getMajorDisciplinesByIds(@WebParam(name = "majorDisciplineIds") List<String> majorDisciplineIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Major Discipline identifiers a given Credential
     * Program Type.
     * 
     * @param programType Type of Credential Program
     * @return List of Major Discipline identifiers for the given Credential
     *         Program Type
     * @throws DoesNotExistException program type not found
     * @throws InvalidParameterException invalid program type
     * @throws MissingParameterException program type is not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<String> getMajorDisciplineIdsByCredentialProgramType(@WebParam(name = "programType") String programType, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

  
    /**
     * Validates a Major discipline against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param majorDisciplineInfo Major discipline information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateMajorDiscipline(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "majorDisciplineInfo") MajorDisciplineInfo majorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,PermissionDeniedException;

    /**
     * Creates a Major Discipline Program
     * 
     * @param majorDisciplineInfo majorDisciplineInfo
     * @return the created Major Discipline
     * @throws AlreadyExistsException The Major already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Major
     * @throws MissingParameterException missing Major
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MajorDisciplineInfo createMajorDiscipline(@WebParam(name = "majorDisciplineTypeKey") String majorDisciplineTypeKey,
            @WebParam(name = "majorDisciplineInfo") MajorDisciplineInfo majorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Major Discipline
     * 
     * @param majorDisciplineInfo majorDisciplineInfo
     * @return updated Major Discipline
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Major not found
     * @throws InvalidParameterException invalid Major
     * @throws MissingParameterException missing Major
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MajorDisciplineInfo updateMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "majorDisciplineInfo") MajorDisciplineInfo majorDisciplineInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Major Discipline
     * 
     * @param majorDisciplineId identifier for majorDisciplineId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Major does not exist
     * @throws InvalidParameterException invalid majorDisciplineId
     * @throws MissingParameterException invalid majorDisciplineId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Major Discipline version based on the current Major
     * 
     * @param majorDisciplineId identifier for the Major Discipline to be
     *            versioned
     * @param versionComment comment for the current version
     * @return the new versioned Major Discipline information
     * @throws DoesNotExistException Major does not exist
     * @throws InvalidParameterException invalid majorDisciplineId
     * @throws MissingParameterException invalid majorDisciplineId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws DataValidationErrorException
     */
    public MajorDisciplineInfo createNewMajorDisciplineVersion(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "versionComment") String versionComment,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException;

    /**
     * Retrieves a HonorsProgram
     * 
     * @param honorsProgramId Unique Id of the HonorsProgram. Maps to cluId
     * @return the created Honors Program
     * @throws DoesNotExistException HonorsProgram does not exist
     * @throws InvalidParameterException invalid Honors Program
     * @throws MissingParameterException missing Honors Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HonorsProgramInfo getHonorsProgram(@WebParam(name = "honorsProgramId") String honorsProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * 
     * Retrieves a list of honors program by ids
     * 
     * @param honorsProgramIds
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<HonorsProgramInfo> getHonorsProgramsByIds(@WebParam(name = "honorsProgramIds") List<String> honorsProgramIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Honors Program Program a given Credential Program
     * Type.
     * 
     * @param programType Type of Credential Program
     * @return List of Honors Programs for the given Credential Program Type
     * @throws DoesNotExistException program type not found
     * @throws InvalidParameterException invalid program type
     * @throws MissingParameterException program type is not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<String> getHonorProgramIdsByCredentialProgramType(@WebParam(name = "programType") String programType, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Validates a Honors Program against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param honorsProgramInfo Honors Program information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateHonorsProgram(@WebParam(name = "validationType") String validationType, @WebParam(name = "honorsProgramInfo") HonorsProgramInfo honorsProgramInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a Honors Program
     * 
     * @param honorsProgramInfo honorsProgramInfo
     * @return the created Honors Program
     * @throws AlreadyExistsException The Honors Program already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Honors Program
     * @throws MissingParameterException missing Honors Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HonorsProgramInfo createHonorsProgram(@WebParam(name = "honorsProgramTypeKey") String honorsProgramTypeKey, @WebParam(name = "honorsProgramInfo") HonorsProgramInfo honorsProgramInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Honors Program
     * 
     * @param honorsProgramInfo honorsProgramInfo
     * @return updated Honors Program
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Honors Program not found
     * @throws InvalidParameterException invalid Honors Program
     * @throws MissingParameterException missing Honors Program
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HonorsProgramInfo updateHonorsProgram(@WebParam(name = "honorsProgramId") String honorsProgramId, @WebParam(name = "honorsProgramTypeKey") String honorsProgramTypeKey,
            @WebParam(name = "honorsProgramInfo") HonorsProgramInfo honorsProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Honors Program
     * 
     * @param honorsProgramId identifier for honorsProgramId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Honors Program does not exist
     * @throws InvalidParameterException invalid honorsProgramId
     * @throws MissingParameterException invalid honorsProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteHonorsProgram(@WebParam(name = "honorsProgramId") String honorsProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a Core Program
     * 
     * @param coreProgramId Unique Id of the Core Program. Maps to cluId
     * @return the Core Program
     * @throws DoesNotExistException Program Requirement does not exist
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CoreProgramInfo getCoreProgram(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * 
     * Retrieves a list of core program by ids
     * 
     * @param coreProgramIds
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CoreProgramInfo> getCoreProgramsByIds(@WebParam(name = "coreProgramIds") List<String> coreProgramIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Core Program against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param coreProgramInfo Core Program information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateCoreProgram(@WebParam(name = "validationType") String validationType, @WebParam(name = "coreProgramInfo") CoreProgramInfo coreProgramInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a Core Program
     * 
     * @param coreProgramInfo coreProgramInfo
     * @return the created Core Program
     * @throws AlreadyExistsException The Core Program already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Core Program
     * @throws MissingParameterException missing Core Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CoreProgramInfo createCoreProgram(@WebParam(name = "coreProgramTypeKey") String coreProgramTypeKey, @WebParam(name = "coreProgramInfo") CoreProgramInfo coreProgramInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Core Program version based on the current Core Program
     * 
     * @param coreProgramId identifier for the Core Program to be versioned
     * @param versionComment comment for the current version
     * @return the new versioned Core Program information
     * @throws DoesNotExistException Core Program does not exist
     * @throws InvalidParameterException invalid coreProgramId
     * @throws MissingParameterException invalid coreProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws DataValidationErrorException
     */
    public CoreProgramInfo createNewCoreProgramVersion(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "versionComment") String versionComment,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException,ReadOnlyException;

    /**
     * Sets a specific version of the Core Program as current. The sequence
     * number must be greater than the existing current Core Program version.
     * This will truncate the current version's end date to the
     * currentVersionStart param. If a Major exists which is set to become
     * current in the future, that Major's currentVersionStart and
     * CurrentVersionEnd will be nullified. The currentVersionStart must be in
     * the future to prevent changing historic data.
     * 
     * @param coreProgramId Version Specific Id of the Core Program
     * @param currentVersionStart Date when this Core Program becomes current.
     *            Must be in the future and be after the most current major's
     *            start date.
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Core Program for coreProgramId does not
     *             exist
     * @throws InvalidParameterException invalid coreProgramId,
     *             currentVersionStart
     * @throws MissingParameterException invalid coreProgramId
     * @throws IllegalVersionSequencingException a CoreProgram with higher
     *             sequence number from the one provided is marked current
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo setCurrentCoreProgramVersion(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "currentVersionStart") Date currentVersionStart,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Core Program
     * 
     * @param coreProgramInfo coreProgramInfo
     * @return updated Core Program
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Core Program not found
     * @throws InvalidParameterException invalid Core Program
     * @throws MissingParameterException missing Core Program
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CoreProgramInfo updateCoreProgram(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "coreProgramTypeKey") String coreProgramTypeKey,
            @WebParam(name = "coreProgramInfo") CoreProgramInfo coreProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Core Program
     * 
     * @param coreProgramId identifier for coreProgramId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Core Program does not exist
     * @throws InvalidParameterException invalid coreProgramId
     * @throws MissingParameterException invalid coreProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCoreProgram(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a ProgramRequirement
     * 
     * @param programRequirementId Unique Id of the ProgramRequirement. Maps to
     *            cluId
     * @return the Program Requirement
     * @throws DoesNotExistException Program Requirement does not exist
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProgramRequirementInfo getProgramRequirement(@WebParam(name = "programRequirementId") String programRequirementId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * 
     * Retrieves a list of ProgramRequirements by ids
     * 
     * @param programRequirementIds
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<ProgramRequirementInfo> getProgramRequirementsByIds(@WebParam(name = "programRequirementIds") List<String> programRequirementIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Program Requirement against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param programRequirementInfo Program Requirement information to be
     *            tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateProgramRequirement(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "programRequirementInfo") ProgramRequirementInfo programRequirementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Creates a Program Requirement
     * 
     * @param programRequirementInfo programRequirementInfo
     * @return the created Program Requirement
     * @throws AlreadyExistsException The Program Requirement already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProgramRequirementInfo createProgramRequirement(@WebParam(name = "programRequirementTypeKey") String programRequirementTypeKey,
            @WebParam(name = "programRequirementInfo") ProgramRequirementInfo programRequirementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Program Requirement
     * 
     * @param programRequirementInfo programRequirementInfo
     * @return updated Program Requirement
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Program Requirement not found
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProgramRequirementInfo updateProgramRequirement(@WebParam(name = "programRequirementId") String programRequirementId,
            @WebParam(name = "programRequirementTypeKey") String programRequirementTypeKey, @WebParam(name = "programRequirementInfo") ProgramRequirementInfo programRequirementInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Program Requirement
     * 
     * @param programRequirementId identifier for programRequirementId.Maps to
     *            cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Program Requirement does not exist
     * @throws InvalidParameterException invalid programRequirementId
     * @throws MissingParameterException invalid programRequirementId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteProgramRequirement(@WebParam(name = "programRequirementId") String programRequirementId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Sets a specific version of the Major as current. The sequence number must
     * be greater than the existing current Major version. This will truncate
     * the current version's end date to the currentVersionStart param. If a
     * Major exists which is set to become current in the future, that Major's
     * currentVersionStart and CurrentVersionEnd will be nullified. The
     * currentVersionStart must be in the future to prevent changing historic
     * data.
     * 
     * @param majorDisciplineId Version Specific Id of the Major Discipline
     * @param currentVersionStart Date when this Major becomes current. Must be
     *            in the future and be after the most current major's start
     *            date.
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Major for majorVersionId does not exist
     * @throws InvalidParameterException invalid majorVersionId,
     *             currentVersionStart
     * @throws MissingParameterException invalid majorVersionId
     * @throws IllegalVersionSequencingException a Major with higher sequence
     *             number from the one provided is marked current
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo setCurrentMajorDisciplineVersion(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "currentVersionStart") Date currentVersionStart,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a MinorDiscipline
     * 
     * @param minorDisciplineId Unique Id of the MinorDiscipline. Maps to cluId
     * @return the created MinorDiscipline
     * @throws DoesNotExistException MinorDiscipline does not exist
     * @throws InvalidParameterException invalid MinorDiscipline
     * @throws MissingParameterException missing MinorDiscipline
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MinorDisciplineInfo getMinorDiscipline(@WebParam(name = "minorDisciplineId") String minorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Minor Discipline Program a given Credential Program
     * Type.
     * 
     * @param programType Type of Credential Program
     * @return List of Minor Disciplines for the given Credential Program Type
     * @throws DoesNotExistException program type not found
     * @throws InvalidParameterException invalid program type
     * @throws MissingParameterException program type is not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<String> getMinorsByCredentialProgramType(@WebParam(name = "programType") String programType, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Validates a Minor discipline against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param minorDisciplineInfo Minor discipline information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<org.kuali.student.common.validation.dto.ValidationResultInfo> validateMinorDiscipline(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "minorDisciplineInfo") MinorDisciplineInfo minorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Creates a Minor Discipline Program
     * 
     * @param minorDisciplineInfo minorDisciplineInfo
     * @return the created Minor Discipline
     * @throws AlreadyExistsException The Minor already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Minor
     * @throws MissingParameterException missing Minor
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MinorDisciplineInfo createMinorDiscipline(@WebParam(name = "minorDisciplineTypeKey") String minorDisciplineTypeKey,
            @WebParam(name = "minorDisciplineInfo") MinorDisciplineInfo minorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Minor Discipline
     * 
     * @param minorDisciplineInfo minorDisciplineInfo
     * @return updated Minor Discipline
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Minor not found
     * @throws InvalidParameterException invalid Minor
     * @throws MissingParameterException missing Minor
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MinorDisciplineInfo updateMinorDiscipline(@WebParam(name = "minorDisciplineId") String minorDisciplineId, @WebParam(name = "minorDisciplineTypeKey") String minorDisciplineTypeKey,
            @WebParam(name = "minorDisciplineInfo") MinorDisciplineInfo minorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Minor Discipline
     * 
     * @param minorDisciplineId identifier for minorDisciplineId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Minor does not exist
     * @throws InvalidParameterException invalid minorDisciplineId
     * @throws MissingParameterException invalid minorDisciplineId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteMinorDiscipline(@WebParam(name = "minorDisciplineId") String minorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @Deprecated
    public VersionDisplayInfo getCurrentVersion(@WebParam(name="refObjectTypeURI") String refObjectTypeURI, @WebParam (name="refObjectId") String refObjectId, @WebParam(name="contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    @Deprecated
    public List<VersionDisplayInfo> getVersions(@WebParam(name="programServiceConstants") String programServiceConstants, @WebParam (name="versionIndId") String versionIndId, @WebParam(name="contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    //TODO KSCM I added this methods since the implementation has this method aswell.
    // The signature is updated on implementation to have a ContexInfo parameter.
    @Deprecated
    @Transactional(readOnly=true)
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectTypeURI,
                                               String refObjectId, Date date, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    @Transactional(readOnly=true)
    VersionDisplayInfo getFirstVersion(String refObjectTypeURI,
                                       String refObjectId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    @Transactional(readOnly=true)
    VersionDisplayInfo getLatestVersion(String refObjectTypeURI,
                                        String refObjectId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    @Transactional(readOnly=true)
    VersionDisplayInfo getVersionBySequenceNumber(
            String refObjectTypeURI, String refObjectId, Long sequence, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    @Transactional(readOnly=true)
    List<VersionDisplayInfo> getVersionsInDateRange(String refObjectTypeURI, String refObjectId, Date from, Date to, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    LuTypeInfo getCredentialProgramType(String credentialProgramTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    List<LuTypeInfo> getCredentialProgramTypes(ContextInfo contextInfo)
            throws OperationFailedException;

    List<String> getHonorsByCredentialProgramType(String programType, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    List<String> getMajorIdsByCredentialProgramType(String programType, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    @Transactional(readOnly=true)
    List<ProgramVariationInfo> getVariationsByMajorDisciplineId(
            String majorDisciplineId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException;

    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    CredentialProgramInfo updateCredentialProgram(
            CredentialProgramInfo credentialProgramInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException;

    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    HonorsProgramInfo updateHonorsProgram(
            HonorsProgramInfo honorsProgramInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException;

    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    MinorDisciplineInfo updateMinorDiscipline(
            MinorDisciplineInfo minorDisciplineInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException;

    ObjectStructureDefinition getObjectStructure(String objectTypeKey, ContextInfo contextInfo);

    List<String> getObjectTypes(ContextInfo contextInfo);

    @Transactional(readOnly=true)
    ProgramRequirementInfo getProgramRequirement(String programRequirementId, String nlUsageTypeKey, String language, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    MajorDisciplineInfo updateMajorDiscipline(
            MajorDisciplineInfo majorDisciplineInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException;

    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    ProgramRequirementInfo updateProgramRequirement(
            ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException;




}