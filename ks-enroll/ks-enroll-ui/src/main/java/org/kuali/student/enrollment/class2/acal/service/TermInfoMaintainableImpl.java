package org.kuali.student.enrollment.class2.acal.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

public class TermInfoMaintainableImpl extends KualiMaintainableImpl {
	private static final long serialVersionUID = 1L;	
	
    public final static String TERM_KEY_PREFIX = "kuali.term.";
    public final static String TYPE_KEY_PREFIX = "kuali.atp.type.";
    public final static String DEFAULT_VALUE_OF_ATP_STATE ="kuali.atp.state.Official";
    
    private transient AcademicCalendarService academicCalendarService;

    @Override
    public void saveBusinessObject() {
    	System.out.println(">>In TermInfoMaintainableImpl.saveBusinessObject()");
        TermInfo termInfo = (TermInfo)getDataObject();
        String termKey = getTermInfoKey (termInfo);
        System.out.println(">>>termKey = "+termKey);
        termInfo.setKey(termKey);
        termInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);

        try{
        	if(getMaintenanceAction().equals(KNSConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KNSConstants.MAINTENANCE_COPY_ACTION)) {   
        		getAcademicCalendarService().createTerm(termKey, termInfo, ContextInfo.newInstance());
        	}
        	else {
        		getAcademicCalendarService().updateTerm(termKey, termInfo, ContextInfo.newInstance());
        	}
        }catch (AlreadyExistsException aee){
            
        }catch (DataValidationErrorException dvee){
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
           
        }catch (PermissionDeniedException pde){
            
        }catch (DoesNotExistException dee){
            
        }catch (VersionMismatchException vme){
            
        }       
        
    }
    
    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
    	ContextInfo context = ContextInfo.newInstance();
    	try{
    		return getAcademicCalendarService().getTerm(dataObjectKeys.get("key"), context);
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
           
        }catch (PermissionDeniedException pde){
            
        }catch (DoesNotExistException dee){
            
        }
        return null;
  
    }
    
    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
       	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal","AcademicCalendarService"));
       }

       return academicCalendarService;
   }
    
    /**
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
    	System.out.println (">>> in prepareForSave ");
        if (getMaintenanceAction().equalsIgnoreCase(KNSConstants.MAINTENANCE_NEW_ACTION)) {
        	TermInfo newTerm = (TermInfo)getDataObject();   	
        	newTerm.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        }
        super.prepareForSave();
    }
    
    /*
     *  Based on Norm's suggestion at 
     *  https://wiki.kuali.org/display/STUDENT/How+to+Calculate+Keys+for+Academic+Calendar+Entities
     *  Term Keys should be 
     *  kuali.term.<yearOfStartDate>-<yearOfEndDate>.
     *  <The last part of the type key of the term selected (when split using ".") converted to lower case>
     */
   private String getTermInfoKey(TermInfo termInfo){
       String termKey = new String (TERM_KEY_PREFIX);
       String theType;
       
       String theTypeKey = termInfo.getTypeKey();      
       if (theTypeKey.startsWith(TYPE_KEY_PREFIX)){
    	   theType = theTypeKey.substring(15);
       }
       else {
    	   theType = theTypeKey;
       }        
       String yearOfStartDate = getYearFromDate(termInfo.getStartDate());
       String yearOfEndDate = getYearFromDate(termInfo.getEndDate());
       termKey = termKey.concat("."+yearOfStartDate+"-"+yearOfEndDate+"."+theType.toLowerCase());
       return termKey;       
       
   }
   
   private String getYearFromDate(Date date){
   	Calendar cal = Calendar.getInstance();
   	cal.setTime(date);
   	int year = cal.get(Calendar.YEAR);
   	return new Integer(year).toString();
   }

}
