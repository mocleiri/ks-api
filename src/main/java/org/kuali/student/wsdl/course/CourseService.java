package org.kuali.student.wsdl.course;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Thu Aug 26 12:54:12 EDT 2010
 * Generated source version: 2.2.3
 * 
 */
 
@WebService(targetNamespace = "http://student.kuali.org/wsdl/course", name = "CourseService")
@XmlSeeAlso({org.kuali.student.wsdl.exceptions.ObjectFactory.class,org.kuali.student.wsdl.dictionary.ObjectFactory.class,ObjectFactory.class})
public interface CourseService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCourseActivities", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourseActivities")
    @ResponseWrapper(localName = "getCourseActivitiesResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourseActivitiesResponse")
    @WebMethod
    public java.util.List<org.kuali.student.wsdl.course.ActivityInfo> getCourseActivities(
        @WebParam(name = "formatId", targetNamespace = "")
        java.lang.String formatId
    ) throws PermissionDeniedException, MissingParameterException, OperationFailedException, DoesNotExistException, InvalidParameterException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "createCourseStatement", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.CreateCourseStatement")
    @ResponseWrapper(localName = "createCourseStatementResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.CreateCourseStatementResponse")
    @WebMethod
    public org.kuali.student.wsdl.course.StatementTreeViewInfo createCourseStatement(
        @WebParam(name = "courseId", targetNamespace = "")
        java.lang.String courseId,
        @WebParam(name = "statementTreeViewInfo", targetNamespace = "")
        org.kuali.student.wsdl.course.StatementTreeViewInfo statementTreeViewInfo
    ) throws PermissionDeniedException, MissingParameterException, OperationFailedException, DoesNotExistException, InvalidParameterException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateCourseStatement", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.UpdateCourseStatement")
    @ResponseWrapper(localName = "updateCourseStatementResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.UpdateCourseStatementResponse")
    @WebMethod
    public org.kuali.student.wsdl.course.StatementTreeViewInfo updateCourseStatement(
        @WebParam(name = "courseId", targetNamespace = "")
        java.lang.String courseId,
        @WebParam(name = "statementTreeViewInfo", targetNamespace = "")
        org.kuali.student.wsdl.course.StatementTreeViewInfo statementTreeViewInfo
    ) throws PermissionDeniedException, MissingParameterException, OperationFailedException, DoesNotExistException, InvalidParameterException;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "getObjectTypesResponse", targetNamespace = "http://student.kuali.org/wsdl/course", partName = "parameters")
    @WebMethod
    public org.kuali.student.wsdl.dictionary.GetObjectTypesResponse getObjectTypes(
        @WebParam(partName = "parameters", name = "getObjectTypes", targetNamespace = "http://student.kuali.org/wsdl/course")
        org.kuali.student.wsdl.dictionary.GetObjectTypes parameters
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateCourse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.UpdateCourse")
    @ResponseWrapper(localName = "updateCourseResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.UpdateCourseResponse")
    @WebMethod
    public org.kuali.student.wsdl.course.CourseInfo updateCourse(
        @WebParam(name = "courseInfo", targetNamespace = "")
        org.kuali.student.wsdl.course.CourseInfo courseInfo
    ) throws CircularRelationshipException, AlreadyExistsException, UnsupportedActionException, PermissionDeniedException, MissingParameterException, VersionMismatchException, OperationFailedException, DependentObjectsExistException, DoesNotExistException, InvalidParameterException, DataValidationErrorException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCourse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourse")
    @ResponseWrapper(localName = "getCourseResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourseResponse")
    @WebMethod
    public org.kuali.student.wsdl.course.CourseInfo getCourse(
        @WebParam(name = "courseId", targetNamespace = "")
        java.lang.String courseId
    ) throws PermissionDeniedException, MissingParameterException, OperationFailedException, DoesNotExistException, InvalidParameterException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCourseFormats", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourseFormats")
    @ResponseWrapper(localName = "getCourseFormatsResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourseFormatsResponse")
    @WebMethod
    public java.util.List<org.kuali.student.wsdl.course.FormatInfo> getCourseFormats(
        @WebParam(name = "courseId", targetNamespace = "")
        java.lang.String courseId
    ) throws PermissionDeniedException, MissingParameterException, OperationFailedException, DoesNotExistException, InvalidParameterException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "createCourse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.CreateCourse")
    @ResponseWrapper(localName = "createCourseResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.CreateCourseResponse")
    @WebMethod
    public org.kuali.student.wsdl.course.CourseInfo createCourse(
        @WebParam(name = "courseInfo", targetNamespace = "")
        org.kuali.student.wsdl.course.CourseInfo courseInfo
    ) throws CircularRelationshipException, AlreadyExistsException, UnsupportedActionException, PermissionDeniedException, MissingParameterException, VersionMismatchException, OperationFailedException, DependentObjectsExistException, DoesNotExistException, InvalidParameterException, DataValidationErrorException;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "getObjectStructureResponse", targetNamespace = "http://student.kuali.org/wsdl/course", partName = "parameters")
    @WebMethod
    public org.kuali.student.wsdl.dictionary.GetObjectStructureResponse getObjectStructure(
        @WebParam(partName = "parameters", name = "getObjectStructure", targetNamespace = "http://student.kuali.org/wsdl/course")
        org.kuali.student.wsdl.dictionary.GetObjectStructure parameters
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCourseLos", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourseLos")
    @ResponseWrapper(localName = "getCourseLosResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourseLosResponse")
    @WebMethod
    public java.util.List<org.kuali.student.wsdl.course.LoDisplayInfo> getCourseLos(
        @WebParam(name = "courseId", targetNamespace = "")
        java.lang.String courseId
    ) throws PermissionDeniedException, MissingParameterException, OperationFailedException, DoesNotExistException, InvalidParameterException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCourseStatements", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourseStatements")
    @ResponseWrapper(localName = "getCourseStatementsResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.GetCourseStatementsResponse")
    @WebMethod
    public java.util.List<org.kuali.student.wsdl.course.StatementTreeViewInfo> getCourseStatements(
        @WebParam(name = "courseId", targetNamespace = "")
        java.lang.String courseId
    ) throws PermissionDeniedException, MissingParameterException, OperationFailedException, DoesNotExistException, InvalidParameterException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "validateCourse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.ValidateCourse")
    @ResponseWrapper(localName = "validateCourseResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.ValidateCourseResponse")
    @WebMethod
    public java.util.List<org.kuali.student.wsdl.course.ValidationResultInfo> validateCourse(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        org.kuali.student.wsdl.course.CourseInfo arg1
    ) throws MissingParameterException, OperationFailedException, InvalidParameterException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deleteCourse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.DeleteCourse")
    @ResponseWrapper(localName = "deleteCourseResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.DeleteCourseResponse")
    @WebMethod
    public org.kuali.student.wsdl.course.StatusInfo deleteCourse(
        @WebParam(name = "courseId", targetNamespace = "")
        java.lang.String courseId
    ) throws CircularRelationshipException, AlreadyExistsException, UnsupportedActionException, PermissionDeniedException, MissingParameterException, VersionMismatchException, OperationFailedException, DependentObjectsExistException, DoesNotExistException, InvalidParameterException, DataValidationErrorException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "validateCourseStatement", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.ValidateCourseStatement")
    @ResponseWrapper(localName = "validateCourseStatementResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.ValidateCourseStatementResponse")
    @WebMethod
    public java.util.List<org.kuali.student.wsdl.course.ValidationResultInfo> validateCourseStatement(
        @WebParam(name = "courseId", targetNamespace = "")
        java.lang.String courseId,
        @WebParam(name = "statementTreeViewInfo", targetNamespace = "")
        org.kuali.student.wsdl.course.StatementTreeViewInfo statementTreeViewInfo
    ) throws MissingParameterException, OperationFailedException, InvalidParameterException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deleteCourseStatement", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.DeleteCourseStatement")
    @ResponseWrapper(localName = "deleteCourseStatementResponse", targetNamespace = "http://student.kuali.org/wsdl/course", className = "org.kuali.student.wsdl.course.DeleteCourseStatementResponse")
    @WebMethod
    public org.kuali.student.wsdl.course.StatusInfo deleteCourseStatement(
        @WebParam(name = "courseId", targetNamespace = "")
        java.lang.String courseId,
        @WebParam(name = "statementTreeViewInfo", targetNamespace = "")
        org.kuali.student.wsdl.course.StatementTreeViewInfo statementTreeViewInfo
    ) throws PermissionDeniedException, MissingParameterException, OperationFailedException, DoesNotExistException, InvalidParameterException;
}
